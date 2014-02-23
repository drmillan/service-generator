package com.mobivery.utils;

import java.util.Collection;
import java.util.HashSet;

/**
 * Memory cache with a fixed maximum size, using LRU algorithm to discard elements. Works for Android 3.1+.
 * A new implementation could be done using the support libraries.
 *
 * @author Service Generator
 */
public class LruMemoryCache<T, Q> implements CacheInterface<T, Q> {

    private final int maxSize;
    private final LruCache<T, Q> cache;

    public LruMemoryCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        this.cache = new LruCache<T, Q>(maxSize);
    }

    @Override
    public void put(T request, Q response) {
        if (request == null || response == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (cache) {
            cache.put(request, response);
        }
    }

    @Override
    public Q get(T request) {
        if (request == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (cache) {
            return cache.get(request);
        }
    }

    @Override
    public void remove(T request) {
        if (request == null) {
            throw new NullPointerException("key == null");
        }

        synchronized (this) {
           cache.remove(request);
        }
    }

    @Override
    public Collection<T> keys() {
        synchronized (this) {
            return new HashSet<T>(cache.snapshot().keySet());
        }
    }

    @Override
    public void clear() {
        cache.evictAll();
    }
}
