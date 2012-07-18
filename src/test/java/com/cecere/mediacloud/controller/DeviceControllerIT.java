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
import com.cecere.dlna.domain.Device;
import com.cecere.dlna.domain.Renderer;
import com.cecere.mediacloud.config.ClingConfig;
import com.cecere.mediacloud.config.ControllerConfig;
import com.cecere.mediacloud.config.RepositoryConfig;
import com.cecere.mediacloud.config.ServiceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ClingConfig.class,RepositoryConfig.class,ServiceConfig.class,ControllerConfig.class}, loader = AnnotationConfigContextLoader.class)
public class DeviceControllerIT {

	@Autowired
	private DeviceController controller;
	
	@Autowired
	private ContentController contentController;
	
	@Test
	public void testDevices(){
		List<Device> devices = controller.getAllDevices();
		assertFalse(devices.isEmpty());
		for(Device d: devices){
			System.out.println("name: "+d.getName());
			assertNotNull(d.getName());
		}
	}
	
	@Test
	public void testRenderers(){
		List<Renderer> devices = controller.getAllRenderers();
		assertFalse(devices.isEmpty());
		for(Device d: devices){
			System.out.println("name: "+d.getName());
			assertNotNull(d.getName());
		}
	}
	
	
	@Test
	public void testPlayMedia(){
		String rendererName = "Samsung Electronics UN55D7050 AllShare1.0"; //"Bubblesoft BubbleUPnP Media Renderer 1.4.4.1";
		List<Content> contents = contentController.getAllContents();
		Renderer r = new Renderer(null,null);
		r.setName(rendererName);
		r.setNowPlayingContentId(contents.get(0).getId());
		controller.playContentOnRenderer(rendererName,r);
	}
}
