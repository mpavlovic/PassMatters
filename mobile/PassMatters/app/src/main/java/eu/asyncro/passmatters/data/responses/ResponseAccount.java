package eu.asyncro.passmatters.data.responses;

import com.lightandroid.api.LightResponse;
import com.google.gson.annotations.Expose;

/**
 * Data model of account response
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
