package com.fluagen.exmaple.cypher.des;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * DES Coder<br/>
 * secret key length: 56 bit, default: 56 bit<br/>
 * mode: ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/>
 * padding: Nopadding/PKCS5Padding/ISO10126Padding/
 * 
 */
public class DESCoder {

	/**
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "DES";

	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
	// private static final String DEFAULT_CIPHER_ALGORITHM =
	// "DES/ECB/ISO10126Padding";

	/**
	 * 初始化密钥
	 * 
	 * @return byte[] 密钥
	 * @throws Exception
	 */
	public static byte[] initSecretKey() throws Exception {
		// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 初始化此密钥生成器，使其具有确定的密钥大小
		kg.init(56);
		// 生成一个密钥
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		// 实例化DES密钥规则
		DESKeySpec dks = new DESKeySpec(key);
		// 实例化密钥工厂
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		// 生成密钥
		SecretKey secretKey = skf.generateSecret(dks);
		return secretKey;
	}

//  ****** 加密 ******
	
	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            二进制密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            二进制密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

//  ****** 解密 ******
	
	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            二进制密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            二进制密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	private static String showByteArray(byte[] data) {
		if (null == data) {
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for (byte b : data) {
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		// byte[] key = initSecretKey();
//		byte[] key = "123456718".getBytes();
//		System.out.println("key：" + Hex.encodeHexString(key));
//
//		Key k = toKey(key);
//
//		String data = "DES数据";
//		System.out.println("加密前数据: string:" + data);
//		System.out.println("加密前数据: byte[]:" + showByteArray(data.getBytes()));
//		System.out.println();
//		byte[] encryptData = encrypt(data.getBytes(), k);
//		System.out.println("加密后数据: byte[]:" + showByteArray(encryptData));
//		System.out.println("加密后数据: hexStr:" + Hex.encodeHexString(encryptData));
//		System.out.println();
//		byte[] decryptData = decrypt(encryptData, k);
//		System.out.println("解密后数据: byte[]:" + showByteArray(decryptData));
//		System.out.println("解密后数据: string:" + new String(decryptData));
		
		
		/**
		 * 密钥长度要求56位 + 8位奇偶校验位，共64位长度
		 * 换算成字节是8个字节
		 * key中只有8个字节有用，如果使用前面8个，则第8个字节后面的字符不影响加密解密；
		 * 所以 “keyText = 12345678 与 keyText = 12345678abc“ 的加密解密效果是一样的
		 */
		String keyText = "12345678";
		//keyText = "12345678abc";
		byte[] key = keyText.getBytes(StandardCharsets.UTF_8);
		
		Key k = toKey(key);
		String data = "DES数据";
		
		System.out.println("密钥："+keyText);
		System.out.println("密钥：hex "+Hex.encodeHexString(key));
		System.out.println();
		
		byte[] encryptData = encrypt(data.getBytes(StandardCharsets.UTF_8), k);
		System.out.println("加密前数据："+data);
		System.out.println("加密后数据："+Hex.encodeHexString(encryptData));
		System.out.println();
		
		byte[] decryptData = decrypt(encryptData, k);
		System.out.println("解密后数据: " + new String(decryptData));
		
	}
}
