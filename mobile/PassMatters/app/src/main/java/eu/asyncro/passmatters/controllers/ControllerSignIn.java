package eu.asyncro.passmatters.controllers;

import com.dmacan.lightandroid.data.LightController;

import eu.asyncro.passmatters.data.requests.RequestSignIn;
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
            if (getOnDataReadListener() != null)
                getOnDataReadListener().onDataRead(responseSignIn);

        }

        @Override
        public void failure(RetrofitError error) {
            if (getOnErrorListener() != null)
                getOnErrorListener().onError(error);
        }
    };

    public void signIn(RequestSignIn request) {
/*
        LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).signIn(request, callbackSignIn);
*/
        demoSignIn(request);
    }

    private void demoSignIn(RequestSignIn request){

        if(request.getUsername().equals("demo")) {
            ResponseSignIn response = new ResponseSignIn();
            response.setLogged(1);

            getOnDataReadListener().onDataRead(response);
        }
        }
}
