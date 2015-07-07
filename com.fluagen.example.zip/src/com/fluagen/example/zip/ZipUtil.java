package com.fluagen.example.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	/**
	 * 
	 * @param srcPath		源文件或路径
	 * @param targetPath	目标目录
	 * @throws FileNotFoundException 
	 */
	public void compress(String srcPath,String targetPath,String targetName) throws Exception{
		File f = new File(targetPath);
		if(!f.exists()){
			f.mkdirs();
		}
		if(!f.isDirectory()){
			return;
		}
		
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(targetPath + File.separator
				+ targetName)));
		File srcFile = new File(srcPath);
		String target = "";
		if(srcFile.isFile()){
			target = getFileNameWithNotExtension(srcFile);
		}else{
			/**
			 *  ZipEntry对象： 
   			 *	/结尾：文件夹  
			 *	没有/结尾：文件
			 */
			target = srcFile.getName()+"/";
		}
		zip(srcFile,zos,target);
	}
	
	private String getFileNameWithNotExtension(File file) {
        String fileName = file.getName();
        String name = fileName;
        if(fileName.lastIndexOf(".") > 0){
        	name = fileName.substring(0,fileName.lastIndexOf("."));
        }
        return name;
    }
	
	public void zip(File srcFile,ZipOutputStream zos,String target) throws IOException{
		
		if(srcFile.isFile()){
			zos.putNextEntry(new ZipEntry(target));
			FileInputStream fis = new FileInputStream(srcFile);
			int n = 0;
			byte[] buffer = new byte[1024];
			while((n = fis.read(buffer)) > 0){  
	            zos.write(buffer,0,n);  
	        }
			fis.close();
		}else{
			File[] files = srcFile.listFiles();
			if(files == null){
				return;
			}
			for(File f : files){
				String nt = target + f.getName();
				nt = nt.replace("\\", "/");
				if(f.isDirectory() && !nt.endsWith("/")){
					nt +=  "/";
				}
				zip(f,zos,nt);
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		ZipUtil zu = new ZipUtil();
		String srcPath = "E:\\book";
		String targetPath = "E:\\book2";
		String targetName = "test.zip";
		zu.compress(srcPath, targetPath, targetName);
	}
	
}
