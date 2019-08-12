package com.fluagen.exmaple.cypher.digest;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * JDK 消息摘要实现
 * 支持  MD2/MD5/SHA/SHA256/SHA384/SHA512 算法
 */
public class JDKDigest {

	/**
	 * 根据给定摘要算法创建一个消息摘要实例
	 * 
	 * @param algorithm
	 *            摘要算法名
	 * @return 消息摘要实例
	 * @see MessageDigest#getInstance(String)
	 * @throws RuntimeException
	 *             当 {@link java.security.NoSuchAlgorithmException} 发生时
	 */
	static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 获取 MD5 消息摘要实例
	 * 
	 * @return MD5 消息摘要实例
	 * @throws RuntimeException
	 *             当 {@link java.security.NoSuchAlgorithmException} 发生时
	 */
	private static MessageDigest getMd5Digest() {
		return getDigest("MD5");
	}

	/**
	 * 获取 SHA-1 消息摘要实例
	 * 
	 * @return SHA-1 消息摘要实例
	 * @throws RuntimeException
	 *             当 {@link java.security.NoSuchAlgorithmException} 发生时
	 */
	private static MessageDigest getShaDigest() {
		return getDigest("SHA");
	}

	/**
	 * 获取 SHA-256 消息摘要实例
	 * 
	 * @return SHA-256 消息摘要实例
	 * @throws RuntimeException
	 *             当 {@link java.security.NoSuchAlgorithmException} 发生时
	 */
	private static MessageDigest getSha256Digest() {
		return getDigest("SHA-256");
	}

	/**
	 * 获取 SHA-384 消息摘要实例
	 * 
	 * @return SHA-384 消息摘要实例
	 * @throws RuntimeException
	 *             当 {@link java.security.NoSuchAlgorithmException} 发生时
	 */
	private static MessageDigest getSha384Digest() {
		return getDigest("SHA-384");
	}

	/**
	 * 获取 SHA-512 消息摘要实例
	 * 
	 * @return SHA-512 消息摘要实例
	 * @throws RuntimeException
	 *             当 {@link java.security.NoSuchAlgorithmException} 发生时
	 */
	private static MessageDigest getSha512Digest() {
		return getDigest("SHA-512");
	}

	/**
	 * 使用MD5消息摘要算法计算消息摘要
	 * 
	 * @param data
	 *            做消息摘要的数据
	 * @return 消息摘要（长度为16的字节数组）
	 */
	public static byte[] encodeMD5(byte[] data) {
		return getMd5Digest().digest(data);
	}

	/**
	 * 使用MD5消息摘要算法计算消息摘要
	 * 
	 * @param data
	 *            做消息摘要的数据
	 * @return 消息摘要（长度为32的十六进制字符串）
	 */
	public static String encodeMD5Hex(byte[] data) {
		return Hex.encodeHexString(encodeMD5(data));
	}
	
	public static String md5(byte[] data){
		MessageDigest md5 = getMd5Digest();
		md5.update(data);
		//digest() 方法返回值是一个字节数组类型的 16 位长度的哈希值(16位字节数组)
		byte[] data2 = md5.digest();
		
		BigInteger bigInt = new BigInteger(1, data2);
		
		//转换十六进制字符串
		String rst = bigInt.toString(16);
		return rst;
	}

	/**
	 * 使用SHA-1消息摘要算法计算消息摘要
	 * 
	 * @param data
	 *            做消息摘要的数据
	 * @return SHA-1消息摘要（长度为20的字节数组）
	 */
	public static byte[] encodeSHA(byte[] data) {
		return getShaDigest().digest(data);
	}

	/**
	 * 使用SHA-1消息摘要算法计算消息摘要
	 * 
	 * @param data
	 *            做消息摘要的数据
	 * @return SHA-1消息摘要（长度为40的十六进制字符串）
	 */
	public static String encodeSHAHex(byte[] data) {
		return Hex.encodeHexString(encodeSHA(data));
	}
	
	public static String sha(byte[] data){
		MessageDigest sha = getShaDigest();
		sha.update(data);
		byte[] data2 = sha.digest();
		
		BigInteger bigInt = new BigInteger(1, data2);
		String rst = bigInt.toString(16);
		return rst;
	}

	/**
	 * 使用SHA-256消息摘要算法计算消息摘要
	 * 
	 * @param data
	 *            做消息摘要的数据
	 * @return SHA-256消息摘要（长度为32的字节数组）
	 */
	public static byte[] encodeSHA256(byte[] data) {
		return getSha256Digest().digest(data);
	}

