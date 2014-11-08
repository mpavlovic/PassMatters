package eu.asyncro.passmatters.data;

import com.dmacan.lightandroid.type.LightData;
import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 11/8/2014.
 */
public class DataSignIn extends LightData {

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
