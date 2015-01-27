package eu.asyncro.passmatters.data.requests;

import com.lightandroid.api.LightRequest;
import com.google.gson.annotations.Expose;

/**
 * Data model of accounts request
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
