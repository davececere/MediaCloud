package com.cecere.mediacloud.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cecere.dlna.domain.Content;
import com.cecere.mediacloud.service.ContentService;

@Controller
public class ContentController {
	
	private ContentService service;
	
	public ContentController(ContentService service){
		this.service = service;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/contents", 
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
	public @ResponseBody List<Content> getAllContents() {
		return service.findAllContent();
	}
}
