package com.uqam.inf4375.tp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.http.HttpEntity;

public class JsonReader {
    
    public static JSONObject getLocationFromAddress(String address) throws ParseException, UnsupportedEncodingException, IOException{
        InputStream inputStream = null;
        String json = "";
        String googleAPI = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",URLEncoder.encode(address, "UTF-8"), "AIzaSyDs0nPWlhPGr4nzSrq-IACDGxSKMgLvsDw");
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(googleAPI);
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        inputStream = entity.getContent();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"),8);
        StringBuilder sbuild = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sbuild.append(line);
        }
        inputStream.close();
        json = sbuild.toString();
        
        
        //now parse
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(json);
        JSONObject jb = (JSONObject) obj;
        
        //now read
        JSONArray jsonObject1 = (JSONArray) jb.get("results");
        JSONObject jsonObject2 = (JSONObject)jsonObject1.get(0);
        JSONObject jsonObject3 = (JSONObject)jsonObject2.get("geometry");
        JSONObject location = (JSONObject) jsonObject3.get("location");
        return location;
    }
}
