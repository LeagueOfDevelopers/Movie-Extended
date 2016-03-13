package com.lod.movie_extended.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.lod.movie_extended.data.model.Language;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Жамбыл on 2/8/2016.
 */
public interface ServerAPI {

    String ENDPOINT = "http://movieextended1.azurewebsites.net/api/";

    @POST("session/login")
    @Headers("Content-Type:application/json")
    Observable<String> getToken(@Body String qrCode);

    class Creator {
        public static ServerAPI newService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServerAPI.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ServerAPI.class);
        }
    }
}
