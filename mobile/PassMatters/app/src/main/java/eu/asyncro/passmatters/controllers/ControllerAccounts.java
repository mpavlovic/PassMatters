package eu.asyncro.passmatters.controllers;

import android.app.Activity;

import com.lightandroid.util.LightAPIUtil;

import eu.asyncro.passmatters.Settings;
import eu.asyncro.passmatters.api.APIPassMatters;
import eu.asyncro.passmatters.data.DataAccount;
import eu.asyncro.passmatters.data.requests.RequestAccount;
import eu.asyncro.passmatters.data.responses.ResponseAccounts;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Controller used to management all accounts
 */
public class ControllerAccounts extends BaseController {

    /**
     * Callback used to forwarding server response
     */
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

    /**
     * Method used to send server request
     * @param token session token
     */
    public void getAccounts(String token) {
        showDialog();
        if (Settings.demoMode)
            demoAccounts();
        else
            LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).getAccounts(token, callbackAccounts);
    }


    /**
     * Method used to generate demo response
     */
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
