package com.itstore.core.data.converter;

import org.apache.commons.lang3.NotImplementedException;

public interface Converter<T, S> {

    T convert(S source);

    default S convert(T source, Object... additional) {
        throw new NotImplementedException("Method 'S convert(T source)' not implemented");
    }
}
