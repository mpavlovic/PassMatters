package eu.asyncro.passmatters.controllers;

import android.app.Activity;

import com.dmacan.lightandroid.data.LightController;
import com.dmacan.lightandroid.util.LightAPIUtil;

import eu.asyncro.passmatters.Settings;
import eu.asyncro.passmatters.api.APIPassMatters;
import eu.asyncro.passmatters.data.responses.ResponseLogOut;
import eu.asyncro.passmatters.interfaces.OnLogOutListener;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 12/5/2014.
 */
public class ControllerLogOut extends BaseController {

    private OnLogOutListener onLogOutListener;

    private Callback<ResponseLogOut> callbackLogout = new Callback<ResponseLogOut>() {
        @Override
        public void success(ResponseLogOut responseLogOut, Response response) {
            if (getOnLogOutListener() != null)
                getOnLogOutListener().onLogOut(responseLogOut);

        }

        @Override
        public void failure(RetrofitError error) {
            if (getOnErrorListener() != null)
                getOnErrorListener().onError(error);
        }
    };

    public ControllerLogOut(Activity activity) {
        super(activity);
    }

    public void logOut(String token) {
        if (Settings.demoMode)
            demoLogOut();
        else
            LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).logOut(token, callbackLogout);
    }

    private void demoLogOut(){
        ResponseLogOut responseLogOut=new ResponseLogOut();
        responseLogOut.setCode(1);
        onLogOutListener.onLogOut(responseLogOut);
    }

    public OnLogOutListener getOnLogOutListener() {
        return onLogOutListener;
    }

    public void setOnLogOutListener(OnLogOutListener onLogOutListener) {
        this.onLogOutListener = onLogOutListener;
    }
}
