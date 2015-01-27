package hr.asyncro.passmatters;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import eu.asyncro.passmatters.R;
import eu.asyncro.passmatters.activities.ActivityMain;

/**
 * Created by ahuskano on 1/27/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<ActivityMain> {

    private ActivityMain activity;
    private EditText usernameEt;
    private EditText passwordEt;
    private Button button;
    private TextView usernameTv;
    private TextView passwordTv;

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
    }

    public void testPreconditions() {
        assertNotNull("Activity is null", activity);
        assertNotNull("EditText for username is null", usernameEt);
        assertNotNull("EditText for password is null", passwordEt);
        assertNotNull("Button for sign in is null", button);
        assertNotNull("TextView for username is null", usernameTv);
        assertNotNull("TextView for password is null", passwordTv);
    }

    public void testLabels() {
        assertEquals("Buttons label is wrong", button.getText().toString(), activity.getString(R.string.bt_sign_in));
        assertEquals("Username textview is wrong", usernameTv.getText().toString(), activity.getString(R.string.username_placeholder));
        assertEquals("Password textview is wrong", passwordTv.getText().toString(), activity.getString(R.string.password_placeholder));

    }

}
