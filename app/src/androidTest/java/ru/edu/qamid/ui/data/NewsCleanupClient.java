package ru.edu.qamid.ui.data;

import android.util.Log;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.edu.qamid.BuildConfig;
import ru.edu.qamid.dto.AuthState;
import ru.edu.qamid.dto.LoginData;
import ru.edu.qamid.dto.News;
import ru.edu.qamid.dto.NewsResponse;

public class NewsCleanupClient {

    interface Api {
        @POST("authentication/login")
        Call<AuthState> login(@Body LoginData loginData);

        @GET("news")
        Call<NewsResponse> getNews(@Header("Authorization") String token);

        @DELETE("news/{id}")
        Call<Void> delete(@Header("Authorization") String token, @Path("id") int id);
    }

    private final Api api = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);

    public void deleteByTitles(Collection<String> titles) {
        if (titles.isEmpty()) return;
        try {
            String token = api.login(new LoginData(TestData.VALID_LOGIN, TestData.VALID_PASSWORD))
                    .execute().body().getAccessToken();
            for (News news : api.getNews(token).execute().body().getElements()) {
                if (titles.contains(news.getTitle())) {
                    api.delete(token, news.getId()).execute();
                    Log.i("Cleanup", "удалена " + news.getTitle());
                }
            }
        } catch (Exception e) {
            Log.e("Cleanup", "не удалось почистить " + titles, e);
        }
    }
}
