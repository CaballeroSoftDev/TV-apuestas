package com.example.tvapuestas.api;

import com.example.tvapuestas.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://api.the-odds-api.com/v4/";
    private static final String API_KEY = BuildConfig.ODDS_API_KEY;
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Crear un interceptor para añadir la apiKey a cada solicitud
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            // Añadir un logging interceptor para ver las solicitudes en el logcat
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);

            // Añadir interceptor para añadir la apiKey como query parameter
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                // Añadir apiKey como query parameter
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apiKey", API_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

}