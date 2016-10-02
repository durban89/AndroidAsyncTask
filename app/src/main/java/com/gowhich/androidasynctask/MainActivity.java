package com.gowhich.androidasynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    private String imagePath = "http://i2.w.yun.hjfile.cn/slide/201309/8335717857.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) this.findViewById(R.id.button);
        imageView = (ImageView) this.findViewById(R.id.imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        HttpURLConnection

                    }
                });
            }
        });

    }

    /**
     * 使用异步任务的规则
     * 1,声明一个类继承AsyncTask, 标注三个参数的类型
     * 2,第一个参数表示要执行的任务通常是网络的路径,第二个参数表示进度的刻度,第三个参数表示任务执行的返回结果
     */
    public class MyTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return null;
        }
    }
}
