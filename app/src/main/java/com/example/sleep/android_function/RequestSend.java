package com.example.sleep.android_function;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** Use HTTP GET/POST to access data
 * !!! need to use thread in main activity !!!
 * !!! need to use thread in main activity !!!
 * !!! need to use thread in main activity !!!*/

class RequestSend{

    RequestSend(){}
    /** Use GET method access data
     * String request_url: input url
     * return String data, if access error it would return "error"  */
    String requestGet(String request_url) {
        try {
            URL url = new URL(request_url);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            // GET request
            connect.setRequestMethod("GET");
            connect.setConnectTimeout(5000);

            // User agent, tell server, I use chrome to connect
            connect.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
            // response code, 200 is OK, 404 is can't find url
            int responseCode = connect.getResponseCode();
            Log.e("responseCode", Integer.toString(responseCode));
            if(responseCode == 200) {
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
                String line;
                StringBuilder sb = new StringBuilder("");
                while ((line = streamReader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
    /** Use POST method access data
     * String request_url: input url
     * String params: input  some key, value
     * return String data, if access error it would return "error"  */
    String requestPost(String request_url, HashMap<String, String> params) {
        try {
            URL url = new URL(request_url);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");
            connect.setConnectTimeout(5000);
            connect.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
            // post data need to do this, allowed it can set output data
            connect.setDoOutput(true);
            // post data
            OutputStream os = connect.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));
            writer.flush();
            writer.close();
            os.close();

            int responseCode = connect.getResponseCode();
            Log.e("responseCode", Integer.toString(responseCode));
            if(responseCode == 200) {
                // response
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
                String line;
                StringBuilder sb = new StringBuilder("");
                while ((line = streamReader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
    /**
     * HashMap<String, String> params to String
     * [("key1", "value1"), ("key2", "value2")] change to
     * key=value1&key2=value2
     * */
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
