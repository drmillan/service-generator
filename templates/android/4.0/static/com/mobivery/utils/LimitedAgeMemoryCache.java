package com.mobivery.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for other memory caches that adds an expiration date to the hits
 *
 * @author Service Generator
 */
public class LimitedAgeMemoryCache<T, Q> implements CacheInterface<T, Q> {

    private final CacheInterface<T, Q> cache;
    private final long maximumAge;
    private final Map<T, Long> datesMap = Collections.synchronizedMap(new HashMap<T, Long>());

    /**
     * Applies a wrap layer to any memory cache system so we can add some maximum time the object
     * is valid on the cache.
     * @param cache any other memory cache system
     * @param maximumAge maximum time the cache is valid, in milliseconds
     */
    public LimitedAgeMemoryCache(CacheInterface<T,Q> cache, long maximumAge) {
        this.cache = cache;
        this.maximumAge = maximumAge;
    }

    @Override
    public void put(T request, Q response) {
        cache.put(request, response);
        datesMap.put(request, System.currentTimeMillis());
    }

    @Override
    public Q get(T request) {
        Long date = datesMap.get(request);
        if (date != null && System.currentTimeMillis() - date > maximumAge) {
            cache.remove(request);
            datesMap.remove(request);
        }
        return cache.get(request);
    }

    @Override
    public void remove(T key) {
        cache.remove(key);
        datesMap.remove(key);
    }

    @Override
    public Collection<T> keys() {
        return cache.keys();
    }

    @Override
    public void clear() {
        cache.clear();
        datesMap.clear();
    }
}
