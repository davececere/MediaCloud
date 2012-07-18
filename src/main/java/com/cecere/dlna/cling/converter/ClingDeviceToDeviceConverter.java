package com.cecere.dlna.cling.converter;


import org.teleal.cling.model.meta.Device;

import com.cecere.converter.Converter;

public class ClingDeviceToDeviceConverter implements Converter<Device, com.cecere.dlna.domain.Device> {

	@Override
	public com.cecere.dlna.domain.Device convertTo(Device from) {
		com.cecere.dlna.domain.Device ret = new com.cecere.dlna.domain.Device();
		ret.setName(from.getDisplayString());
		ret.setId(from.getDisplayString());
		return ret;
	}

	@Override
	public Device convertFrom(com.cecere.dlna.domain.Device to) {
		throw new UnsupportedOperationException("not yet supported");
	}

}
