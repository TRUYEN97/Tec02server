package com.tec02.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.util.DigestUtils;

public class Util {

	@SuppressWarnings("rawtypes")
	public static boolean isNullorEmpty(List list) {
		return list == null || list.isEmpty();
	}

	public static boolean isNullorEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static String subInLength(String str, int length) {
		if (str == null) {
			return null;
		}
		if (str.length() > length) {
			return str.substring(0, length);
		}
		return str;
	}

	public static String md5File(byte[] bytes) {
		if(bytes == null) {
			return null;
		}
		return DigestUtils.md5DigestAsHex(bytes);
	}

	public static String md5File(InputStream inputStream) {
		try {
			return DigestUtils.md5DigestAsHex(inputStream);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String md5File(String filePath) {
		try (FileInputStream input = new FileInputStream(filePath)){
			return md5File(input);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