	/**
	 * 使用SHA-256消息摘要算法计算消息摘要
	 * 
	 * @param data
	 *            做消息摘要的数据
	 * @return SHA-256消息摘要（长度为64的十六进制字符串）
	 */
	public static String encodeSHA256Hex(byte[] data) {
		return Hex.encodeHexString(encodeSHA256(data));
	}
	
	public static String sha256(byte[] data){
		MessageDigest sha256 = getSha256Digest();
		sha256.update(data);
		byte[] data2 = sha256.digest();
		
		BigInteger bigInt = new BigInteger(1, data2);
		String rst = bigInt.toString(16);
		return rst;
	}

	/**
	 * 使用SHA-384消息摘要算法计算消息摘要
	 * 
	 * @param data
	 *            做消息摘要的数据
	 * @return SHA-384消息摘要（长度为43的字节数组）
	 */
	public static byte[] encodeSHA384(byte[] data) {
		return getSha384Digest().digest(data);
	}

	/**
	 * 使用SHA-384消息摘要算法计算消息摘要
	 * 
	 * @param data
	 *            做消息摘要的数据
	 * @return SHA-384消息摘要（长度为86的十六进制字符串）
	 */
	public static String encodeSHA384Hex(byte[] data) {
		return Hex.encodeHexString(encodeSHA384(data));
	}
	
	public static String sha384(byte[] data){
		MessageDigest sha384 = getSha384Digest();
		sha384.update(data);
		byte[] data2 = sha384.digest();
		
		BigInteger bigInt = new BigInteger(1, data2);
		String rst = bigInt.toString(16);
		return rst;
	}

	/**
	 * 使用SHA-512消息摘要算法计算消息摘要
	 * 
	 * @param data
	 *            做消息摘要的数据
	 * @return SHA-512消息摘要（长度为64的字节数组）
	 */
	public static byte[] encodeSHA512(byte[] data) {
		return getSha512Digest().digest(data);
	}

	/**
	 * 使用SHA-512消息摘要算法计算消息摘要
	 * 
	 * @param data
	 *            做消息摘要的数据
	 * @return SHA-512消息摘要（长度为128的十六进制字符串）
	 */
	public static String encodeSHA512Hex(byte[] data) {
		return Hex.encodeHexString(encodeSHA512(data));
	}
	
	public static String sha512(byte[] data){
		MessageDigest sha512 = getSha512Digest();
		sha512.update(data);
		byte[] data2 = sha512.digest();
		
		BigInteger bigInt = new BigInteger(1, data2);
		String rst = bigInt.toString(16);
		return rst;
	}
	
	public static void main(String[] args){
		String str = "md5";
		
		String data = encodeMD5Hex(str.getBytes(StandardCharsets.UTF_8));
		String data2 = md5(str.getBytes(StandardCharsets.UTF_8));
		
		System.out.println("md5 加密前数据: "+str);
		System.out.println("md5 加密后数据: "+data);
		System.out.println("md5 加密后数据: "+data2);
		System.out.println();
		
		str = "sha";
		data = encodeSHAHex(str.getBytes(StandardCharsets.UTF_8));
		data2 = sha(str.getBytes(StandardCharsets.UTF_8));
		
		System.out.println("sha 加密前数据: "+str);
		System.out.println("sha 加密后数据: "+data);
		System.out.println("sha 加密后数据: "+data2);
		System.out.println();
		
		str = "sha256";
		data = encodeSHA256Hex(str.getBytes(StandardCharsets.UTF_8));
		data2 = sha256(str.getBytes(StandardCharsets.UTF_8));
		
		System.out.println("sha256 加密前数据: "+str);
		System.out.println("sha256 加密后数据: "+data);
		System.out.println("sha256 加密后数据: "+data2);
		System.out.println();
		
		str = "sha384";
		data = encodeSHA384Hex(str.getBytes(StandardCharsets.UTF_8));
		data2 = sha384(str.getBytes(StandardCharsets.UTF_8));
		
		System.out.println("sha384 加密前数据: "+str);
		System.out.println("sha384 加密后数据: "+data);
		System.out.println("sha384 加密后数据: "+data2);
		System.out.println();
		
		str = "sha512";
		data = encodeSHA512Hex(str.getBytes(StandardCharsets.UTF_8));
		data2 = sha512(str.getBytes(StandardCharsets.UTF_8));
		
		System.out.println("sha512 加密前数据: "+str);
		System.out.println("sha512 加密后数据: "+data);
		System.out.println("sha512 加密后数据: "+data2);
		System.out.println();
	}

}
