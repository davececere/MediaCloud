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
	
	private DeviceService service;
	
	public DeviceController(DeviceService service){
		this.service = service;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/device", 
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public @ResponseBody List<Device> getAllDevices() {
		return service.findAllDevices();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/renderer", 
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public @ResponseBody List<Renderer> getAllRenderers() {
		return service.findAllRenderers();
	}
	
	@ResponseStatus(HttpStatus.ACCEPTED)
	@RequestMapping(
			value = "/renderer/{name}/nowPlaying", 
			method = RequestMethod.PUT,
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public @ResponseBody Content playContentOnRenderer(@PathVariable("name") String rendererName,@RequestBody Content content) {
		List<Renderer> renderers = service.findAllRenderers();
		//move to get renderer by name service method
		boolean found = false;
		for(Renderer r: renderers){
			if(r.getName().equals(rendererName)){
				service.playMediaOnRenderer(r, content);
				found = true;
				break;
			}
		}
		//TODO: change to exception with HttpStatus set appropriately
		if(!found)
			throw new RuntimeException("renderer not found");
		return content;
	}
	
}
