package eu.asyncro.passmatters.controllers;

import android.app.Activity;

import com.dmacan.lightandroid.data.LightController;
import com.dmacan.lightandroid.util.LightAPIUtil;

import eu.asyncro.passmatters.Settings;
import eu.asyncro.passmatters.api.APIPassMatters;
import eu.asyncro.passmatters.data.requests.RequestSignIn;
import eu.asyncro.passmatters.data.responses.ResponseSignIn;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 11/8/2014.
 */
public class ControllerSignIn extends BaseController {

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

    public ControllerSignIn(Activity activity) {
        super(activity);
    }

    public void signIn(RequestSignIn request) {
        showDialog();
        if (Settings.demoMode)
            demoSignIn(request);
        else
            LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).signIn(request, callbackSignIn);
    }

    private void demoSignIn(RequestSignIn request) {

        if (request.getUsername().equals("demo")) {
            ResponseSignIn response = new ResponseSignIn();
            response.setCode(1);

            getOnDataReadListener().onDataRead(response);
        }
    }
}
