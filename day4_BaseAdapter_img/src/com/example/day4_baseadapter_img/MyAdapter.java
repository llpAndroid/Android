package com.example.day4_baseadapter_img;

import java.util.List;

import com.example.day4_baseadapter_img.bean.Joke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	Context mContext;
	List<Joke> data;
	LayoutInflater inflater = null;

	public MyAdapter(Context mContext, List<Joke> data) {
		super();
		this.mContext = mContext;
		this.data = data;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_item, parent,
					false);
			holder = new ViewHolder();
			holder.tv_id = (TextView) convertView.findViewById(R.id.xhid);
			holder.tv_author = (TextView) convertView.findViewById(R.id.author);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.content);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (holder.task != null) {
			holder.task.cancel(true);
		}

		// 清除上次的数据
		holder.tv_id.setText("");
		holder.tv_author.setText("");
		holder.tv_content.setText("");
		//holder.iv.setImageResource(R.drawable.ic_launcher);
		 holder.iv.setVisibility(View.GONE);

		// 设置值
		Joke joke = data.get(position);
		holder.tv_id.setText(joke.xhid + "");
		holder.tv_author.setText(joke.author);
		holder.tv_content.setText(joke.content);

		if (joke.picurl.length()>7&&joke.picurl.toLowerCase().startsWith("http")) {
			MyTaskDownLoadBitmap task = new MyTaskDownLoadBitmap(holder.iv);
			holder.iv.setVisibility(View.VISIBLE);
			holder.task = task;
			
			task.execute(joke.picurl);
		}
		

		return convertView;
	}

	class ViewHolder {
		MyTaskDownLoadBitmap task;
		ImageView iv;
		TextView tv_id;
		TextView tv_author;
		TextView tv_content;
	}
}
