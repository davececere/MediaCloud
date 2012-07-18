package com.cecere.dlna.domain;

import org.teleal.cling.controlpoint.ActionCallback;
import org.teleal.cling.controlpoint.ControlPoint;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.message.UpnpResponse;
import org.teleal.cling.model.meta.Service;
import org.teleal.cling.support.avtransport.callback.Play;
import org.teleal.cling.support.avtransport.callback.SetAVTransportURI;

/**
 * Representation of a Device that can render contents
 * 
 * @author dave
 *
 */
public class Renderer extends Device {

	private ControlPoint controlPoint;
	private Service rendererService;
	//has to be flat due to sencha limitation on one-to-one mappings
	//this is annoying since its in this class instead of a subresource due to another sencha limition with using subresource rest calls
	private String nowPlayingContentId; 
	
	public Renderer(ControlPoint controlPoint, Service rendererService){
		this.controlPoint = controlPoint;
		this.rendererService = rendererService;
	}
	
	public void playContent(Content content){
		String mediaUri = content.getMediaUri();//"http://192.168.1.2:50002/v/NDLNA/1384.m4v"; //pull from VideoItem
		
		ActionCallback setUriAction = new SetAVTransportURI(rendererService,mediaUri){
			@Override
			public void failure(ActionInvocation invocation,UpnpResponse operation, String defaultMsg) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		setUriAction.setControlPoint(controlPoint);
		new Thread(setUriAction).start(); //TODO: use threadpool
		
		ActionCallback playAction = new Play(rendererService){
			@Override
			public void failure(ActionInvocation invocation,
					UpnpResponse operation, String defaultMsg) {
				// TODO Auto-generated method stub
				
			}
		};
		playAction.setControlPoint(controlPoint);
		new Thread(playAction).start(); //TODO: use threadpool
	}

	public String getNowPlayingContentId() {
		return nowPlayingContentId;
	}

	public void setNowPlayingContentId(String nowPlayingContentId) {
		this.nowPlayingContentId = nowPlayingContentId;
	}
}
