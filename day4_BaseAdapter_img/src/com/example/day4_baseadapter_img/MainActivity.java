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
	
	//��ǰҳ����
	int currentPage=1;
	//�Ƿ���Ҫ����
	boolean addMore=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv=(ListView) findViewById(R.id.lv);
		data=new ArrayList<>();
		adapter=new MyAdapter(MainActivity.this, data);
		lv.setAdapter(adapter);
		//�����첽����
		MyTaskDownLoadString task=new MyTaskDownLoadString(new CallBack() {
			
			@Override
			public void getDate(List<Joke> list) {
				data.addAll(list);
				adapter.notifyDataSetChanged();
			}
		});
		task.execute("http://api.1-blog.com/biz/bizserver/xiaohua/list.do?size=20&page=1");
		//���÷�ҳ����
		OnSetListnenerView();
		
	}

	private void OnSetListnenerView() {
		// TODO Auto-generated method stub
		lv.setOnScrollListener(new OnScrollListener() {
			//�����ı�ʱ
			//0.�������ֹͣ
			//1�������ڹ���
			//2���Թ���
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
				
				//�������ײ��ҹ���ֹͣʱ���ټ��ظ���
				if(addMore&&scrollState==0){
					//�����첽����
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
			
			//����ʱ����,��ִ��
			//����һ��ListView
			//����������һ���ɼ�item
			//���������ɼ�item������
			//�����ģ��ܹ�������
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				/*Log.d("tag", "onScroll firstVisibleItem"+firstVisibleItem);
				Log.d("tag", "onScroll visibleItemCount"+visibleItemCount);
				Log.d("tag", "onScroll totalItemCount"+totalItemCount);*/
				
				//�ж��Ƿ��������
				if (firstVisibleItem+visibleItemCount==totalItemCount) {
					addMore=true;
				}else {
					addMore=false;
				}
				
			}
		});
	}
	
}
