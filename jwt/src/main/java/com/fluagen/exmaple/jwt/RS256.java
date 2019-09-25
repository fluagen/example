package com.fluagen.exmaple.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class RS256 {

	public static void main(String[] args) throws NoSuchAlgorithmException {

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Algorithm algorithm = Algorithm.RSA256(null, privateKey);
		Algorithm algorithm2 = Algorithm.RSA256(publicKey, null);
		
//		LocalDate d = LocalDate.now();
//		LocalTime t = LocalTime.of(18, 16, 00);
//		LocalDateTime now = LocalDateTime.of(d, t);
//		ZoneId zoneId = ZoneId.systemDefault();
//      ZonedDateTime zdt = now.atZone(zoneId);
//      Date date = Date.from(zdt.toInstant());
		
		String token = JWT.create().withIssuer("auth0").sign(algorithm);
		System.out.println(token);

		
		JWTVerifier verifier = JWT.require(algorithm2).withIssuer("auth0").build(); 
		DecodedJWT jwt = verifier.verify(token);
		String issuer = jwt.getIssuer();
		
		System.out.println(issuer);
	}
}
