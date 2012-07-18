package com.cecere.mediacloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cecere.mediacloud.controller.ContentController;
import com.cecere.mediacloud.controller.DeviceController;
import com.cecere.mediacloud.service.ContentService;
import com.cecere.mediacloud.service.DeviceService;

@Configuration
public class ControllerConfig {

	@Autowired
	private ContentService contentService;
	@Autowired
	private DeviceService deviceService;
	
	@Bean
	public ContentController contentController(){
		return new ContentController(contentService);
	}
	
	@Bean
	public DeviceController deviceController(){
		return new DeviceController(deviceService,contentService);
	}
}
