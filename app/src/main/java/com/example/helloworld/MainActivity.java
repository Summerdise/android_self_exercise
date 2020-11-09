package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button button;

    Wrapper wrapper;
    LocalDataSource localDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication myApplication = new MyApplication();
        localDataSource = myApplication.getLocalDataSourceInstance();
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
                        List<Person> list = localDataSource.personDao().getAll();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getToast(list);
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {

                            String toastInfo = Objects.requireNonNull(response.body()).string();
                            Gson gson = new Gson();
                            wrapper = gson.fromJson(toastInfo, Wrapper.class);
                            updateDataBase();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (wrapper.getData().size() > 0) {
                                        getToast(wrapper.getData());
                                    }
                                }
                            });
                        }
                    }
                }
        );
    }

    public void updateDataBase() {
        List<Person> personList = localDataSource.personDao().getAll();
        if (personList.size() != wrapper.getData().size()) {
            for (Person person : wrapper.getData()) {
                localDataSource.personDao().insert(person);
            }
        }
    }

    public void toastInformation(String info){
        Toast.makeText(MainActivity.this, info, Toast.LENGTH_LONG).show();
    }

    public void getToast(List<Person> list){
        if(list.size()>0){
            toastInformation(list.get(0).getName());
        }
    }
}