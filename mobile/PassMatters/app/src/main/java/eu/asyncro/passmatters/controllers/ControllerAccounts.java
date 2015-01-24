package eu.asyncro.passmatters.controllers;

import android.app.Activity;

import com.dmacan.lightandroid.data.LightController;
import com.dmacan.lightandroid.util.LightAPIUtil;

import eu.asyncro.passmatters.Settings;
import eu.asyncro.passmatters.api.APIPassMatters;
import eu.asyncro.passmatters.data.DataAccount;
import eu.asyncro.passmatters.data.requests.RequestAccount;
import eu.asyncro.passmatters.data.responses.ResponseAccounts;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 11/9/2014.
 */
public class ControllerAccounts extends BaseController {

    private Callback<ResponseAccounts> callbackAccounts = new Callback<ResponseAccounts>() {
        @Override
        public void success(ResponseAccounts responseAccounts, Response response) {
            if (getOnDataReadListener() != null)
                getOnDataReadListener().onDataRead(responseAccounts);
        }

        @Override
        public void failure(RetrofitError error) {
            if (getOnErrorListener() != null)
                getOnErrorListener().onError(error);
        }
    };

    public ControllerAccounts(Activity activity) {
        super(activity);
    }

    public void getAccounts(String token) {
        if (Settings.demoMode)
            demoAccounts();
        else
            LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).getAccounts(token, callbackAccounts);
    }


    private void demoAccounts() {
        DataAccount[] account = new DataAccount[7];
        account[0] = new DataAccount(1, "Facebook");
        account[1] = new DataAccount(2, "Linkedin");
        account[2] = new DataAccount(3, "Twitter");
        account[3] = new DataAccount(3, "YouTube");
        account[4] = new DataAccount(3, "Gmail");
        account[5] = new DataAccount(3, "FoiEmail");
        account[6] = new DataAccount(3, "Instagram");

        ResponseAccounts response = new ResponseAccounts();
        response.setAccounts(account);
        response.setCode(1);
        getOnDataReadListener().onDataRead(response);

    }

    public void sendAccount(RequestAccount request) {
        //  LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).getAccounts(request, callbackAccounts);

    }

}
