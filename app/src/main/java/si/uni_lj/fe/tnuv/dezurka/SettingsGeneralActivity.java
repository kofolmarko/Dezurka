package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;

public class SettingsGeneralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_general);

        setupHamburgerMenu(this);
        setupToolbar(getString(R.string.settings_text_1), this);

        CheckBox c = findViewById(R.id.available_dates_ntf_checkbox);
        c.setButtonDrawable(R.drawable.custom_checkbox);
    }
}