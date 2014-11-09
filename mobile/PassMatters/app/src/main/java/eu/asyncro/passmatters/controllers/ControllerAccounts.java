package eu.asyncro.passmatters.controllers;

import com.dmacan.lightandroid.data.LightController;
import com.dmacan.lightandroid.util.LightAPIUtil;

import eu.asyncro.passmatters.api.APIPassMatters;
import eu.asyncro.passmatters.data.requests.RequestAccount;
import eu.asyncro.passmatters.data.requests.RequestAccounts;
import eu.asyncro.passmatters.data.responses.ResponseAccounts;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 11/9/2014.
 */
public class ControllerAccounts extends LightController {

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

    public void getAccounts(RequestAccounts request){
      //  LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).getAccounts(request, callbackAccounts);
        demoAccounts();
    }


    private void demoAccounts (){


    }

    public void sendAccount(RequestAccount request){
        //  LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION).create(APIPassMatters.class).getAccounts(request, callbackAccounts);

    }

}
