package eu.asyncro.passmatters.data.requests;

import com.dmacan.lightandroid.api.LightRequest;
import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 11/9/2014.
 */
public class RequestAccounts extends LightRequest {

    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
