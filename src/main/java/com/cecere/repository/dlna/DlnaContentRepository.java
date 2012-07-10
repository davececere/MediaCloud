package com.cecere.repository.dlna;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
import org.teleal.cling.support.contentdirectory.callback.Browse;
import org.teleal.cling.support.contentdirectory.callback.Browse.Status;
import org.teleal.cling.support.model.BrowseFlag;
import org.teleal.cling.support.model.DIDLContent;
import org.teleal.cling.support.model.item.Item;

import com.cecere.cling.CollectBrowseItemsActionCallback;
import com.cecere.converter.Converter;
import com.cecere.domain.dlna.Content;
import com.cecere.repository.ReadOnlyTreeRepository;

public class DlnaContentRepository implements ReadOnlyTreeRepository<Content> {
	private UpnpService upnpService; //hold instance of initialized cling service singleton
	//synchronized for multithreaded cling or repository client access
	private List<Content> contents = Collections.synchronizedList(new ArrayList<Content>()); 
	private Converter<Item,Content> converter;
	
	public DlnaContentRepository(Converter<Item,Content> converter){
		this.converter = converter;
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
		upnpService = new UpnpServiceImpl(listener);
		// Send a search message to all devices and services, they should respond soon and populate the registry
		upnpService.getControlPoint().search(new STAllHeader());
		
	}
	
	public void initContents(){
		//get media servers
		ServiceType serviceType = new UDAServiceType("ContentDirectory", 1);  //urn:upnp-org:serviceId:ContentDirectory
		Collection<Device> devices = upnpService.getRegistry().getDevices(serviceType);
		Service contentDirectoryService = devices.iterator().next().findService(serviceType);
		
		ActionCallback browseAndCollectAction = new CollectBrowseItemsActionCallback<Content>(contentDirectoryService, "33/3380", BrowseFlag.DIRECT_CHILDREN,upnpService.getControlPoint(),converter,contents);
		browseAndCollectAction.run();
	}
	
	@Override
	public List<Content> findAllChildren() {
		return Collections.unmodifiableList(contents);
	}

}
