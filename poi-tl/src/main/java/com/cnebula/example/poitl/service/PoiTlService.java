package com.cnebula.example.poitl.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.deepoove.poi.XWPFTemplate;

@Service
public class PoiTlService {
	
	public File find(String template) throws FileNotFoundException {
		File dic = ResourceUtils.getFile("classpath:poitl");
		File file = Arrays.stream(dic.listFiles()).filter(f -> {
			return f.getName().equals(template);
		}).findFirst().get();
		
		return file;
	}

	public void render(String template, Map<String,String> data) throws IOException {
		File templateFile = find(template);
		String path = ResourceUtils.getURL("classpath:word").getPath();
		path = path + "/" + templateFile.getName();
		XWPFTemplate.compile(templateFile).render(data).writeToFile(path);
	}
}
