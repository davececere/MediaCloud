package com.cecere.repository;

import java.util.List;

public interface ReadOnlyTreeRepository<T> {
	public List<T> findAllChildren();
}
