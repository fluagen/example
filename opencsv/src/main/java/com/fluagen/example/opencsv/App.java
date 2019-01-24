package com.fluagen.example.opencsv;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;

public class App {

	public static void main(String[] args) throws IOException {
		
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("lrb000002.csv"), "gbk"));
		/*
		 * 逐行读取
		 */
		String[] strArr = null;
		while ((strArr = reader.readNext()) != null) {
			List<String> list = Arrays.asList(strArr);
			StringBuilder line = new StringBuilder();
			list.forEach(str -> {
				line.append(str);
				line.append(",");
			});
			
			System.out.println(line.toString());
		}

		reader.close();
	}
}