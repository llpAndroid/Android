package com.example.day4_baseadapter_img;

import java.util.List;

import com.example.day4_baseadapter_img.bean.Joke;

import android.os.AsyncTask;
import android.util.Log;

public class MyTaskDownLoadString extends AsyncTask<String, Void, List<Joke>> {
	
	public interface CallBack{
		public void getDate(List<Joke>list);
	}
	CallBack cb;
	public MyTaskDownLoadString(CallBack cb) {
		super();
		this.cb = cb;
	}
	
	@Override
	protected List<Joke> doInBackground(String... params) {
		try {
			String jsonString = HttpUtils.getStringByHttp(params[0]);
			List<Joke> parserDate = HttpUtils.getParserDate(jsonString);
			return parserDate;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	@Override
	protected void onPostExecute(List<Joke> result) {
		Log.d("tag", result+"");
		super.onPostExecute(result);
		if (cb!=null&&result!=null) {
			cb.getDate(result);
		}
		
	}
}
