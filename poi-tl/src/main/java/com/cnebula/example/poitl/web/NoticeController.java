package com.cnebula.example.poitl.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnebula.example.poitl.model.NoticeTlModel;
import com.cnebula.example.poitl.service.NoticeTlService;

@RestController
@CrossOrigin
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	NoticeTlService noticeTlService;

	@PostMapping("/tl")
	public String postTl(@RequestBody NoticeTlModel request) throws Exception {
		return noticeTlService.render(request);
	}
	
	@GetMapping("/word/download")
	public String wordDownload(@RequestParam("name") String name, HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException {
		File file = noticeTlService.word(name);
        if(!file.exists()){
            return "下载文件不存在";
        }
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
//        response.addHeader("Content-Disposition", "attachment;fileName="+name );// 设置文件名
        response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(name.getBytes("UTF-8"), "ISO-8859-1") + "\";filename*=UTF-8''" + new String(name.getBytes("UTF-8"), "ISO-8859-1"));


        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "下载失败";
        }
        return "下载成功";
	}
}
