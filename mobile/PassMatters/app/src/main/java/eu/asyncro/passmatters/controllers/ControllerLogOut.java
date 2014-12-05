package eu.asyncro.passmatters.controllers;

import com.dmacan.lightandroid.data.LightController;
import com.dmacan.lightandroid.util.LightAPIUtil;

import eu.asyncro.passmatters.api.APIPassMatters;
import eu.asyncro.passmatters.data.requests.RequestSignIn;
import eu.asyncro.passmatters.data.responses.ResponseLogOut;
import eu.asyncro.passmatters.data.responses.ResponseSignIn;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 12/5/2014.
 */
public class ControllerLogOut extends LightController {
    private Callback<ResponseLogOut> callbackLogout = new Callback<ResponseLogOut>() {
        @Override
        public void success(ResponseLogOut responseLogOut, Response response) {
            if (getOnDataReadListener() != null)
                getOnDataReadListener().onDataRead(responseLogOut);

        }

        @Override
        public void failure(RetrofitError error) {
            if (getOnErrorListener() != null)
                getOnErrorListener().onError(error);
        }
    };

    public void logOut(String token) {
        LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).logOut(token, callbackLogout);

    }

}
