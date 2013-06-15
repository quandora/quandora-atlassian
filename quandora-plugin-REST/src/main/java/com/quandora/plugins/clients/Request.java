package com.quandora.plugins.clients;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bstefanescu
 *
 */
public class Request {

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	
	public String method;

	public String url;
	
	public String data;
	
	public Map<String,String> headers;
	
	public Request(String url, String method) {
		this.url = url;
		this.method = method == null ? "GET" : method.toUpperCase();
		headers = new HashMap<String, String>();
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getUrl() {
		return url;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
		
	public Request addHeader(String key, String value) {
		headers.put(key, value);
		return this;
	}
	
	public boolean hasData() {
		return data != null;
	}
	
}
