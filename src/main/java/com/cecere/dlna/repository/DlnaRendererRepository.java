package com.cecere.dlna.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.teleal.cling.UpnpService;
import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.model.message.header.STAllHeader;
import org.teleal.cling.model.meta.Device;
import org.teleal.cling.model.meta.LocalDevice;
import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.model.meta.Service;
import org.teleal.cling.model.types.ServiceType;
import org.teleal.cling.model.types.UDAServiceType;
import org.teleal.cling.registry.Registry;
import org.teleal.cling.registry.RegistryListener;

import com.cecere.converter.Converter;
import com.cecere.dlna.cling.UpnpServiceConfiguration;
import com.cecere.dlna.domain.Renderer;
import com.cecere.repository.DeviceRepository;
import com.cecere.repository.RendererRepository;

public class DlnaRendererRepository implements RendererRepository{
	private UpnpService upnpService; 
	private Converter<Device, Renderer> rendererConverter;
	
	public DlnaRendererRepository(UpnpService upnpService,Converter<Device,Renderer> rendererConverter){
		this.upnpService = upnpService;
		this.rendererConverter = rendererConverter;
	}
	
	@Override
	public List<Renderer> findAll() {
		List<Renderer> ret = new ArrayList<Renderer>();
		ServiceType rendererServiceType = new UDAServiceType("AVTransport",1);
		for(Device device: upnpService.getRegistry().getDevices(rendererServiceType)){
			ret.add(rendererConverter.convertTo(device));
		}
		return ret;
	}

}
