package com.cecere.mediacloud.service;

import java.util.List;

import com.cecere.dlna.domain.Content;
import com.cecere.repository.Repository;

public class ContentServiceImpl implements ContentService {
	
	private Repository<Content> readOnlyRepository;

	public ContentServiceImpl(Repository<Content> repository){
		this.readOnlyRepository = repository;
	}
	
	@Override
	public List<Content> findAllContent() {
		return readOnlyRepository.findAll();
	}

}
