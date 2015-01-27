package eu.asyncro.passmatters.api;

import com.lightandroid.api.LightRequest;

import eu.asyncro.passmatters.data.responses.ResponseAccount;
import eu.asyncro.passmatters.data.responses.ResponseAccounts;
import eu.asyncro.passmatters.data.responses.ResponseLogOut;
import eu.asyncro.passmatters.data.responses.ResponseSignIn;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Interface used to server request/response communication
 */
public interface APIPassMatters {

    public static String API_LOCATION = "https://www.passmatters.eu";
    public static String API_LOCATION_PORT = "http://www.passmatters.eu:3000";

    @POST("/api/login")
    void signIn(@Body LightRequest request, Callback<ResponseSignIn> response);

    @GET("/api/listAccounts")
    void getAccounts(@Query("token") String token, Callback<ResponseAccounts> response);

    @POST("/api/logout")
    void logOut(@Query("token") String token, Callback<ResponseLogOut> response);

    @GET("/api/requestPassword")
    void sendAccount(@Query("token") String token, @Query("account_id") int accountId, Callback<ResponseAccount> response);

}
