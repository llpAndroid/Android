package com.example.day4_baseadapter_img;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class MyTaskDownLoadBitmap extends AsyncTask<String, Void, Bitmap> {

	ImageView iv;
	//通过传参的方法传入
	public MyTaskDownLoadBitmap(ImageView iv) {
		super();
		this.iv = iv;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		try {
			Bitmap bitmap = HttpUtils.getBitmapByHttp(params[0]);
			return bitmap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result!=null&&!isCancelled()) {
			iv.setImageBitmap(result);
		}
	}
}
