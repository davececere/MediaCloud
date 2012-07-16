package com.cecere.mediacloud.controller;

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
import com.cecere.mediacloud.config.ControllerConfig;
import com.cecere.mediacloud.config.RepositoryConfig;
import com.cecere.mediacloud.config.ServiceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ClingConfig.class,RepositoryConfig.class,ServiceConfig.class,ControllerConfig.class}, loader = AnnotationConfigContextLoader.class)
public class ContentControllerIT {

	@Autowired
	private ContentController controller;
	
	@Test
	public void testContents(){
		List<Content> contents = controller.getAllContents();
		assertFalse(contents.isEmpty());
		for(Content c: contents){
			assertNotNull(c.getId());
			assertNotNull(c.getTitle());
		}
	}
	
}
