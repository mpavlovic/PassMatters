package eu.asyncro.passmatters.activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dmacan.lightandroid.LightActivity;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.fragments.FragmentSignIn;
import eu.asyncro.passmatters.receivers.LockReceiver;

/**
 * Created by ahuskano on 11/8/2014.
 */
public class ActivityMain extends LightActivity {

    private LockReceiver lockReceiver;

    private long oldTime = SystemClock.elapsedRealtime();
    private long newTime = SystemClock.elapsedRealtime();
    private long DELAY = 500000;

    @Override
    public int provideLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void main() {
        getSupportFragmentManager().beginTransaction().add(R.id.container,new FragmentSignIn()).commit();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        newTime = SystemClock.elapsedRealtime();
        if (newTime - oldTime >= DELAY) {
            sendAction();
            oldTime = newTime;
        }else
            oldTime = newTime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lockReceiver.destroy();
    }

    public LockReceiver getLockReceiver() {
        return lockReceiver;
    }

    public void setLockReceiver(LockReceiver lockReceiver) {
        this.lockReceiver = lockReceiver;

    }
    private void sendAction() {
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(
                new Intent(LockReceiver.KEY));
    }
}
