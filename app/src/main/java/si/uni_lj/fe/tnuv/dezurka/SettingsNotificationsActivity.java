package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsNotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notifications);

        setupHamburgerMenu(this);
        setupToolbar(getString(R.string.settings_text_2), this);
    }
}