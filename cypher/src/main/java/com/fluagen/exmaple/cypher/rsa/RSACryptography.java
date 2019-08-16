package com.fluagen.exmaple.cypher.rsa;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * 利用KeyPairGenerator直接生成公钥和密钥对
 * 
 * 一般私钥保留给服务端，公钥交给客户端
 *
 */
public class RSACryptography {

	public static String data = "hello world";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		KeyPair keyPair = genKeyPair(1024);

		// 获取公钥，并以base64格式打印出来
		PublicKey publicKey = keyPair.getPublic();
		System.out.println("公钥：" + new String(Base64.getEncoder().encode(publicKey.getEncoded())));

		// 获取私钥，并以base64格式打印出来
		PrivateKey privateKey = keyPair.getPrivate();
		System.out.println("私钥：" + new String(Base64.getEncoder().encode(privateKey.getEncoded())));

		// 公钥加密
		byte[] encryptedBytes = encrypt(data.getBytes(), publicKey);
		System.out.println("加密后：" + new String(encryptedBytes));

		// 私钥解密
		byte[] decryptedBytes = decrypt(encryptedBytes, privateKey);
		System.out.println("解密后：" + new String(decryptedBytes));
	}

	// 生成密钥对
	public static KeyPair genKeyPair(int keyLength) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(keyLength);
		return keyPairGenerator.generateKeyPair();
	}

	// 公钥加密
	public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(content);
	}

	// 私钥解密
	public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(content);
	}

}
