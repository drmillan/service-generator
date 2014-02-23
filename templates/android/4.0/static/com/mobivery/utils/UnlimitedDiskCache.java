package com.mobivery.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * Abstract class to save contents to disk. As we do not know if the stuff stored is a DTO or how
 * can we serialize/deserialize it, that part is left to the developer to implement, thus the
 * abstraction.
 *
 * @author Service Generator
 */
public abstract class UnlimitedDiskCache<T, Q> implements CacheInterface<T, Q> {

    private final File cacheDirectory;

    public UnlimitedDiskCache(File cacheDirectory) {
        if (cacheDirectory == null) {
            throw new IllegalArgumentException("cacheDirectory must be not null");
        }
        this.cacheDirectory = cacheDirectory;
    }

    @Override
    public void put(T request, Q response) {
        File localFile = fileFromKey(request);
        String contents = serializeObject(response);
        writeStringToFile(localFile, contents);
    }

    public abstract String serializeObject(Q response);

    @Override
    public Q get(T request) {
        File localFile = fileFromKey(request);
        if (localFile.exists()) {
            String objectString = readStringFromFile(localFile);
            return deserializeObject(objectString);
        } else {
            return null;
        }
    }

    public abstract Q deserializeObject(String serializedString);

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
