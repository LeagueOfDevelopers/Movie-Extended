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
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Жамбыл on 2/8/2016.
 */
public interface Server {

    String ENDPOINT = "http://movieextended1.azurewebsites.net/api/";

    @POST("session/login")
    @Headers("Content-Type:application/json")
    Observable<String> getToken(@Body String qrCode);

    @POST("languages/get")
    @Headers("Content-Type:application/json")
    Observable<JsonArray> getMovieLanguages(@Body String tokenValue);

    @POST("languages/get")
    @Headers("Content-Type:application/json")
    Object getMovieLanguagesTest(Callback<Language> response, @Body String tokenValue);


    class Creator {
        public static Server newService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Server.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(Server.class);
        }
    }
}
