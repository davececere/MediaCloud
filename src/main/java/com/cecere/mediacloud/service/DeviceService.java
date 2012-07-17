package com.cecere.mediacloud.service;

import java.util.List;

import com.cecere.dlna.domain.Content;
import com.cecere.dlna.domain.Device;
import com.cecere.dlna.domain.Renderer;

public interface DeviceService {
	public List<Device> findAllDevices();
	public List<Renderer> findAllRenderers();
	public void playMediaOnRenderer(Renderer renderer, Content content);
}
