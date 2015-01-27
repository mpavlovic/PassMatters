package eu.asyncro.passmatters.controllers;

import android.app.Activity;

import com.lightandroid.util.LightAPIUtil;

import eu.asyncro.passmatters.Settings;
import eu.asyncro.passmatters.api.APIPassMatters;
import eu.asyncro.passmatters.data.responses.ResponseAccount;
import eu.asyncro.passmatters.interfaces.OnAccountSendedListener;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Controller used to clicked acoount management
 */
public class ControllerAccount extends BaseController {

    private OnAccountSendedListener onAccountSendedListener;

    /**
     * Callback used to forwarding server responses
     */
    private Callback<ResponseAccount> callbackAccount = new Callback<ResponseAccount>() {
        @Override
        public void success(ResponseAccount responseAccount, Response response) {
            if (getOnAccountSendedListener() != null)
                getOnAccountSendedListener().onAccountSended(responseAccount);
        }

        @Override
        public void failure(RetrofitError error) {
            if (getOnErrorListener() != null)
                getOnErrorListener().onError(error);
        }
    };

    public ControllerAccount(Activity activity) {
        super(activity);
    }

    /**
     * Method used to send request on server
     * @param token session token
     * @param accountId
     */
    public void setAccount(String token, int accountId) {
        showDialog();
        if (Settings.demoMode)
            demoSetAccount();
        else
            LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION_PORT).create(APIPassMatters.class).sendAccount(token, accountId, callbackAccount);
    }

    /**
     * Method used to generate demo response
     */
    private void demoSetAccount(){
        ResponseAccount responseAccount=new ResponseAccount();
        responseAccount.setStatus(1);
        onAccountSendedListener.onAccountSended(responseAccount);
    }

    public OnAccountSendedListener getOnAccountSendedListener() {
        return onAccountSendedListener;
    }

    public void setOnAccountSendedListener(OnAccountSendedListener onAccountSendedListener) {
        this.onAccountSendedListener = onAccountSendedListener;
    }

    public Callback<ResponseAccount> getCallbackAccount() {
        return callbackAccount;
    }

    public void setCallbackAccount(Callback<ResponseAccount> callbackAccount) {
        this.callbackAccount = callbackAccount;
    }
}
