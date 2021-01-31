package com.cnebula.example.poitl.service;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.cnebula.example.poitl.model.NoticeTlModel;
import com.deepoove.poi.XWPFTemplate;

@Service
public class NoticeTlService {

	public String render(NoticeTlModel data) throws Exception{
		String word = "通知.docx";
		File template = ResourceUtils.getFile("classpath:poitl/notice_tl.docx");
		String path = ResourceUtils.getURL("classpath:word").getPath();
		path = path + "/"+word;
		data.text = data.text.replaceAll("\n\n", "\r\n");
		XWPFTemplate.compile(template).render(data).writeToFile(path);
		return word;
	}
	
	public File word(String name) throws FileNotFoundException {
		String path = ResourceUtils.getURL("classpath:word").getPath();
		path = path + "/" + name;
		File file = new File(path);
		return file;
	}
}
