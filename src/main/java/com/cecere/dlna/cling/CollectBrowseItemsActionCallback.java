package com.cecere.dlna.cling;

import java.util.Collection;

import org.teleal.cling.controlpoint.ControlPoint;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.message.UpnpResponse;
import org.teleal.cling.model.meta.Service;
import org.teleal.cling.support.contentdirectory.callback.Browse;
import org.teleal.cling.support.model.BrowseFlag;
import org.teleal.cling.support.model.DIDLContent;
import org.teleal.cling.support.model.container.Container;
import org.teleal.cling.support.model.item.Item;

import com.cecere.converter.Converter;
import com.cecere.dlna.repository.DlnaContentRepository;

public class CollectBrowseItemsActionCallback<T> extends Browse {
	private Converter<Item,T> converter;
	private Collection<T> collection;
	private DlnaContentRepository contentRepository;

	public CollectBrowseItemsActionCallback(Service service,String containerId, BrowseFlag flag,ControlPoint controlPoint,DlnaContentRepository contentRepository,Converter<Item,T> converter,Collection<T> collection) {
		super(service, containerId, flag);
		setControlPoint(controlPoint); //not sure why this isn't set autmoatically since super class requires it.
		this.converter = converter;
		this.collection = collection;
		this.contentRepository = contentRepository;
	}

	@Override
	public void received(ActionInvocation actionInvocation, DIDLContent didl) {
		//collect the items
		for(Item item: didl.getItems()){
			collection.add(converter.convertTo(item));
		}
		for(Container container: didl.getContainers()){
			System.out.println("searching container "+container.getTitle());
			contentRepository.initContentsFromNode(container.getId());
		}
	}

	@Override
	public void updateStatus(Status status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failure(ActionInvocation invocation, UpnpResponse operation,
			String defaultMsg) {
		// TODO Auto-generated method stub

	}

}
