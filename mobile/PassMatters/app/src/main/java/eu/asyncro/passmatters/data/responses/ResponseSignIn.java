package eu.asyncro.passmatters.data.responses;

import com.lightandroid.api.LightResponse;
import com.google.gson.annotations.Expose;

/**
 * Data model of sign in response
 */
public class ResponseSignIn extends LightResponse {

    @Expose
    private int logged;

    @Expose
    private String token;

    public int getLogged() {
        return logged;
    }

    public void setLogged(int logged) {
        this.logged = logged;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
