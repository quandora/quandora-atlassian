package com.quandora.plugins.clients;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.quandora.plugins.REST.Admin.ConfigResource.Config;
import com.sun.jersey.core.util.Base64;


/**
 * @author Nicolas Joseph
 *
 */
public class RestClient{

    protected Config config;

    public RestClient(Config config){
        this.config=config;
    }

    /**
     * The default implementation use the Java URL
     * @param request
     * @return
     */
    public QuandoraResponse execute(Request request) throws Exception {
        URL url = new URL((request).getUrl());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoOutput(request.hasData());
        conn.setDoInput(true);
        conn.setUseCaches(false);
        for (Map.Entry<String,String> entry : request.getHeaders().entrySet()) {
            conn.addRequestProperty(entry.getKey(), entry.getValue());
        }
        if (request.hasData()) {
            OutputStream out = conn.getOutputStream();
            out.write(request.data.getBytes());
            out.flush();
        }
        QuandoraResponse resp = new QuandoraResponse();
        resp.status = conn.getResponseCode();
        String ctype = conn.getContentType();
        String charset = "UTF-8";
        if (ctype != null) {
            int i = ctype.indexOf("charset");
            if (i > -1) {
                i += "charset".length();
                //ctype.indexOf(i, '=')
            }
        }
        InputStream in = conn.getInputStream();
        InputStreamReader reader = new InputStreamReader(in, charset);

        resp.result = readAll(reader, 4096);
        //TODO get headers conn.get
        return resp;
    }

    public static String readAll(Reader reader, int bufSize) throws IOException {
        StringBuilder buf = new StringBuilder();
        int r = -1;
        char[] chars = new char[bufSize];
        try {
            while ((r = reader.read(chars)) > -1) {
                if (r > 0) {
                    buf.append(chars, 0, r);
                }
            }
            return buf.toString();
        } finally {
            reader.close();
        }
    }

    public Request createRequest(String url) {
        return createRequest(url, Request.GET);
    }

    protected Request createRequest(String url, String method) {
        Request req = new Request(url, method);
        req.addHeader("Authorization", "Basic "+createEncodedText(config.getLogin(),config.getPass()));
        return req;
    }

    private static String createEncodedText(final String username, final String password) {
        final String pair = username + ":" + password;
        final byte[] encodedBytes = Base64.encode(pair.getBytes());
        return new String(encodedBytes);
    }

}