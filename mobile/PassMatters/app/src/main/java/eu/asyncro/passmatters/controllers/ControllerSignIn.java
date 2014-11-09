package eu.asyncro.passmatters.controllers;

import com.dmacan.lightandroid.api.LightRequest;
import com.dmacan.lightandroid.api.LightResponse;
import com.dmacan.lightandroid.data.LightController;
import com.dmacan.lightandroid.util.LightAPIUtil;

import eu.asyncro.passmatters.api.APIPassMatters;
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
            if (getOnDataReadListener() != null)
                getOnDataReadListener().onDataRead(responseSignIn);

        }

        @Override
        public void failure(RetrofitError error) {
            if (getOnErrorListener() != null)
                getOnErrorListener().onError(error);
        }
    };

    public void signIn(DataSignIn data) {
/*
        LightRequest request=new LightRequest();
        request.setData(data);
        LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).signIn(request, callbackSignIn);
*/
        demoSignIn(data);
    }

    private void demoSignIn(DataSignIn data){

        if(data.getUsername().equals("demo")) {
            ResponseSignIn response = new ResponseSignIn();
            response.setLogged(1);

            getOnDataReadListener().onDataRead(response);
        }
        }
}
