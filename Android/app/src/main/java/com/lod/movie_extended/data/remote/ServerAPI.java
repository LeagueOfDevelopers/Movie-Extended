package com.lod.movie_extended.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Date;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Жамбыл on 2/8/2016.
 */
public interface ServerAPI {

    String ENDPOINT = "http://movieextended1.azurewebsites.net/";

    @POST("api/session/login")
    @Headers("Content-Type:application/json")
    Observable<JsonObject> getData(@Body String qrCode);

    @GET("time/start/{movieId}")
    Observable<Date> getFilmStartTime(@Path("movieId") int movieId);

    class Creator {
        public static ServerAPI newService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS")
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
