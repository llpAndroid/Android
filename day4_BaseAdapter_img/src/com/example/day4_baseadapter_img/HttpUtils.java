package com.example.day4_baseadapter_img;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.day4_baseadapter_img.bean.Joke;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpUtils {
	
	//联网下载字符串
	public static String getStringByHttp(String http) throws Exception{
		HttpURLConnection conn=null;
		URL url=new URL(http);
		conn=(HttpURLConnection) url.openConnection();
		InputStream iStream = conn.getInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(iStream));
		String line =null;
		StringBuilder sBuilder=new StringBuilder();
		while((line=br.readLine())!=null){
			sBuilder.append(line).append("\n");
		}
		br.close();
		conn.disconnect();
		return sBuilder.toString();		
	}
	
	
	//联网下载图片
		public static Bitmap getBitmapByHttp(String http) throws Exception{
			HttpURLConnection conn=null;
			URL url=new URL(http);
			conn=(HttpURLConnection) url.openConnection();
			InputStream iStream = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(iStream);
			iStream.close();
			conn.disconnect();
			return bitmap;		
		}
		
		//解析数据
		
		public static List<Joke> getParserDate(String str) throws JSONException{
			ArrayList<Joke> list=new ArrayList<>();
			
			JSONObject jsonObject=new JSONObject(str);
			JSONArray jsonArray = jsonObject.getJSONArray("detail");
			int len=jsonArray.length();
			for (int i = 0; i < len; i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Joke joke=new Joke();
				joke.xhid=jsonObject2.getInt("xhid");
				joke.author=jsonObject2.getString("author");
				joke.content=jsonObject2.getString("content");
				joke.picurl = jsonObject2.getString("picUrl");
				list.add(joke);
			}
			
			return list;
			
		}
	
}
