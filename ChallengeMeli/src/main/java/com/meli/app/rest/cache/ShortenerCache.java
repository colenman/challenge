package com.meli.app.rest.cache;

import java.util.HashMap;
import java.util.Map;

public class ShortenerCache {
	
	private final static String URL_BASE = "URL_BASE";
	
	private final static Map<String, String> cacheLtoS = new HashMap<String, String>();
	private final static Map<String, String> cacheStoL = new HashMap<String, String>();
	
	private final static Map<String, String> properties = new HashMap<String, String>();
	
	private static int secuencia = 1;
	

	public static String getUrlShort(String url) {
		String value = cacheLtoS.get(url);
		if (value == null) {
			
				value = toProcessUrl(url);
				cacheLtoS.put(url, value);
				cacheStoL.put(value, url);	
						
		}
		return value;
	}
	
	public static String getUrlOriginal(String url) {
		String value = cacheStoL.get(url);
		if (value == null) {
			value = cacheStoL.get(getUrlBase()+url);
		}
		return value;
	}
	
	public static void deleteUrlShort(String id) {
		String urlOriginal = getUrlOriginal(getUrlBase()+id);
		cacheLtoS.remove(urlOriginal);
		cacheStoL.remove(getUrlBase()+id);
	}
	
	

	private static String toProcessUrl(String url) {
		return getUrlBase() + (convertIntToAZ(secuencia++));
	}

	private static String convertIntToAZ(int num) {
		StringBuilder sb = new StringBuilder();
		while (num > 0) {
			int rem = num % 27;
			
			char c = (char) ('A' + rem-1);
			if(rem == 0) {
				sb.append("0");	
			}else {
				sb.append(c);
			}
			
			num /= 27;
		}
		return sb.reverse().toString();
	}
	
	public static void main(String[] args) {
		for(int i = 2000;i<2100;i++) {
			System.out.println(i+": -> "+convertIntToAZ(i));	
		}
		
	}
	
	private static String getUrlBase() {
		return properties.get(URL_BASE);
	}
	
	public static void addPropertieUrlBase(String ipPublica) {
		properties.put(URL_BASE, "http://"+ipPublica+":8080/meli/");
	}
}
