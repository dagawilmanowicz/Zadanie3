package com.example.dagmara.zadanie4dw;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button btn_start;
    ProgressBar progressBar;
    TextView txt_percentage;
    TextView tt;
    private static Handler myHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tt.setText(String.valueOf(msg.arg1));
            }
        };

        btn_start = (Button) findViewById(R.id.btn_start);
        progressBar =  (ProgressBar) findViewById(R.id.progress);
        txt_percentage= (TextView) findViewById(R.id.txt_percentage);
        tt = (TextView) findViewById(R.id.tt);

        btn_start.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                btn_start.setEnabled(false);
                new ShowDialogAsyncTask().execute();
            }
        });
    }
    public void onStartThreadClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    Message m = new Message();
                    m.arg1 = i;
                    myHandler.sendMessage(m);
                    SystemClock.sleep(200);

                }
            }
        }).start();
    }

    private class ShowDialogAsyncTask extends AsyncTask<Void, Integer, Void> {

        int progress_status;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(MainActivity.this,
                    "Rozpoczęto pobieranie", Toast.LENGTH_SHORT).show();

            progress_status = 0;
            txt_percentage.setText("Pobieranie 0%");

        }

        @Override
        protected Void doInBackground(Void... params) {

            while(progress_status<100){

                progress_status += 2;

                publishProgress(progress_status);
                SystemClock.sleep(300);

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setProgress(values[0]);
            txt_percentage.setText("Pobieranie " +values[0]+"%");

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Toast.makeText(MainActivity.this,
                    "Pobieranie zakończone", Toast.LENGTH_SHORT).show();

            txt_percentage.setText("Zakończono z powodzeniem");
            btn_start.setEnabled(true);
        }
    }
}