package com.fluagen.example.httpclient;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MD5Util {

	public static String md5(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes("UTF-8"));
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext.toLowerCase();
    }
	
	public static String md5(Map<String, String> params)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		StringBuilder raw = new StringBuilder();
		params.entrySet().stream().sorted(Map.Entry.<String,String>comparingByKey()).forEachOrdered(e -> {
			raw.append(e.getKey()).append(e.getValue());
		});
		return MD5Util.md5(raw.toString());
	}
	
	public static String getSign(Map<String, String> params) throws Exception {
		String secret = "secret.api.a000001.las.001";
		StringBuilder raw = new StringBuilder(secret);
		raw.append(signStr(params));
		raw.append(secret);
		String str = MD5Util.md5(raw.toString());
		return str;
	}

	private static String signStr(Map<String, String> params) {
		StringBuilder raw = new StringBuilder();
		params.entrySet().stream().sorted(Map.Entry.<String, String>comparingByKey()).forEachOrdered(e -> {
			raw.append(e.getKey()).append(e.getValue());
		});
		return raw.toString();
	}
	
	public static void main(String[] args) throws Exception{
		Map<String,String> params = new HashMap<String,String>();
		long timestamp = System.currentTimeMillis();
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6aGFuZ3NhbiIsInVzZXJfaWQiOiI3MjYxZWNhYWUzYTc0ZGM2OGI0NjhlMTJhNzBiMWFlYyIsInRlbmFudCI6InRlc3RsaWIifQ.1QA4Svhe1Bks2pte82rBDpurXT7dx1t2GPjkKGMMiOAmZyN4-VVCtUHZf0lxqaVMcHY9QBsbvzUmlIfMkzOxfg";
		
		params.put("timestamp", timestamp+"");
		params.put("client_id", "api.a000001.las.001");
		params.put("tenant_id", "testlib");
		params.put("access_token", token);
		
		String sign = getSign(params);
		
		System.out.println("timestamp ["+timestamp+"]");
		
		System.out.println("sign ["+sign+"]");
		
	}
	
	
}