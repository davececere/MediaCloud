package com.cecere.dlna.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.teleal.cling.UpnpService;
import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.model.message.header.STAllHeader;
import org.teleal.cling.model.meta.Device;
import org.teleal.cling.model.meta.LocalDevice;
import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.registry.Registry;
import org.teleal.cling.registry.RegistryListener;

import com.cecere.converter.Converter;
import com.cecere.dlna.cling.UpnpServiceConfiguration;
import com.cecere.repository.DeviceRepository;

public class DlnaDeviceRepository implements DeviceRepository{
	private UpnpService upnpService; 
	private Converter<Device, com.cecere.dlna.domain.Device> deviceConverter;
	
	public DlnaDeviceRepository(UpnpService upnpService,Converter<Device,com.cecere.dlna.domain.Device> deviceConverter){
		this.upnpService = upnpService;
		this.deviceConverter = deviceConverter;
	}
		@Override
	public List<com.cecere.dlna.domain.Device> findAll() {
		List<com.cecere.dlna.domain.Device> ret = new ArrayList<com.cecere.dlna.domain.Device>();
		for(Device from: upnpService.getRegistry().getDevices()){
			ret.add(deviceConverter.convertTo(from));
		}
		return ret;
	}

}
