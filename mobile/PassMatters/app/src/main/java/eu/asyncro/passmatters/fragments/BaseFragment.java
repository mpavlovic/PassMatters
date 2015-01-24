package eu.asyncro.passmatters.fragments;

import android.app.ProgressDialog;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dmacan.lightandroid.LightFragment;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.controllers.ControllerLogOut;
import eu.asyncro.passmatters.controllers.ManagerSession;
import eu.asyncro.passmatters.receivers.LockReceiver;

/**
 * Created by ahuskano on 12/5/2014.
 */
public abstract class BaseFragment extends LightFragment {

    public abstract int getMenuResource();

    protected ControllerLogOut controllerLogOut;



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getMenuResource() != 0) inflater.inflate(getMenuResource(), menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                if (controllerLogOut != null)
                    controllerLogOut.logOut(ManagerSession.getToken(getActivity().getBaseContext()));
                break;
        }
        return true;
    }

    public ControllerLogOut getControllerLogOut() {
        return controllerLogOut;
    }

    public void setControllerLogOut(ControllerLogOut controllerLogOut) {
        this.controllerLogOut = controllerLogOut;
    }


}
