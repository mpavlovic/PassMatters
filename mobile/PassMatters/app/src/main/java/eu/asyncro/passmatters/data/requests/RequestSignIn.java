package eu.asyncro.passmatters.data.requests;

import com.lightandroid.api.LightRequest;
import com.google.gson.annotations.Expose;

/**
 * Data model of sign in request
 */
public class RequestSignIn extends LightRequest {

    @Expose
    private String username;

    @Expose
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
