package com.fluagen.exmaple.cypher.digest;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Apache 消息摘要实现
 * 支持  MD2/MD5/SHA/SHA256/SHA384/SHA512 算法
 */
public class CCDigest {

	private static String md5(String data) {
		return DigestUtils.md5Hex(data);
	}

	private static String sha(String data) {
		return DigestUtils.sha1Hex(data);
	}

	private static String sha256(String data) {
		return DigestUtils.sha256Hex(data);
	}

	private static String sha384(String data) {
		return DigestUtils.sha384Hex(data);
	}

	private static String sha512(String data) {
		return DigestUtils.sha512Hex(data);
	}

	public static void main(String[] args) {
		
		String str = "md5";
		String data = md5(str);
		System.out.println("md5 加密前数据: " + str);
		System.out.println("md5 加密后数据: " + data);
		System.out.println();

		str = "sha";
		data = sha(str);
		System.out.println("sha 加密前数据: " + str);
		System.out.println("sha 加密后数据: " + data);
		System.out.println();

		str = "sha256";
		data = sha256(str);
		System.out.println("sha256 加密前数据: " + str);
		System.out.println("sha256 加密后数据: " + data);
		System.out.println();

		str = "sha384";
		data = sha384(str);
		System.out.println("sha384 加密前数据: " + str);
		System.out.println("sha384 加密后数据: " + data);
		System.out.println();

		str = "sha512";
		data = sha512(str);
		System.out.println("sha512 加密前数据: " + str);
		System.out.println("sha512 加密后数据: " + data);
		System.out.println();
	}
}
