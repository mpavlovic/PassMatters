package eu.asyncro.passmatters.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dmacan.lightandroid.LightFragment;
import com.dmacan.lightandroid.api.LightResponse;
import com.dmacan.lightandroid.api.listener.OnDataReadListener;
import com.dmacan.lightandroid.api.listener.OnErrorListener;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.controllers.ControllerSignIn;
import eu.asyncro.passmatters.data.DataSignIn;
import eu.asyncro.passmatters.data.responses.ResponseSignIn;
import retrofit.RetrofitError;

/**
 * Created by ahuskano on 11/8/2014.
 */
public class FragmentSignIn extends LightFragment implements View.OnClickListener, OnDataReadListener, OnErrorListener {

    private ControllerSignIn controller;
    private EditText username;
    private EditText password;

    @Override
    public int provideLayoutRes() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void main() {
        init();
    }

    private void init() {
        ((Button) getView().findViewById(R.id.btSignIn)).setOnClickListener(this);
        controller = new ControllerSignIn();
        controller.setOnDataReadListener(this);
        controller.setOnErrorListener(this);
        password = (EditText) getView().findViewById(R.id.etPassword);
        username = (EditText) getView().findViewById(R.id.etUsername);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSignIn:
                controller.signIn(getData());
                break;
        }
    }

    private DataSignIn getData() {
        DataSignIn data = new DataSignIn();
        data.setPassword(password.getText().toString());
        data.setUsername(username.getText().toString());
        return data;
    }

    @Override
    public void onDataRead(LightResponse response) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentAccounts()).commit();

    }

    @Override
    public void onError(RetrofitError error) {

    }
}
