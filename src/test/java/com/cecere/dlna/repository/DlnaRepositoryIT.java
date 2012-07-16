package com.cecere.dlna.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.cecere.dlna.domain.Content;
import com.cecere.mediacloud.config.ClingConfig;
import com.cecere.mediacloud.config.RepositoryConfig;
import com.cecere.repository.Repository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ClingConfig.class,RepositoryConfig.class}, loader = AnnotationConfigContextLoader.class)
public class DlnaRepositoryIT {

	@Autowired
	private Repository<Content> repository;
	
	@Test
	public void testInit(){
		List<Content> contents = repository.findAll();
		assertEquals(2,contents.size());
	}
	
}
