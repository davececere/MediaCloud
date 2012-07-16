package com.cecere.mediacloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cecere.mediacloud.service.ContentService;
import com.cecere.mediacloud.service.ContentServiceImpl;
import com.cecere.mediacloud.service.DeviceService;
import com.cecere.mediacloud.service.DeviceServiceImpl;
import com.cecere.repository.ContentRepository;
import com.cecere.repository.DeviceRepository;

@Configuration
public class ServiceConfig {
	
	@Autowired
	private ContentRepository contentRepository;
	@Autowired
	private DeviceRepository deviceRepository;

	@Bean
	public ContentService contentService(){
		return new ContentServiceImpl(contentRepository);
	}
	
	@Bean
	public DeviceService deviceService(){
		return new DeviceServiceImpl(deviceRepository);
	}
}
