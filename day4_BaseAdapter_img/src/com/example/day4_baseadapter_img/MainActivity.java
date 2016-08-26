package com.example.day4_baseadapter_img;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.day4_baseadapter_img.MyTaskDownLoadString.CallBack;
import com.example.day4_baseadapter_img.bean.Joke;

public class MainActivity extends Activity {
	ListView lv;
	ArrayList<Joke>data;
	BaseAdapter adapter;
	
	//当前页码数
	int currentPage=1;
	//是否需要加载
	boolean addMore=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv=(ListView) findViewById(R.id.lv);
		data=new ArrayList<>();
		adapter=new MyAdapter(MainActivity.this, data);
		lv.setAdapter(adapter);
		//启动异步下载
		MyTaskDownLoadString task=new MyTaskDownLoadString(new CallBack() {
			
			@Override
			public void getDate(List<Joke> list) {
				data.addAll(list);
				adapter.notifyDataSetChanged();
			}
		});
		task.execute("http://api.1-blog.com/biz/bizserver/xiaohua/list.do?size=20&page=1");
		//设置分页滚动
		OnSetListnenerView();
		
	}

	private void OnSetListnenerView() {
		// TODO Auto-generated method stub
		lv.setOnScrollListener(new OnScrollListener() {
			//滚动改变时
			//0.代表滚动停止
			//1代表正在滚动
			//2惯性滚动
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
				
				//滚动到底并且滚动停止时，再加载更多
				if(addMore&&scrollState==0){
					//启动异步下载
					MyTaskDownLoadString task=new MyTaskDownLoadString(new CallBack() {
						
						@Override
						public void getDate(List<Joke> list) {
							data.addAll(list);
							adapter.notifyDataSetChanged();
						}
					});
					task.execute("http://api.1-blog.com/biz/bizserver/xiaohua/list.do?size=20&page="+ ++currentPage);
					
				}
				
			}
			
			//滚动时调用,先执行
			//参数一：ListView
			//参数二：第一条可见item
			//参数三：可见item的条数
			//参数四：总共的条数
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				/*Log.d("tag", "onScroll firstVisibleItem"+firstVisibleItem);
				Log.d("tag", "onScroll visibleItemCount"+visibleItemCount);
				Log.d("tag", "onScroll totalItemCount"+totalItemCount);*/
				
				//判断是否滚动到底
				if (firstVisibleItem+visibleItemCount==totalItemCount) {
					addMore=true;
				}else {
					addMore=false;
				}
				
			}
		});
	}
	
}
