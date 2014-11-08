package eu.asyncro.passmatters.controllers;

import com.dmacan.lightandroid.api.LightResponse;
import com.dmacan.lightandroid.data.LightController;

import eu.asyncro.passmatters.data.DataSignIn;
import eu.asyncro.passmatters.data.responses.ResponseSignIn;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 11/8/2014.
 */
public class ControllerSignIn extends LightController {

    private Callback<ResponseSignIn> callbackSignIn = new Callback<ResponseSignIn>() {
        @Override
        public void success(ResponseSignIn responseSignIn, Response response) {

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    public void signIn(DataSignIn data) {
        /*
        LightRequest request=new LightRequest();
        request.setData(data);
        LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).signIn(request, callbackSignIn);
*/
        getOnDataReadListener().onDataRead(new LightResponse());
    }
}
