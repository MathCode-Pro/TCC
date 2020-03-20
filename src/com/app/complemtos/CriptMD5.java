package com.app.complemtos;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptMD5 {

	public static String md5(String senha) {
		String sen = "";
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md5.digest(senha.getBytes()));
		sen = hash.toString(16);
		return sen;
	}

}
