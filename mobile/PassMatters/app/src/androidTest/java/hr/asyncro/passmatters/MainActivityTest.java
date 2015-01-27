package hr.asyncro.passmatters;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.activities.ActivityMain;
import eu.asyncro.passmatters.fragments.FragmentSignIn;
import eu.asyncro.passmatters.widgets.ThreeDListView;

/**
 * Created by ahuskano on 1/27/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<ActivityMain> {

    // sign in screen
    private ActivityMain activity;
    private EditText usernameEt;
    private EditText passwordEt;
    private Button button;
    private TextView usernameTv;
    private TextView passwordTv;
    private ImageView logo;

    public MainActivityTest() {
        super(ActivityMain.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        usernameEt = (EditText) activity.findViewById(R.id.etUsername);
        passwordEt = (EditText) activity.findViewById(R.id.etPassword);
        button = (Button) activity.findViewById(R.id.btSignIn);
        usernameTv = (TextView) activity.findViewById(R.id.tvUsername);
        passwordTv = (TextView) activity.findViewById(R.id.tvPassword);
        logo = (ImageView) activity.findViewById(R.id.ivLogo);
    }

    public void testPreconditions() {
        assertNotNull("Activity is null", activity);
        assertNotNull("EditText for username is null", usernameEt);
        assertNotNull("EditText for password is null", passwordEt);
        assertNotNull("Button for sign in is null", button);
        assertNotNull("TextView for username is null", usernameTv);
        assertNotNull("TextView for password is null", passwordTv);
        assertNotNull("Logo is null", logo);
    }

    public void testLabels() {
        assertEquals("Buttons label is wrong", button.getText().toString(), activity.getString(R.string.bt_sign_in));
        assertEquals("Username textview is wrong", usernameTv.getText().toString(), activity.getString(R.string.username_placeholder));
        assertEquals("Password textview is wrong", passwordTv.getText().toString(), activity.getString(R.string.password_placeholder));
    }

    public void testViews() {
        assertEquals("Button for sign in is not showing", button.getVisibility(), View.VISIBLE);
        assertEquals("Username textview is not showing", usernameTv.getVisibility(), View.VISIBLE);
        assertEquals("Password textview is not showing", usernameTv.getVisibility(), View.VISIBLE);
        assertEquals("Username edittext is not showing", usernameEt.getVisibility(), View.VISIBLE);
        assertEquals("Password edittext is not showing", passwordEt.getVisibility(), View.VISIBLE);
        assertEquals("Logo is not showing", logo.getVisibility(), View.VISIBLE);
    }

    public void testKeyboard(){
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        assertEquals("Keyboard is not hide after button click", imm.isActive(), true);
    }

    public void testFragment(){
        FragmentSignIn fragmentSignIn = (FragmentSignIn) waitForFragment(FragmentSignIn.class.getSimpleName(),5000);
        assertNotNull("Fragment je null",fragmentSignIn);
    }

    protected Fragment waitForFragment(String tag, int timeout) {
        long endTime = SystemClock.uptimeMillis() + timeout;
        while (SystemClock.uptimeMillis() <= endTime) {

            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment != null) {
                return fragment;
            }
        }
        return null;
    }
}
