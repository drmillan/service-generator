package com.mobivery.utils;

/**
* Caching system that does nothing at all, for compatibility purposes or deactivating caching
* @author Service Generator
*/

public class NoCache implements CacheInterface {
    @Override
    public void put(Object request, Object response) {
        // Nothing on purpose
    }

    @Override
    public Object get(Object request) {
        return null;
    }

    @Override
    public Collection keys() {
        return null;
    }

    @Override
    public void clear() {
        // Nothing on purpose
    }

}
