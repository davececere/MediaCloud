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
	
	public void initClingService(){
		// UPnP discovery is asynchronous, we need a callback
		//TODO: move to registry listener subclass
		RegistryListener listener = new RegistryListener() {

			public void remoteDeviceDiscoveryStarted(Registry registry,
					RemoteDevice device) {
				System.out.println(
						"Discovery started: " + device.getDisplayString()
						);
			}

			public void remoteDeviceDiscoveryFailed(Registry registry,
					RemoteDevice device,
					Exception ex) {
				System.out.println(
						"Discovery failed: " + device.getDisplayString() + " => " + ex
						);
			}

			public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
				System.out.println(
						"Remote device available: " + device.getDisplayString()
						);
			}

			public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
				System.out.println(
						"Remote device updated: " + device.getDisplayString()
						);
			}

			public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
				System.out.println(
						"Remote device removed: " + device.getDisplayString()
						);
			}

			public void localDeviceAdded(Registry registry, LocalDevice device) {
				System.out.println(
						"Local device added: " + device.getDisplayString()
						);
			}

			public void localDeviceRemoved(Registry registry, LocalDevice device) {
				System.out.println(
						"Local device removed: " + device.getDisplayString()
						);
			}

			public void beforeShutdown(Registry registry) {
				System.out.println(
						"Before shutdown, the registry has devices: "
								+ registry.getDevices().size()
						);
			}

			public void afterShutdown() {
				System.out.println("Shutdown of registry complete!");

			}
		};
		// This will create necessary network resources for UPnP right away
		System.out.println("Starting Cling...");
		//TODO: extract to shared component with single init search
		upnpService = new UpnpServiceImpl(new UpnpServiceConfiguration(),listener);
		// Send a search message to all devices and services, they should respond soon and populate the registry
		upnpService.getControlPoint().search(new STAllHeader());
		
	}

	//TODO split implementation of device and content repositories
	@Override
	public List<com.cecere.dlna.domain.Device> findAll() {
		List<com.cecere.dlna.domain.Device> ret = new ArrayList<com.cecere.dlna.domain.Device>();
		for(Device from: upnpService.getRegistry().getDevices()){
			ret.add(deviceConverter.convertTo(from));
		}
		return ret;
	}

}
