package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.get_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromURL("https://twc-android-bootcamp.github.io/fake-data/data/default.json");
            }
        });
    }

    OkHttpClient okHttpClient = new OkHttpClient();

    public void getDataFromURL(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "未找到", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String toastInfo = Objects.requireNonNull(response.body()).string();
                        Gson gson = new Gson();
                        Wrapper wrapper = gson.fromJson(toastInfo, Wrapper.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (wrapper.getData().size() > 0) {
                                    Toast.makeText(MainActivity.this, wrapper.getData().get(0).getName(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }

        );
    }
}