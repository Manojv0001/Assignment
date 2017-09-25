package com.example.pankaj.assignment.network;



import com.example.pankaj.assignment.application.ApplicationData;
import com.example.pankaj.assignment.model.Example;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by Ashwini on 5/25/2016.
 */
public class ApiClientMain {
    private static DemoApiInterface DemoApiInterface;  // interface for Retrofit api
    public static final String prefixUrl = "deals.json";
    private static StringBuilder builder = new StringBuilder();
//    public static final String URL = builder.append(ApplicationData.URL).append(prefixUrl).toString(); //"http://faarbetterfilms.com/rockabyte/admin/index.php/api/";
    public static final String MEDIA_TYPE_STRING = "text/plain";
    public static final String MEDIA_TYPE_IMAGE = "image/*";

    public static DemoApiInterface getApiClient() {

        if (DemoApiInterface == null) {
            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(20, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(15, TimeUnit.SECONDS);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApplicationData.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DemoApiInterface = retrofit.create(DemoApiInterface.class);
        }
        return DemoApiInterface;
    }

    public interface DemoApiInterface {
        @Headers({
                "accept: application/json",
                "accept: text/javascript",
                "X-Desidime-Client: 0c50c23d1ac0ec18eedee20ea0cdce91ea68a20e9503b2ad77f44dab982034b0"
        })
        @GET("deals.json?type=top&deal_view=true")
        Call<Example>getResponseofTopDeals(@Query("page") int page);

        @Headers({
                "accept: application/json",
                "accept: text/javascript",
                "X-Desidime-Client: 0c50c23d1ac0ec18eedee20ea0cdce91ea68a20e9503b2ad77f44dab982034b0"
        })
        @GET("deals.json?type=popular&deal_view=true")
        Call<Example> getResponseofPopularDeals(@Query("page") int page);

//        @Query("page") int page
    }

    public static RequestBody getStringRequestBody(String s) {
        return RequestBody.create(MediaType.parse(MEDIA_TYPE_STRING), s);
    }
}
