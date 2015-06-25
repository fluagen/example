package com.fluagen.example.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileInputStreamExample {

	public static void test1() throws IOException{
		String path = FileInputStreamExample.class.getResource("").getPath();
		File f = new File(path + File.separator+"test.txt");
		InputStream in=new FileInputStream(f);
		byte[] b=new byte[(int) f.length()];
		in.read(b);
		in.close();
		System.out.println(new String(b));
	}
	
	public static void test2() throws IOException {
		String path = FileInputStreamExample.class.getResource("").getPath();
		File f = new File(path + File.separator+"test.txt");
		InputStream in=new FileInputStream(f);
        byte[] b=new byte[1024];
        int temp=0;
        int len=0;
        while((temp=in.read())!=-1){//-1为文件读完的标志
            b[len]=(byte) temp;
            len++;
        }
        in.close();
        System.out.println(new String(b,0,len));
    }
	
	public static void main(String[] args){
		try {
			test1();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
