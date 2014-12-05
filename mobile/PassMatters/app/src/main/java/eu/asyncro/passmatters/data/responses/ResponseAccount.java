package eu.asyncro.passmatters.data.responses;

import com.dmacan.lightandroid.api.LightResponse;
import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 11/9/2014.
 */
public class ResponseAccount extends LightResponse {

    @Expose
    private int status;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
