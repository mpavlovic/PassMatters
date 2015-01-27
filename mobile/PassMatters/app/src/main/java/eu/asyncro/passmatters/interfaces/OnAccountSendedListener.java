package eu.asyncro.passmatters.interfaces;

import com.lightandroid.api.LightResponse;

/**
 * Interface used as listener for server response on account request
 */
public interface OnAccountSendedListener {

    public void onAccountSended(LightResponse response);
}
