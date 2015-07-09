package com.example.j.new2;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.List;
import java.util.ArrayList;
import android.view.KeyEvent;
import android.view.KeyEvent;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends ActionBarActivity {

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




    private Button next;
    private Button url1;
    private Button url2;
    private Button url3;
    private Button url4;
    private Button url5;
    private Button url6;
    private EditText rssurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = (Button) findViewById(R.id.button1);
        rssurl=(EditText)findViewById(R.id.editText1);


        url1 = (Button) findViewById(R.id.button2);
        url2 = (Button) findViewById(R.id.button3);


        url3 = (Button) findViewById(R.id.button5);
        url4 = (Button) findViewById(R.id.button4);

        url5 = (Button) findViewById(R.id.button);
        url6 = (Button) findViewById(R.id.button6);




        new Thread(runnable).start();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());




        url1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rssurl.setText("http://www.mobile01.com/rss/hottopics.xml");

        }
        });

        url2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rssurl.setText("http://mlb.mlb.com/partnerxml/gen/news/rss/ana.xml");

            }
        });

        url3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rssurl.setText("http://www.appledaily.com.tw/rss/newcreate/kind/sec/type/1077");
            }
        });
        url4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rssurl.setText("http://udn.com/rssfeed/news/1/1?ch=news");
            }
        });

        url5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rssurl.setText("http://news.ltn.com.tw/rss/focus.xml");
            }
        });

        url6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rssurl.setText("");
            }
        });





        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();   //intent實體化
                intent.setClass(MainActivity.this, Page2.class); // 切到頁面2
                startActivity(intent);    //startActivity觸發換頁

                Bundle bundle = new Bundle();
                bundle.putString(
                        "EditText",
                        rssurl.getText().toString());
                intent.putExtras(bundle);

                startActivity(intent);

                finish();   //換頁後結束此頁
            }
        });



    }




    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            ConfirmExit();//按返回鍵，則執行退出確認
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void ConfirmExit(){//退出確認
        AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("離開");
        ad.setMessage("確定要離開?");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                MainActivity.this.finish();//關閉activity

            }
        });
        ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                //不退出不用執行任何操作
            }
        });
        ad.show();//示對話框
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
