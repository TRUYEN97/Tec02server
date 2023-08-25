package com.tec02.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.util.DigestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

	@SuppressWarnings("rawtypes")
	public static boolean isNullorEmpty(List list) {
		return list == null || list.isEmpty();
	}

	public static boolean isNullorEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static void checkFilePath(String fileName, String filePath) {
		checkFileName(fileName);
		checkDir(filePath);
	}

	public static void checkFileName(String fileName) {
		if (fileName == null || fileName.isBlank() || !fileName.matches("^(?!\\.|\\s)(?!.*[\\\\/:*?\"<>|]).*(?<!\\.|\\s)$")) {
			throw new RuntimeException("Invalid file name");
		}
	}
	
	public static String objectToString(Object object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}
	
	public static String objectToHearderElements(Object object) {
		try {
			@SuppressWarnings("rawtypes")
			Map map = convertTo(object, Map.class);
			StringBuilder builder = new StringBuilder();
			for (Object key : map.keySet()) {
				Object value = map.get(key);
				if(value == null) {
					continue;
				}
				builder.append(key).append("=").append(value).append(";");
			}
			builder.deleteCharAt(builder.length()-1);
			return builder.toString();
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}
	public static <T> T convertTo(Object object, Class<T> clazz) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(object, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}


	public static boolean isNewVersionGreaterThanOldVersion(String version, String oldVersion) {
		if (version.equals(oldVersion) || isInvalidVersion(version) || isInvalidVersion(oldVersion)) {
			return false;
		}
		String[] vOldElems = oldVersion.split("\\.");
		String[] vNewElems = version.split("\\.");
		for (int i = 0; i < 3; i++) {
			int nV = Integer.valueOf(vNewElems[i]);
			int oV = Integer.valueOf(vOldElems[i]);
			if (nV > oV) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInvalidVersion(String version) {
		return version == null || !version.matches("^[0-9]+\\.[0-9]+\\.[0-9]+$");
	}

	public static void checkDir(String filePath) {
		if (filePath != null && !filePath.isBlank() && !filePath.matches("^(?!.*[<>:\"|?*]).+$")) {
			throw new RuntimeException("Invalid directory");
		}
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
