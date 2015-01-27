package eu.asyncro.passmatters.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import eu.asyncro.passmatters.interfaces.OnLockListener;

/**
 * Class used as lock receiver
 */
public class LockReceiver {

    public static final String KEY = LockReceiver.class.getPackage().getName() + ".ACTION";
    private Activity activity;

    private OnLockListener onLockListener = new OnLockListener() {
        @Override
        public void onLock() {
            if (activity != null) activity.finish();
        }
    };

    /**
     * Creating broadcast receiver
     */
    BroadcastReceiver onLockReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(LockReceiver.KEY))
                onLockListener.onLock();
        }
    };


    public LockReceiver(Activity activity, OnLockListener onLockListener) {
        this.activity = activity;
        this.onLockListener = onLockListener;
        LocalBroadcastManager.getInstance(activity).registerReceiver(onLockReceiver, new IntentFilter(LockReceiver.KEY));
    }

    /**
     * Destroying broadcast receiver
     */
    public void destroy() {
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(onLockReceiver);
    }

}
