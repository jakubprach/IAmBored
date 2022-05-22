package com.example.iambored;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void IAmBored(View view){
        result = findViewById(R.id.result);

        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        String url = "https://www.boredapi.com/api/activity";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse = response.body().string();
                    JSONObject json = null;
                    try {
                        json = new JSONObject(myResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //String json = gson.toJson(myResponse);

                    JSONObject finalJson = json;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 0; i< finalJson.length(); i++){
                                try {
                                    result.setText(finalJson.getString("activity"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
                }
            }
        });
    }
}