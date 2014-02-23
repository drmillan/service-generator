/**
* Interface for defining types of cache
* @author Service Generator
*/
package com.mobivery.utils;

public interface CacheInterface<T, Q> {
    public void put(T request, Q response);
    public Q get(T request);
}
