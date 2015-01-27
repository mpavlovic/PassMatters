package eu.asyncro.passmatters.data.responses;

import com.lightandroid.api.LightResponse;
import com.google.gson.annotations.Expose;

import eu.asyncro.passmatters.data.DataAccount;

/**
 * Data model of Accounts responses
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
