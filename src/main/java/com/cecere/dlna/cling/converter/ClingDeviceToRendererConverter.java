package com.cecere.dlna.cling.converter;

import org.teleal.cling.controlpoint.ControlPoint;
import org.teleal.cling.model.meta.Device;
import org.teleal.cling.model.meta.Service;
import org.teleal.cling.model.types.ServiceType;
import org.teleal.cling.model.types.UDAServiceType;

import com.cecere.converter.Converter;
import com.cecere.dlna.domain.Renderer;

public class ClingDeviceToRendererConverter implements Converter<Device, Renderer> {
	private ControlPoint controlPoint;

	public ClingDeviceToRendererConverter(ControlPoint controlPoint){
		this.controlPoint = controlPoint;
	}
	
	@Override
	public Renderer convertTo(Device from) {
		ServiceType rendererServiceType = new UDAServiceType("AVTransport",1);
		Service rendererService = from.findService(rendererServiceType);
		
		Renderer ret = new Renderer(controlPoint,rendererService);
		ret.setName(from.getDisplayString());
		ret.setId(from.getIdentity().getUdn().getIdentifierString());
		return ret;
	}

	@Override
	public Device convertFrom(Renderer to) {
		throw new UnsupportedOperationException("not yet supported");
	}

}
