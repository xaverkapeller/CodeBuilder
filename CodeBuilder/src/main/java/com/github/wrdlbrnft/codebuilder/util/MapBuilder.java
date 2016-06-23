package com.github.wrdlbrnft.codebuilder.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kapeller on 30/09/15.
 */
public final class MapBuilder<K, V> {

    private final Map<K, V> mMap = new HashMap<>();

    public MapBuilder<K, V> put(K key, V value) {
        mMap.put(key, value);
        return this;
    }

    public Map<K, V> build() {
        return mMap;
    }
}

