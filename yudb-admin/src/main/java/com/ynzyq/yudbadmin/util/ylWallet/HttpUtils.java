package com.ynzyq.yudbadmin.util.ylWallet;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static String post(String url, String requestBody) throws IOException {
        return post(url, new ByteArrayInputStream(requestBody.getBytes()), "application/json");
    }

    public static String post(String url, InputStream inputStream, String contentType) throws IOException {
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(10000);
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Accept", "*/*");
        try {
            OutputStream out = conn.getOutputStream();

            byte[] buff = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buff))>0) {
                out.write(buff, 0, len);
            }
            out.flush();
            out.close();

            //取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } else {
                throw new RuntimeException("ResponseCode is an error code:" + conn.getResponseCode());
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }

    }
}
