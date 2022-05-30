package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsHelpActivity extends AppCompatActivity {

    private ConstraintLayout settingsBtn1;
    private ConstraintLayout settingsBtn2;
    private ConstraintLayout settingsBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_help);

        setupHamburgerMenu(this);
        setupToolbar(getString(R.string.settings_text_4), this);

        settingsBtn1 = findViewById(R.id.settings_btn_1);
        settingsBtn2 = findViewById(R.id.settings_btn_2);
        settingsBtn3 = findViewById(R.id.settings_btn_3);

        setText();
        setIcons();
        setOnClickListeners();
    }

        private void setText() {
            TextView tv1 = settingsBtn1.findViewById(R.id.text);
            TextView tv2 = settingsBtn2.findViewById(R.id.text);
            TextView tv3 = settingsBtn3.findViewById(R.id.text);

            tv1.setText(getResources().getString(R.string.settings_help_text_1));
            tv2.setText(getResources().getString(R.string.settings_help_text_2));
            tv3.setText(getResources().getString(R.string.settings_help_text_3));
        }

        private void setIcons() {
            ImageView settingsIcon1 = settingsBtn1.findViewById(R.id.icon);
            ImageView settingsIcon2 = settingsBtn2.findViewById(R.id.icon);
            ImageView settingsIcon3 = settingsBtn3.findViewById(R.id.icon);

            settingsIcon1.setVisibility(View.INVISIBLE);
            settingsIcon2.setVisibility(View.INVISIBLE);
            settingsIcon3.setVisibility(View.INVISIBLE);
        }

        private void setOnClickListeners() {
            settingsBtn1.setOnClickListener(view -> {
                Intent i = new Intent(SettingsHelpActivity.this, SettingsHelpRulesActivity.class);
                startActivity(i);
            });

            settingsBtn2.setOnClickListener(view -> {
                Intent i = new Intent(SettingsHelpActivity.this, SettingsHelpContactActivity.class);
                startActivity(i);
            });

            settingsBtn3.setOnClickListener(view -> {
                Intent i = new Intent(SettingsHelpActivity.this, SettingsHelpQaActivity.class);
                startActivity(i);
            });
        }
}