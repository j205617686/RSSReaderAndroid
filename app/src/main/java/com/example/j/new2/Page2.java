package com.example.j.new2;


import android.os.Bundle;
import java.io.IOException;
import org.json.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import android.util.Log;
import java.lang.String;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import java.net.URL;
import java.util.List;

import android.view.KeyEvent;
import android.app.AlertDialog;
import android.content.DialogInterface;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



class JieHttpClient {

    public static String GET(String url){
        String result = "";
        HttpGet get = new HttpGet(url);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(get);
            if(httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                result = EntityUtils.toString(httpResponse.getEntity());
            }else{
                result = "JieHttpClient GET Fail";
            }
        }catch(ClientProtocolException e){
            System.out.println("JieHttpClient GET Error = "+e.getMessage().toString());
        }catch (IOException e){
            System.out.println("JieHttpClient GET Error = "+e.getMessage().toString());
        }catch (Exception e){
            System.out.println("JieHttpClient GET Error = "+e.getMessage().toString());
        }
        return result;
    }

    public static String POST(String url,HashMap<String,String> paramsMap){
        String result = "";
        HttpPost post = new HttpPost(url);

        try {

            if(paramsMap!=null){
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for(Map.Entry<String,String> entry:paramsMap.entrySet()){
                    params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                }
                HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
                post.setEntity(httpEntity);
            }

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(post);
            if(httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                result = EntityUtils.toString(httpResponse.getEntity());
            }else{
                result = "JieHttpClient POST Fail";
            }

        }catch(ClientProtocolException e){
            System.out.println("JieHttpClient POST Error = " + e.getMessage().toString());
        }catch (IOException e){
            System.out.println("JieHttpClient POST Error = " + e.getMessage().toString());
        }catch (Exception e){
            System.out.println("JieHttpClient POST Error = " + e.getMessage().toString());
        }
        return result;
    }
}








public class Page2 extends Activity {


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果-->" + val);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //
            // TODO: http request.
            //
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "请求结果");
            msg.setData(data);
            handler.sendMessage(msg);
        }


    };

    ArrayList<HashMap<String, String>> Hashlist = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;
    protected ListView itcItems;
    /*
    private void ShowMsgDialog(String Msg)
    {
        Builder MyAlertDialog = new AlertDialog.Builder(this);
        MyAlertDialog.setTitle("標題");
        MyAlertDialog.setMessage(Msg);
        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                //如果不做任何事情 就會直接關閉 對話方塊
            }
        };;
        MyAlertDialog.setNeutralButton("中間按鈕",OkClick );
        MyAlertDialog.show();
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("EditText");


        boolean continueloop = false;

        new Thread(runnable).start();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


        try {

            String IP="";
            String url_str="http://192.168.1.117:8080/test/reader.do?rss_url="+url;

            String myjson=JieHttpClient.GET(url_str);



            JSONObject jsonobj=new JSONObject(myjson);//将字符串转化成json对象
            JSONArray array = jsonobj.getJSONArray("list");

            List<Article> rssReader=new ArrayList<Article>();

            for (int i = 0; i < array.length(); i++) {
                //System.out.println("list:" + array.get(i));
                rssReader.add(new Article(array.getJSONObject(i).get("Title").toString(),array.getJSONObject(i).get("Link").toString(),array.getJSONObject(i).get("pubDate").toString(),array.getJSONObject(i).get("Description").toString(),array.getJSONObject(i).get("Category").toString()));
            }

            //RSSReader rssReader = new RSSReader(url);


           for (int i = 0; i < rssReader.size(); i++) {
                HashMap<String, String> item = new HashMap<String, String>();

                   item.put("title", rssReader.get(i).getTitle());

                item.put("description", rssReader.get(i).getDescription());
                Hashlist.add(item);
            }


            itcItems = (ListView) findViewById(R.id.listView1);

            //新增SimpleAdapter
            adapter = new SimpleAdapter(
                    this,
                    Hashlist,
                    android.R.layout.simple_list_item_2,
                    new String[]{"title", "description"},
                    new int[]{android.R.id.text1, android.R.id.text2});


            itcItems.setAdapter(adapter);
            itcItems.setTextFilterEnabled(true);

            itcItems.setOnItemClickListener(new ListListener(rssReader, this));



        }
        catch (Exception e) {

            AlertDialog dialog = new AlertDialog.Builder(Page2.this).create();
            dialog.setTitle("錯誤訊息");
            dialog.setMessage("發生錯誤 可能是網址無效或是發生例外 請返回重試");
            dialog.setButton("返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent myIntent = new Intent();
                    myIntent = new Intent(Page2.this, MainActivity.class);
                    startActivity(myIntent);
                    finish();


                }
            });
            dialog.show();


        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(Page2.this, MainActivity.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }




}
