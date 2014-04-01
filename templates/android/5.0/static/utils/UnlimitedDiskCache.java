package {{{staticPackage}}}.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * Class to save contents to disk. As we do not know if the stuff stored is a DTO or how
 * can we serialize/deserialize it, we need the DataTransferObjectJSONMarshallingInterface
 *
 * @author Service Generator
 *
 * Generated Class - DO NOT MODIFY
 */
public class UnlimitedDiskCache<T, Q> implements CacheInterface<T, Q> {

    private final File cacheDirectory;
    private final DataTransferObjectJSONMarshallingInterface<Q> dtoMarshaller;

    public UnlimitedDiskCache(File cacheDirectory, DataTransferObjectJSONMarshallingInterface<Q> dtoMarshaller) {
        if (cacheDirectory == null) {
            throw new IllegalArgumentException("cacheDirectory must be not null");
        }
        if (dtoMarshaller == null) {
            throw new IllegalArgumentException("dtoMarshaller must be not null");
        }
        this.cacheDirectory = cacheDirectory;
        cacheDirectory.mkdirs();
        this.dtoMarshaller = dtoMarshaller;
    }

    @Override
    public void put(T request, Q response) {
        File localFile = fileFromKey(request);
        String contents = dtoMarshaller.serialize(response).toString();
        writeStringToFile(localFile, contents);
    }

    @Override
    public Q get(T request) {
        File localFile = fileFromKey(request);
        if (localFile.exists()) {
            String objectString = readStringFromFile(localFile);
            try {
                return dtoMarshaller.deserialize(new JSONObject(objectString));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void remove(T request) {
        File localFile = fileFromKey(request);
        if (localFile.exists()) {
            localFile.delete();
        }
    }

    @Override
    public Collection<T> keys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        File[] files = cacheDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    protected File fileFromKey(T request) {
        String localName = generateFileName(request);
        return new File(cacheDirectory, localName);
    }

    protected String generateFileName(T request) {
        return Integer.valueOf(request.hashCode()).toString();
    }

    protected void writeStringToFile(File file, String contents) {
        try {
            FileWriter out = new FileWriter(file);
            out.write(contents);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String readStringFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in;

        try {
            in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
