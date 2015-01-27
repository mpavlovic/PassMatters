package eu.asyncro.passmatters.fragments;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.lightandroid.api.LightResponse;
import com.lightandroid.api.listener.OnDataReadListener;
import com.lightandroid.api.listener.OnErrorListener;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.controllers.ControllerSignIn;
import eu.asyncro.passmatters.controllers.ManagerSession;
import eu.asyncro.passmatters.data.requests.RequestSignIn;
import eu.asyncro.passmatters.data.responses.ResponseSignIn;
import retrofit.RetrofitError;

/**
 * Fragment class for sign in
 */
public class FragmentSignIn extends BaseFragment implements View.OnClickListener, OnDataReadListener, OnErrorListener {

    private ControllerSignIn controller;
    private EditText username;
    private EditText password;

    /**
     *
     * @return fragment layout
     */
    @Override
    public int provideLayoutRes() {
        return R.layout.fragment_sign_in;
    }

    /**
     *
     */
    @Override
    public void main() {
        init();
    }

    /**
     * Method for fragment setup
     */
    private void init() {
        ((Button) getView().findViewById(R.id.btSignIn)).setOnClickListener(this);
        controller = new ControllerSignIn(getActivity());
        controller.setOnDataReadListener(this);
        controller.setOnErrorListener(this);
        password = (EditText) getView().findViewById(R.id.etPassword);
        username = (EditText) getView().findViewById(R.id.etUsername);
    }

    /**
     *
     * @param view that has been clicked
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSignIn:
                hideKeyboard();
                controller.signIn(getData());
                break;
        }
    }

    /**
     *  Method that hide showing keyboard
     */
    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(username.getWindowToken(), 0);

    }

    /**
     *
     * @return request building method
     */
    private RequestSignIn getData() {
        RequestSignIn data = new RequestSignIn();
        data.setPassword(password.getText().toString());
        data.setUsername(username.getText().toString());
        return data;
    }

    /**
     *
     * @param response obtained from server
     */
    @Override
    public void onDataRead(LightResponse response) {
        ResponseSignIn res = (ResponseSignIn) response;
        if (res.getCode() == getResources().getInteger(R.integer.success_code)) {
            ManagerSession.saveToken(getActivity().getBaseContext(), res.getToken());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentAccounts()).commit();
        } else {
            toastIt(res.getMessage());
        }
        controller.dismissDialog();
    }

    /**
     *
     * @param error obtained from server
     */
    @Override
    public void onError(RetrofitError error) {
        controller.dismissDialog();

    }

    /**
     *
     * @return
     */
    @Override
    public int getMenuResource() {
        return 0;
    }
}
