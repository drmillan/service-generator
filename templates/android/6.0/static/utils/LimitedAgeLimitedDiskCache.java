package {{{staticPackage}}}.utils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Unlimited disk cache that adds an expiration date to the hits and cache size limit
 *
 * @author Service Generator
 *
 * Generated Class - DO NOT MODIFY
 */
public class LimitedAgeLimitedDiskCache<T, Q> extends UnlimitedDiskCache<T, Q> {

    private final long maxSizeInBytes;
    private final long maximumAge;
    private final Map<File, Long> datesMap = Collections.synchronizedMap(new HashMap<File, Long>());
    private final File cacheDirectory;

    public LimitedAgeLimitedDiskCache(DataTransferObjectJSONMarshallingInterface<Q> dtoMarshaller, File cacheDirectory, File cacheFilesDirectory, long maximumAge, long maxSizeInBytes) {
        super(cacheFilesDirectory, dtoMarshaller);
        this.maximumAge = maximumAge;
        this.cacheDirectory = cacheDirectory;
        this.maxSizeInBytes = maxSizeInBytes;
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

            long cacheDirSize = dirSize(this.cacheDirectory);
            if (cacheDirSize >= maxSizeInBytes) {
                deleteCacheDir(cacheDirectory);
            } else if (System.currentTimeMillis() - loadingDate > maximumAge) {
                file.delete();
                datesMap.remove(file);
            } else if (!cached) {
                datesMap.put(file, loadingDate);
            }
        }
        return super.get(request);
    }

    private void deleteCacheDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();

            for (int i = 0; i < children.length; i++) {
                File listedFile = new File(dir, children[i]);
                if (listedFile.isDirectory()) {
                    deleteCacheDir(listedFile);
                } else {
                    listedFile.delete();
                }
            }
        }
    }

    /**
     * Return the size of a directory in bytes
     */
    private static long dirSize(File dir) {

        if (dir.exists() && dir.isDirectory()) {
            long result = 0;
            File[] fileList = dir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // Recursive call if it's a directory
                if (fileList[i].isDirectory()) {
                    result += dirSize(fileList[i]);
                } else {
                    // Sum the file size in bytes
                    result += fileList[i].length();
                }
            }
            return result; // return the file size
        }
        return 0;
    }

}
