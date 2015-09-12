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
	private EditText inId; //����id
	private Button search; //��ȡ��ť
	private Button next;
	private ListView listView;  //��ȡ��
	private String url; //��ȡ��ȡ�Ĳ�����ַ
	private TextView result; //��ȡҳ��
	private int i=1;  //ҳ��
	List<String> data = new ArrayList<String>();//��Ż�����Ϣ
	List<String> data_url = new ArrayList<String>();//������ӵ���ַ���Թ���ת
	
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
    	//�󶨿ؼ�    	
    	listView = (ListView) findViewById(R.id.listView1);    	
    	inId = (EditText) findViewById(R.id.editText1);
    	result = (TextView) findViewById(R.id.textView1);
    	search = (Button) findViewById(R.id.button1);
    	next = (Button) findViewById(R.id.button2);
    	//����һ��Adapter��ʵ��
    	final MyBaseAdapter mAdapter = new MyBaseAdapter();
    	
    	//����search�ĵ���¼�   	
    	search.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.getId()==R.id.button1){
					//ÿ���һ��search ��i����Ϊ1��ʹnext page��ͷ����
					i=1;
		    		Toast.makeText(MainActivity.this, "���ڻ�ȡ...", 0).show();
		    		//��������
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
					//�򿪰ٶ�����APP
					/*Intent intent = getPackageManager().getLaunchIntentForPackage("com.baidu.tieba");
					Uri uri = Uri.parse(data_url.get(arg2));
					intent.setData(uri);
					startActivity(intent);*/
					
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "��ʧ�ܣ�", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
    	//����search�ĵ���¼�
    	next.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.getId()==R.id.button2){
					Toast.makeText(MainActivity.this, "���ڻ�ȡ...", 0).show();
					//��������
		    		httpClientThread = new Thread(next_httpclientRunnable);
		    		httpClientThread.start();	
				}
			}
		}); 
    	
    	mHandler = new Handler(){
    	public void handleMessage(Message msg){
    			switch(msg.what){
    			case MSG_SUCCESS:
    				//���ӳɹ����������ʾ
    				//new AlertDialog.Builder(MainActivity.this).setTitle("").setMessage("��ȡ�ɹ�").setPositiveButton("ȷ��", null).show();
    				//Toast.makeText(getApplicationContext(), "��ȡ�ɹ�...", Toast.LENGTH_SHORT).show();
    				//����ȡ����ҳԴ����ת��ΪString����
    			    String message = (String) msg.obj;
    			    //������ʽƥ�䷢��URL
    			    Pattern pdata_url = Pattern.compile("bluelink\" href=\".*?\"");
    			    Matcher mdata_url = pdata_url.matcher(message);
    			    //����data_url���������ǰ�������
    			    data_url.clear();
    			    while(mdata_url.find()){
    					//��data����� �غõ�����
    			    	data_url.add("http://tieba.baidu.com"+mdata_url.group().toString().substring(16,mdata_url.group().toString().length()-1));    
    				}
    				//������ʽƥ���л�ҳ���id
    				Pattern p_url = Pattern.compile("0&un=.*?&only");
    				Matcher m_id = p_url.matcher(message);
    				if(m_id.find()){
    					url = m_id.group().substring(5,m_id.group().length()-5);
    				}
    				//������ʽƥ�䷢������
    				Pattern p_content = Pattern.compile("<div class=\"p_content\">.*?</div>");
    				Matcher m_content = p_content.matcher(message);
    				//����data���������ʱ�����
    				data.clear();
    				while(m_content.find()){
    					//��data����� �غõ�����
    					data.add(m_content.group().toString().substring(23,m_content.group().toString().length()-6));    
    					mAdapter.notifyDataSetChanged();
    				}
    				break;
    			case MSG_FAILURE:
    				Toast.makeText(getApplicationContext(), "��ȡʧ��...", Toast.LENGTH_SHORT).show();
    				break;
    				
    			default:
    				break;
    			}
    			if(result.getText()!=null){
    	    		result.setText("     ");
    	    	}   	
    	    	result.setText("��"+i+"ҳ");
    		}
    	};
    	//����Adapter
    	listView.setAdapter(mAdapter);
    	
	}
    //����search����¼��Ķ��߳�
    Runnable search_httpclientRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//��ȡ�ӱ༭���������id
			//mHandler.postDelayed(search_httpclientRunnable, 3000);
			String id = inId.getText().toString();
			try {
				//��id��Ϊutf-8��ʽ
				id = URLEncoder.encode(id, "utf-8");
				//��װurl
				httpClientWebData("http://tieba.baidu.com/f/search/ures?ie=utf-8&kw=&qw=&rn=10&un="+id+"&only_thread=&sm=1&sd=&ed=&pn=1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    };
    //����next����¼��Ķ��߳�
    Runnable next_httpclientRunnable = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//mHandler.postDelayed(next_httpclientRunnable, 3000);
			i=i+1;
			try {
				//��װurl
				String complete_url = "http://tieba.baidu.com/f/search/ures?kw=&qw=&rn=10&un="+url+"&only_thread=&sm=1&sd=&ed=&pn="+Integer.valueOf(i).toString();
				httpClientWebData(complete_url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    };
    
    /**
     * Get����
     * @param Url ���������
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
     * ����һ����̳�BaseAdapter
     */
    class MyBaseAdapter extends BaseAdapter{

    	/**
    	 * �õ�Item������
    	 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			//����ListView Item��Ŀ������
			return data.size();
		}

		/**
		 * �õ�Item����Ķ���
		 */
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			// ����ListView Item��Ŀ����Ķ���
			return data.get(position);
		}

		/**
		 * �õ�Item��id
		 */
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			//����ListView Item��id
			return position;
		}

		/**
		 * �õ�Item��View��ͼ
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//��List_item.xml�ļ��ҳ�����ת����View����
			View view = View.inflate(MainActivity.this, R.layout.list_item, null);
			//�ҵ�list_item.xml�д�����TextView
			TextView textView = (TextView) view.findViewById(R.id.tv_list);
			textView.setText(data.get(position));
			return view;
		}
    }
}
