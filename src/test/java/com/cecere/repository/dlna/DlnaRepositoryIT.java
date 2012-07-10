package com.cecere.repository.dlna;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.cecere.config.RepositoryConfig;
import com.cecere.domain.dlna.Content;
import com.cecere.repository.ReadOnlyTreeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfig.class}, loader = AnnotationConfigContextLoader.class)
public class DlnaRepositoryIT {

	@Autowired
	private ReadOnlyTreeRepository<Content> repository;
	
	@Test
	public void testInit(){
		List<Content> contents = repository.findAllChildren();
		assertEquals(2,contents.size());
	}
	
}
