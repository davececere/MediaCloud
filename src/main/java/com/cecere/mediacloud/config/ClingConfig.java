package com.cecere.mediacloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.teleal.cling.UpnpService;
import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.model.message.header.STAllHeader;
import org.teleal.cling.model.meta.LocalDevice;
import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.registry.Registry;
import org.teleal.cling.registry.RegistryListener;

import com.cecere.dlna.cling.UpnpServiceConfiguration;

@Configuration
public class ClingConfig {

	@Bean
	public UpnpService upnpService() throws InterruptedException{
		// UPnP discovery is asynchronous, we need a callback
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
		UpnpService upnpService = new UpnpServiceImpl(new UpnpServiceConfiguration(),listener);
		// Send a search message to all devices and services, they should respond soon and populate the registry
		upnpService.getControlPoint().search(new STAllHeader());
		
		//sleep 10 seconds here for now to allow discovert to complete before considering this instance created.
		//this should eventually be moved to an async model so we don't sleep to assume work is done
		Thread.sleep(10000);
		return upnpService;
	}
}
