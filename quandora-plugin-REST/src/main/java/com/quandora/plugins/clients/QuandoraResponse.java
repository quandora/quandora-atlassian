package com.quandora.plugins.clients;

import java.util.HashMap;
import java.util.Map;

public class QuandoraResponse {

    public int status;

    public String result;

    public Map<String,String> headers;

    public QuandoraResponse() {
        headers = new HashMap<String, String>();
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getResult() {
        return result;
    }

    public int getStatus() {
        return status;
    }

}
