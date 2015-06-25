package com.fluagen.example.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileOutputStreamExample {


	public static void test1() throws IOException {
		String path = FileOutputStreamExample.class.getResource("").getPath();
		File f = new File(path + File.separator+"test.txt");
        OutputStream out=new FileOutputStream(f);//如果文件不存在会自动创建,但不会自动创建目录
        String str="Hello World";
        byte[] b=str.getBytes();
        for(int i=0;i<b.length;i++){
            out.write(b[i]);
        }
        out.close();
	}
	public static void test2() throws IOException {
		String path = FileOutputStreamExample.class.getResource("").getPath();
		File f = new File(path + File.separator + "test.txt");
		OutputStream out = new FileOutputStream(f, true);//追加内容
		String str = "\r\nHello World";
		byte[] b = str.getBytes();
		for (int i = 0; i < b.length; i++) {
			out.write(b[i]);
		}
		out.close();
	}
	
	public static void main(String[] args){
		try {
			test2();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
