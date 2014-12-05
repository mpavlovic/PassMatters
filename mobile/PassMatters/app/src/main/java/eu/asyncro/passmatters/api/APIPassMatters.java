package eu.asyncro.passmatters.api;

import com.dmacan.lightandroid.api.LightRequest;

import eu.asyncro.passmatters.data.requests.RequestAccount;
import eu.asyncro.passmatters.data.responses.ResponseAccounts;
import eu.asyncro.passmatters.data.responses.ResponseLogOut;
import eu.asyncro.passmatters.data.responses.ResponseSignIn;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by ahuskano on 11/8/2014.
 */
public interface APIPassMatters {

    public static String API_LOCATION = "http://178.62.212.164";

    @POST("/api/login")
    void signIn(@Body LightRequest request, Callback<ResponseSignIn> response);

    @GET("/api/listAccounts")
    void getAccounts(@Query("token") String token, Callback<ResponseAccounts> response);

    @GET("/api/logout")
    void logOut(@Query("token") String token, Callback<ResponseLogOut> response);

    @POST("/signIn")
    void sendAccount(@Body RequestAccount request, Callback<ResponseAccounts> response);

}
