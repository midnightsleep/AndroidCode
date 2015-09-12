package com.example.tieba;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private EditText inId; //输入id
	private Button search; //获取按钮
	private Button next;
	private ListView listView;  //获取表单
	private String url; //存取获取的部分网址
	private TextView result; //获取页码
	private int i=1;  //页码
	List<String> data = new ArrayList<String>();//存放回帖信息
	List<String> data_url = new ArrayList<String>();//存放帖子的网址，以供跳转
	
	private static final int MSG_SUCCESS = 0;
	private static final int MSG_FAILURE = 1;
	private Handler mHandler = null;
	
	private Thread httpClientThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);      
        init(); 
    }

/**
 * 
 */
    private void init() {
		// TODO Auto-generated method stub
    	//绑定控件    	
    	listView = (ListView) findViewById(R.id.listView1);    	
    	inId = (EditText) findViewById(R.id.editText1);
    	result = (TextView) findViewById(R.id.textView1);
    	search = (Button) findViewById(R.id.button1);
    	next = (Button) findViewById(R.id.button2);
    	//创建一个Adapter的实例
    	final MyBaseAdapter mAdapter = new MyBaseAdapter();
    	
    	//创建search的点击事件   	
    	search.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.getId()==R.id.button1){
					//每点击一次search 将i设置为1，使next page从头计数
					i=1;
		    		Toast.makeText(MainActivity.this, "正在获取...", 0).show();
		    		//进行连接
		    		httpClientThread = new Thread(search_httpclientRunnable);
		    		httpClientThread.start();
		    	}
			}
		});
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse(data_url.get(arg2));
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				try {
					
					startActivity(intent);
					Log.w("11111", intent.toString());
					//打开百度贴吧APP
					/*Intent intent = getPackageManager().getLaunchIntentForPackage("com.baidu.tieba");
					Uri uri = Uri.parse(data_url.get(arg2));
					intent.setData(uri);
					startActivity(intent);*/
					
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "打开失败！", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
    	//创建search的点击事件
    	next.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.getId()==R.id.button2){
					Toast.makeText(MainActivity.this, "正在获取...", 0).show();
					//进行连接
		    		httpClientThread = new Thread(next_httpclientRunnable);
		    		httpClientThread.start();	
				}
			}
		}); 
    	
    	mHandler = new Handler(){
    	public void handleMessage(Message msg){
    			switch(msg.what){
    			case MSG_SUCCESS:
    				//连接成功，将结果显示
    				//new AlertDialog.Builder(MainActivity.this).setTitle("").setMessage("获取成功").setPositiveButton("确定", null).show();
    				//Toast.makeText(getApplicationContext(), "获取成功...", Toast.LENGTH_SHORT).show();
    				//将获取的网页源代码转换为String类型
    			    String message = (String) msg.obj;
    			    //正则表达式匹配发帖URL
    			    Pattern pdata_url = Pattern.compile("bluelink\" href=\".*?\"");
    			    Matcher mdata_url = pdata_url.matcher(message);
    			    //在向data_url中添加数据前，先清空
    			    data_url.clear();
    			    while(mdata_url.find()){
    					//向data中添加 截好的数据
    			    	data_url.add("http://tieba.baidu.com"+mdata_url.group().toString().substring(16,mdata_url.group().toString().length()-1));    
    				}
    				//正则表达式匹配切换页面的id
    				Pattern p_url = Pattern.compile("0&un=.*?&only");
    				Matcher m_id = p_url.matcher(message);
    				if(m_id.find()){
    					url = m_id.group().substring(5,m_id.group().length()-5);
    				}
    				//正则表达式匹配发帖内容
    				Pattern p_content = Pattern.compile("<div class=\"p_content\">.*?</div>");
    				Matcher m_content = p_content.matcher(message);
    				//在向data中添加数据时先清空
    				data.clear();
    				while(m_content.find()){
    					//向data中添加 截好的数据
    					data.add(m_content.group().toString().substring(23,m_content.group().toString().length()-6));    
    					mAdapter.notifyDataSetChanged();
    				}
    				break;
    			case MSG_FAILURE:
    				Toast.makeText(getApplicationContext(), "获取失败...", Toast.LENGTH_SHORT).show();
    				break;
    				
    			default:
    				break;
    			}
    			if(result.getText()!=null){
    	    		result.setText("     ");
    	    	}   	
    	    	result.setText("第"+i+"页");
    		}
    	};
    	//设置Adapter
    	listView.setAdapter(mAdapter);
    	
	}
    //创建search点击事件的多线程
    Runnable search_httpclientRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//获取从编辑框中输入的id
			//mHandler.postDelayed(search_httpclientRunnable, 3000);
			String id = inId.getText().toString();
			try {
				//将id改为utf-8格式
				id = URLEncoder.encode(id, "utf-8");
				//组装url
				httpClientWebData("http://tieba.baidu.com/f/search/ures?ie=utf-8&kw=&qw=&rn=10&un="+id+"&only_thread=&sm=1&sd=&ed=&pn=1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    };
    //创建next点击事件的多线程
    Runnable next_httpclientRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//mHandler.postDelayed(next_httpclientRunnable, 3000);
			i=i+1;
			try {
				//组装url
				String complete_url = "http://tieba.baidu.com/f/search/ures?kw=&qw=&rn=10&un="+url+"&only_thread=&sm=1&sd=&ed=&pn="+Integer.valueOf(i).toString();
				httpClientWebData(complete_url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    };
    
    /**
     * Get连接
     * @param Url 输入的连接
     */
    protected void httpClientWebData(String Url){
    	DefaultHttpClient httpClient = new DefaultHttpClient();
    	HttpGet httpGet = new HttpGet(Url);
    	ResponseHandler<String> responseHandler = new BasicResponseHandler();
    	try {
			String content = httpClient.execute(httpGet,responseHandler);
			mHandler.obtainMessage(MSG_SUCCESS, content).sendToTarget();
		} catch (ClientProtocolException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /*
     * 创建一个类继承BaseAdapter
     */
    class MyBaseAdapter extends BaseAdapter{

    	/**
    	 * 得到Item的总数
    	 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			//返回ListView Item条目的总数
			return data.size();
		}

		/**
		 * 得到Item代表的对象
		 */
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			// 返回ListView Item条目代表的对象
			return data.get(position);
		}

		/**
		 * 得到Item的id
		 */
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			//返回ListView Item的id
			return position;
		}

		/**
		 * 得到Item的View视图
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//将List_item.xml文件找出来并转换成View对象
			View view = View.inflate(MainActivity.this, R.layout.list_item, null);
			//找到list_item.xml中创建的TextView
			TextView textView = (TextView) view.findViewById(R.id.tv_list);
			textView.setText(data.get(position));
			return view;
		}
    }
}
