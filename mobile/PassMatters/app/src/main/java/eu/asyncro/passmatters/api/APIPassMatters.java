package eu.asyncro.passmatters.api;

import com.dmacan.lightandroid.api.LightRequest;

import eu.asyncro.passmatters.data.responses.ResponseSignIn;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by ahuskano on 11/8/2014.
 */
public interface APIPassMatters {

    public static String API_LOCATION = "http://95.85.4.199/mac/index.php";

    @POST("/signIn")
    void signIn(@Body LightRequest request, Callback<ResponseSignIn> response);

}
