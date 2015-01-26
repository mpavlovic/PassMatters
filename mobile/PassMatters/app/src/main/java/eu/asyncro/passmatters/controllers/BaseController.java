package eu.asyncro.passmatters.controllers;

import android.app.Activity;
import android.app.ProgressDialog;

import com.dmacan.lightandroid.data.LightController;

import eu.asyncro.passmatters.R;

/**
 * Created by ahuskano on 1/24/2015.
 */
public abstract class BaseController extends LightController {
    private Activity activity;
    private ProgressDialog dialog;

    public BaseController(Activity activity){
        this.activity=activity;
    }
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void showDialog(){
        if(dialog==null)
            setUpDialog();
        dialog.show();
    }
    private ProgressDialog setUpDialog(){
        dialog=new ProgressDialog(getActivity(), R.style.DialogTheme);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(android.R.style.Widget_Holo_ProgressBar_Large);
        return dialog;
    }

    public void dismissDialog(){
        if(dialog!=null && dialog.isShowing())
            dialog.dismiss();
    }

}
