package eu.asyncro.passmatters.fragments;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.lightandroid.LightFragment;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.controllers.ControllerLogOut;
import eu.asyncro.passmatters.controllers.ManagerSession;

/**
 * Abstract class of every fragment used in PassMatters
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
            case R.id.refresh:
                refresh();
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

    /**
     *
     *  this method is just here so we can override it in fragment where we need it
     */
    public void refresh(){

    }

    public void toastIt(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
