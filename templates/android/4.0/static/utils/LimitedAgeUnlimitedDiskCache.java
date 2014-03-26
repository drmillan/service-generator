package {{{staticPackage}}}.utils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Unlimited disk cache that adds an expiration date to the hits
 *
 * @author Service Generator
 */
public class LimitedAgeUnlimitedDiskCache<T, Q> extends UnlimitedDiskCache<T, Q> {

    private final long maximumAge;
    private final Map<File, Long> datesMap = Collections.synchronizedMap(new HashMap<File, Long>());

    public LimitedAgeUnlimitedDiskCache(DataTransferObjectJSONMarshallingInterface<Q> dtoMarshaller, File cacheDirectory, long maximumAge) {
        super(cacheDirectory, dtoMarshaller);
        this.maximumAge = maximumAge;
    }

    @Override
    public void put(T request, Q response) {
        long currentTime = System.currentTimeMillis();
        File file = fileFromKey(request);
        file.setLastModified(currentTime);
        datesMap.put(file, currentTime);
        super.put(request, response);
    }

    @Override
    public Q get(T request) {
        File file = fileFromKey(request);
        if (file.exists()) {
            boolean cached = false;
            Long loadingDate = datesMap.get(file);
            if (loadingDate == null) {
                cached = false;
                loadingDate = file.lastModified();
            } else {
                cached = true;
            }

            if (System.currentTimeMillis() - loadingDate > maximumAge) {
                file.delete();
                datesMap.remove(file);
            } else if (!cached) {
                datesMap.put(file, loadingDate);
            }
        }
        return super.get(request);
    }

}
