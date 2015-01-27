package eu.asyncro.passmatters.controllers;

import android.app.Activity;

import com.lightandroid.util.LightAPIUtil;

import eu.asyncro.passmatters.Settings;
import eu.asyncro.passmatters.api.APIPassMatters;
import eu.asyncro.passmatters.data.requests.RequestSignIn;
import eu.asyncro.passmatters.data.responses.ResponseSignIn;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Controller used to management sign in process
 */
public class ControllerSignIn extends BaseController {

    /**
     * Callback used to forwarding server response
     */
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

    /**
     * Method used to send sign in request
     * @param request
     */
    public void signIn(RequestSignIn request) {
        showDialog();
        if (Settings.demoMode)
            demoSignIn(request);
        else
            LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).signIn(request, callbackSignIn);
    }

    /**
     * Method used to generate demo response
     * @param request
     */
    private void demoSignIn(RequestSignIn request) {

        if (request.getUsername().equals("demo")) {
            ResponseSignIn response = new ResponseSignIn();
            response.setCode(1);

            getOnDataReadListener().onDataRead(response);
        }
    }
}
