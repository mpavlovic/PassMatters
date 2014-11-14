package eu.asyncro.passmatters.data.responses;

import com.dmacan.lightandroid.api.LightResponse;
import com.google.gson.annotations.Expose;

import eu.asyncro.passmatters.data.DataAccount;

/**
 * Created by ahuskano on 11/9/2014.
 */
public class ResponseAccounts extends LightResponse {

    @Expose
    private DataAccount[] accounts;

    public DataAccount[] getAccounts() {
        return accounts;
    }

    public void setAccounts(DataAccount[] accounts) {
        this.accounts = accounts;
    }
}
