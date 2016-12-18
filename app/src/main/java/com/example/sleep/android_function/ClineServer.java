package com.example.sleep.android_function;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


// <uses-permission android:name="android.permission.INTERNET" />

public class ClineServer extends AppCompatActivity {

    TextView txt_show_json;
    Button btn_post;
    Button btn_get;
    String html_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cline_server);
        init();
    }

    public void btn_getClicked(View v){
        final RequestSend request = new RequestSend();
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                html_str = request.requestGet("http://sleep.ddns.net/");
                Log.e("Get Response", html_str);

                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        });
        t.start();
    }

    public void btn_postClicked(View v){
        final RequestSend request = new RequestSend();
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                // set post data
                HashMap<String, String> params = new HashMap<>();
                params.put("account", "test");
                params.put("passwd", "test");

                html_str = request.requestPost("http://sleep.ddns.net/testpost", params);
                Log.e("Post Response", html_str);

                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        });
        t.start();
    }

    void init(){
        txt_show_json = (TextView)findViewById(R.id.txt_show_json);
        btn_get = (Button)findViewById(R.id.btn_get);
        btn_post = (Button)findViewById(R.id.btn_post);
    }

    /** update textView*/
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    txt_show_json.setText(html_str);
                    break;
            }
        }
    };
}
