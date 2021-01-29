package com.cnebula.example.poitl.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cnebula.example.poitl.model.PoiTLModel;
import com.cnebula.example.poitl.service.PoiTlService;

@RestController
@RequestMapping("/poitl")
public class PoiTlController {
	
	@Autowired
	PoiTlService poiTlService;

	@PostMapping("/render")
	public void postTl(@RequestBody PoiTLModel request) throws IOException {
		poiTlService.render(request.template, request.data);
	}
}
