package com.cecere.dlna.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.teleal.cling.UpnpService;
import org.teleal.cling.controlpoint.ActionCallback;
import org.teleal.cling.model.meta.Device;
import org.teleal.cling.model.meta.Service;
import org.teleal.cling.model.types.ServiceType;
import org.teleal.cling.model.types.UDAServiceType;
import org.teleal.cling.support.model.BrowseFlag;
import org.teleal.cling.support.model.item.Item;

import com.cecere.converter.Converter;
import com.cecere.dlna.cling.CollectBrowseItemsActionCallback;
import com.cecere.dlna.domain.Content;
import com.cecere.repository.ContentRepository;

public class DlnaContentRepository implements ContentRepository {
	private UpnpService upnpService; //hold instance of initialized cling service singleton
	//synchronized for multithreaded cling or repository client access
	private List<Content> contents = Collections.synchronizedList(new ArrayList<Content>()); 
	private Converter<Item,Content> contentConverter;
	
	public DlnaContentRepository(UpnpService upnpService,Converter<Item,Content> contentConverter){
		this.contentConverter = contentConverter;
		this.upnpService = upnpService;
	}
	
	public void initContents(){
		initContentsFromNode("33/3379"); //start with just home movies directory for now
	}
	
	public void initContentsFromNode(String nodeId){
		//get media servers
		ServiceType serviceType = new UDAServiceType("ContentDirectory", 1);  //urn:upnp-org:serviceId:ContentDirectory
		Collection<Device> devices = upnpService.getRegistry().getDevices(serviceType);
		//TODO: check all devices
		Service contentDirectoryService = devices.iterator().next().findService(serviceType);
		
		ActionCallback browseAndCollectAction = new CollectBrowseItemsActionCallback<Content>(contentDirectoryService, nodeId, BrowseFlag.DIRECT_CHILDREN,upnpService.getControlPoint(),this,contentConverter,contents);
		browseAndCollectAction.run();
	}
	
	@Override
	public List<Content> findAll() {
		return Collections.unmodifiableList(contents);
	}

}
