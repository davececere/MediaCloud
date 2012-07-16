package com.cecere.mediacloud.service;

import java.util.List;

import com.cecere.dlna.domain.Device;
import com.cecere.repository.Repository;

public class DeviceServiceImpl implements DeviceService {

	
	private Repository<Device> deviceRepository;
	
	public DeviceServiceImpl(Repository<Device> repository){
		deviceRepository = repository;
	}
	
	@Override
	public List<Device> findAll() {
		return deviceRepository.findAll();
	}

}
