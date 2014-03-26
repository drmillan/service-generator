package {{{staticPackage}}}.utils;

import java.util.Collection;

/**
* Interface for defining types of cache
* @author Service Generator
*/
public interface CacheInterface<T, Q> {
    public void put(T request, Q response);
    public Q get(T request);
    public void remove(T request);
    public Collection<T> keys();
    public void clear();
}
