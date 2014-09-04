package {{{staticPackage}}}.utils;

import java.util.Collection;

/**
* Caching system that does nothing at all, for compatibility purposes or deactivating caching
* @author Service Generator
*
* Generated Class - DO NOT MODIFY
*/

public class NoCache<T, Q> implements CacheInterface<T, Q> {
    @Override
    public void put(T request, Q response) {
        // Nothing on purpose
    }

    @Override
    public Q get(T request) {
        return null;
    }

    @Override
    public void remove(T request) {
        
    }

    @Override
    public Collection<T> keys() {
        return null;
    }

    @Override
    public void clear() {
        // Nothing on purpose
    }

}
