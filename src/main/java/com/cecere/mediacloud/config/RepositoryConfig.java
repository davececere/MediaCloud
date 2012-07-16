package com.cecere.mediacloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.teleal.cling.UpnpService;
import org.teleal.cling.support.model.item.Item;

import com.cecere.converter.Converter;
import com.cecere.dlna.cling.converter.ClingDeviceToDeviceConverter;
import com.cecere.dlna.cling.converter.ClingItemToContentConverter;
import com.cecere.dlna.domain.Content;
import org.teleal.cling.model.meta.Device;
import com.cecere.dlna.repository.DlnaContentRepository;
import com.cecere.dlna.repository.DlnaDeviceRepository;
import com.cecere.repository.ContentRepository;
import com.cecere.repository.DeviceRepository;

@Configuration
public class RepositoryConfig {
	@Autowired
	private UpnpService upnpService;
	
	@Bean
	public ContentRepository dlnaContentRepository() {
		ContentRepository ret = new DlnaContentRepository(upnpService,clingItemToContentConverter());
		((DlnaContentRepository)ret).initContents();
		return ret;
	}
	
	@Bean
	public DeviceRepository dlnaDeviceRepository() {
		DeviceRepository ret = new DlnaDeviceRepository(upnpService,clingDeviceToDeviceConverter());
		return ret;
	}
	
	@Bean
	public Converter<Device,com.cecere.dlna.domain.Device> clingDeviceToDeviceConverter(){
		return new ClingDeviceToDeviceConverter();
	}
	
	@Bean
	public Converter<Item,Content> clingItemToContentConverter(){
		return new ClingItemToContentConverter();
	}
}
