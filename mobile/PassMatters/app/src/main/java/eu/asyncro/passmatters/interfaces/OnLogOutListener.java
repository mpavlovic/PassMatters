package eu.asyncro.passmatters.interfaces;

import com.lightandroid.api.LightResponse;

/**
 * Interface used as listener for server response on log out request
 */
public interface OnLogOutListener {

    public void onLogOut(LightResponse response);
}
