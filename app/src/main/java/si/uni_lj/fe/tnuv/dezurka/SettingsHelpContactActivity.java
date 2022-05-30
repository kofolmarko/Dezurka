package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SettingsHelpContactActivity extends AppCompatActivity {

    TextView phone;
    TextView location;
    TextView email;
    TextView webpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_help_contact);

        setupHamburgerMenu(this);
        setupToolbar(getString(R.string.settings_help_text_2), this);

        setText();
    }

    private void setText() {
        phone = findViewById(R.id.phone_text);
        location = findViewById(R.id.location_text);
        email = findViewById(R.id.email_text);
        webpage = findViewById(R.id.webpage_text);

        phone.setText(R.string.uprava_phone_number);
        location.setText(R.string.uprava_address);
        email.setText(R.string.uprava_email);
        webpage.setText(R.string.uprava_webpage);
    }
}