package com.gowhich.androidasynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    private String imagePath = "http://i2.w.yun.hjfile.cn/slide/201309/8335717857.jpg";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) this.findViewById(R.id.button);
        imageView = (ImageView) this.findViewById(R.id.imageView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("下载提示");
        progressDialog.setMessage("图片下载中.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute(imagePath);
            }
        });

    }

    /**
     * 使用异步任务的规则
     * 1,声明一个类继承AsyncTask, 标注三个参数的类型
     * 2,第一个参数表示要执行的任务通常是网络的路径,第二个参数表示进度的刻度,第三个参数表示任务执行的返回结果
     */
    public class MyTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imageView.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);
            Bitmap bitmap = null;

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = null;


            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    inputStream = httpResponse.getEntity().getContent();
                    long fileLength = httpResponse.getEntity().getContentLength();
                    int len = 0;
                    byte[] data = new byte[2024];
                    int totalLength = 0;
                    while ((len = inputStream.read(data)) != -1) {
                        totalLength += len;
                        int value = (int) ((totalLength / (float) fileLength) * 100);
                        publishProgress(value);
                        outputStream.write(data, 0, len);
                    }

                    byte[] result = outputStream.toByteArray();
                    bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return bitmap;
        }
    }
}
