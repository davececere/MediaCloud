package com.cecere.spike;


import java.util.Collection;
import java.util.Set;

import org.junit.Test;
import org.teleal.cling.UpnpService;
import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.controlpoint.ActionCallback;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.message.UpnpResponse;
import org.teleal.cling.model.message.header.STAllHeader;
import org.teleal.cling.model.meta.Device;
import org.teleal.cling.model.meta.LocalDevice;
import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.model.meta.Service;
import org.teleal.cling.model.types.ServiceType;
import org.teleal.cling.model.types.UDAServiceType;
import org.teleal.cling.registry.Registry;
import org.teleal.cling.registry.RegistryListener;
import org.teleal.cling.support.avtransport.callback.Play;
import org.teleal.cling.support.avtransport.callback.SetAVTransportURI;
import org.teleal.cling.support.contentdirectory.callback.Browse;
import org.teleal.cling.support.model.BrowseFlag;
import org.teleal.cling.support.model.DIDLContent;


public class ClingTest {

	@Test   //dont run by default since it actually plays content
	public void testListener() throws InterruptedException{
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
		System.out.println("Starting Cling...");
		UpnpService upnpService = new UpnpServiceImpl(listener);

		// Send a search message to all devices and services, they should respond soon
		upnpService.getControlPoint().search(new STAllHeader());

		// Let's wait 10 seconds for them to respond
		System.out.println("Waiting 10 seconds before shutting down...");
		Thread.sleep(10000);
		

		//get media servers
		ServiceType serviceType = new UDAServiceType("ContentDirectory", 1);  //urn:upnp-org:serviceId:ContentDirectory
		Collection<Device> devices = upnpService.getRegistry().getDevices(serviceType);
		Device synology = null;
		for(Device d: devices){
			if(d.getDisplayString().equals("Synology Inc DS411slim"))
				synology = d;
		}
		Service contentDirectoryService = synology.findService(serviceType);
		
		//get renderers
		ServiceType rendererServiceType = new UDAServiceType("AVTransport",1);
		Collection<Device> rendererDevices = upnpService.getRegistry().getDevices(rendererServiceType);
		Service rendererService = rendererDevices.iterator().next().findService(rendererServiceType);
		
		//0 is root
		//21 is music
		//32 is photo
		//33 is video
		//33/3379 is Home Movies
		//33/3380 is Home Movies/2012
		//33/@1384 videoItem hackmatch2
		ActionCallback browseAction = new Browse(contentDirectoryService, "33/3380", BrowseFlag.DIRECT_CHILDREN){
			
			@Override
			public void received(ActionInvocation actionInvocation,
					DIDLContent didl) {
				// TODO Auto-generated method stub
				return;
			}

			@Override
			public void updateStatus(Status status) {
				// TODO Auto-generated method stub
				return;
			}

			@Override
			public void failure(ActionInvocation invocation,
					UpnpResponse operation, String defaultMsg) {
				// TODO Auto-generated method stub
				return;
			}
		};
		browseAction.setControlPoint(upnpService.getControlPoint());
		new Thread(browseAction).start();
		
		/*
		String mediaUri = "http://192.168.1.2:50002/v/NDLNA/1384.m4v"; //pull from VideoItem
		
		ActionCallback setUriAction = new SetAVTransportURI(rendererService,mediaUri){
			@Override
			public void failure(ActionInvocation invocation,
					UpnpResponse operation, String defaultMsg) {
				// TODO Auto-generated method stub
				
			}
			
		};
		setUriAction.setControlPoint(upnpService.getControlPoint());
		new Thread(setUriAction).start();
		
		ActionCallback playAction = new Play(rendererService){

			@Override
			public void failure(ActionInvocation invocation,
					UpnpResponse operation, String defaultMsg) {
				// TODO Auto-generated method stub
				
			}
		};
		playAction.setControlPoint(upnpService.getControlPoint());
		new Thread(playAction).start();
		// Release all resources and advertise BYEBYE to other UPnP devices
		*/
		System.out.println("Stopping Cling...");
		upnpService.shutdown();
	}
}
