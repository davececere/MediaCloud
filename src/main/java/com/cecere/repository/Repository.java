package com.cecere.repository;

import java.util.List;

public interface Repository<T> {
	List<T> findAll();
}
