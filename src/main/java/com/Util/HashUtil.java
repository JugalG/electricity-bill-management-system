package com.Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
	public static String hashPassword(String pwd) throws NoSuchAlgorithmException {

		String hashingAlgorithm = "SHA-256";

		MessageDigest digest = MessageDigest.getInstance(hashingAlgorithm);
		byte[] hash = digest.digest(pwd.getBytes(StandardCharsets.UTF_8));

		StringBuilder sb = new StringBuilder();
		for (byte b : hash) {
			sb.append(String.format("%02x", b)); 
		}

		return sb.toString();
	}
}
