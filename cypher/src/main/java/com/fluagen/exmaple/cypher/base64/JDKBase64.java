package com.fluagen.exmaple.cypher.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;  

/**
 * JDK BASE64编码实现
 *
 */
public class JDKBase64 {
	/** * BASE64解密 * @param key * @return * @throws Exception */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/** * BASE64加密 * @param key * @return * @throws Exception */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	public static void main(String[] args) throws Exception {
		String str = "http://aub.iteye.com/";
		String data = JDKBase64.encryptBASE64(str.getBytes());
		System.out.println("加密前：" + str);
		System.out.println("加密后：" + data);
		byte[] byteArray = JDKBase64.decryptBASE64(data);
		System.out.println("解密后：" + new String(byteArray));
	}
}
