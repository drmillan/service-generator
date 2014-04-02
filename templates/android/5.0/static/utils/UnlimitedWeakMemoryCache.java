package {{{staticPackage}}}.utils;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Memory cache of weak references with unlimited size
 * @author Service Generator
 *
 * Generated Class - DO NOT MODIFY
 */
public class UnlimitedWeakMemoryCache<T, Q> implements CacheInterface<T, Q> {

    private final Map<T, WeakReference<Q>> weakReferenceMap = Collections.synchronizedMap(new HashMap<T, WeakReference<Q>>());

    @Override
    public void put(T request, Q response) {
        weakReferenceMap.put(request, new WeakReference<Q>(response));
    }

    @Override
    public Q get(T request) {
        Q response = null;
        WeakReference<Q> reference = weakReferenceMap.get(request);
        if (reference != null) {
            response = reference.get();
        }
        return response;
    }

    @Override
    public void remove(T request) {
        weakReferenceMap.remove(request);
    }

    @Override
    public Collection<T> keys() {
        synchronized (weakReferenceMap) {
            return new HashSet<T>(weakReferenceMap.keySet());
        }
    }

    @Override
    public void clear() {
        weakReferenceMap.clear();
    }
}
