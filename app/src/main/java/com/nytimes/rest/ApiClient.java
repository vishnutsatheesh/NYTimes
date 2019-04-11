package com.nytimes.rest;

import android.util.Log;

import com.nytimes.BuildConfig;
import com.nytimes.interfaces.InternetListener;
import com.nytimes.util.LogsUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    /**
     *
     */

    private static Retrofit retrofit = null;


    public static InternetListener listener;



    public static Retrofit getClient(InternetListener _listener) {
        listener = _listener;
        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient())
                    .build();
        }
        return retrofit;
    }


    public static OkHttpClient provideOkHttpClient() {

         final String TAG= ApiClient.class.getSimpleName();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);

        okhttpClientBuilder.addInterceptor(new NetworkConnectionInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                LogsUtils.INSTANCE.makeLogE(">>>", "isInternetAvailable");
                // return this.isInternetAvailable();
                return false;
            }

            @Override
            public void onInternetUnavailable() {
                LogsUtils.INSTANCE.makeLogE(TAG, "onInternetUnavailable");
                listener.isConnectionAvailable(false);

            }

            @Override
            public void onCacheUnavailable() {
                LogsUtils.INSTANCE.makeLogE(TAG, "onCacheUnavailable");

            }

            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                LogsUtils.INSTANCE.makeLogE(TAG, "intercept>>");



                Request request = chain.request();
                Response response = chain.proceed(request);

                return response;

            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                LogsUtils.INSTANCE.makeLogE(TAG, "response clone>>");
                return super.clone();
            }
        });
        // add logging as last interceptor
        okhttpClientBuilder.addInterceptor(logging);  // <-- this is the important line!

        return okhttpClientBuilder.build();
    }


}
