package com.cecere.converter;

public interface Converter<T, V> {
	public V convertTo(T from);
	public T convertFrom(V to);
}
