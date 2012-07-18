package com.cecere.mediacloud.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cecere.dlna.domain.Content;
import com.cecere.dlna.domain.Device;
import com.cecere.dlna.domain.Renderer;
import com.cecere.mediacloud.service.ContentService;
import com.cecere.mediacloud.service.DeviceService;

@Controller
public class DeviceController {
	
	private DeviceService deviceService;
	private ContentService contentService;
	
	public DeviceController(DeviceService deviceService,ContentService contentService){
		this.deviceService = deviceService;
		this.contentService = contentService;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/devices", 
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public @ResponseBody List<Device> getAllDevices() {
		return deviceService.findAllDevices();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/renderers", 
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public @ResponseBody List<Renderer> getAllRenderers() {
		return deviceService.findAllRenderers();
	}
	
	@ResponseStatus(HttpStatus.ACCEPTED)
	@RequestMapping(
			value = "/renderers/{id}", 
			method = RequestMethod.PUT,
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public @ResponseBody Renderer playContentOnRenderer(@PathVariable("id") String rendererName,@RequestBody Renderer rendererToUpdate) {
		
		List<Renderer> renderers = deviceService.findAllRenderers();
		Renderer actualRenderer = null;
		//TODO: move to get renderer by name service method
		for(Renderer r: renderers){
			if(r.getName().equals(rendererName)){
				actualRenderer = r;
				if(rendererToUpdate.getNowPlayingContentId() != null) {
					//TODO: move to getcontent by id service
					List<Content> contents = contentService.findAllContent();
					boolean contentFound = false;
					for(Content c: contents){
						if(c.getId().equals(rendererToUpdate.getNowPlayingContentId())){
							contentFound = true;
							deviceService.playMediaOnRenderer(r, c);
							break;
						}
					}
				}
				break;
			}
		}
		//TODO: change to exception with HttpStatus set appropriately
		if(actualRenderer == null)
			throw new RuntimeException("renderer not found");
		
		return actualRenderer;
	}
	
}
