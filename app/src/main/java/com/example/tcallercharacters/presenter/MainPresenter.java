package com.example.tcallercharacters.presenter;

import android.os.AsyncTask;

import com.example.tcallercharacters.api.ApiCall;
import com.example.tcallercharacters.view.MainView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainPresenter implements MainPresenterContract {

    private MainView mainView;
    private Retrofit retrofit = null;
    private ApiCall apiCall = null;
    private String baseUrl = null;

    public MainPresenter(MainView view) {
        this.mainView = view;
    }

    @Override
    public void init() {

        baseUrl = "https://blog.truecaller.com";

        Dispatcher dispatcher=new Dispatcher();
        dispatcher.setMaxRequests(3);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).dispatcher(dispatcher).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();

        apiCall = retrofit.create(ApiCall.class);

    }

    @Override
    public void onButtonClick() {
        if(mainView != null) {
            mainView.showProgress();
            fetchData();
        }
    }

    private void fetchData() {
        if(apiCall != null) {
            Call<String> call1 = apiCall.doNetworkRequest();
            call1.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if(response.isSuccessful()) {
                        Task10thCharacterRequest task1  = new Task10thCharacterRequest();
                        TaskEvery10thCharacterRequest task2 = new TaskEvery10thCharacterRequest();
                        TaskWordCounterRequest task3 = new TaskWordCounterRequest();

                        task3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, response.body());
                        task2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, response.body());
                        task1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, response.body());

                        mainView.hideProgress();
                    } else {
                        mainView.hideProgress();
                        mainView.showError();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    mainView.hideProgress();
                    mainView.showError();
                }
            });

        }
    }

    private void updateUI(String s1, String s2, String s3) {
        mainView.showResult(s1,s2,s3);
    }

    public class Task10thCharacterRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... s) {
            return Character.toString(((String) s[0]).charAt(10));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateUI(s,"","");
        }
    }

    private class TaskEvery10thCharacterRequest extends AsyncTask<String, Void, StringBuilder> {

        @Override
        protected StringBuilder doInBackground(String... strings) {
            int val = 10;
            boolean flag = true;
            StringBuilder sb = new StringBuilder();

            while (flag) {
                if (strings[0].length() < val) {
                    flag = false;
                    return sb;

                } else {
                    sb.append(Character.toString(strings[0].charAt(val-1)));
                }
                val = val + 10;
            }
            return null;
        }

        @Override
        protected void onPostExecute(StringBuilder s) {
            super.onPostExecute(s);
            updateUI("",s.toString(),"");
        }
    }

    private class TaskWordCounterRequest extends AsyncTask<String,Void,StringBuilder> {

        @Override
        protected StringBuilder doInBackground(String... strings) {

            String[] str = strings[0].split("\\s+");
            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < str.length; i++) {
                if (map.containsKey(str[i])) {
                    map.put(str[i], map.get(str[i]) + 1);
                } else {
                    map.put(str[i], 1);
                }
            }
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entries : map.entrySet()) {
                String key = entries.getKey();
                String val = Integer.toString(entries.getValue());
                sb = sb.append(key).append(" = ").append(val).append("\n");

            }
            return sb;
        }

        @Override
        protected void onPostExecute(StringBuilder s) {
            super.onPostExecute(s);
            updateUI("","",s.toString());
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
        retrofit = null;
        apiCall = null;
    }
}
