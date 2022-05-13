package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private ConstraintLayout settingsBtn1;
    private ConstraintLayout settingsBtn2;
    private ConstraintLayout settingsBtn3;
    private ConstraintLayout settingsBtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsBtn1 = findViewById(R.id.settings_btn_1);
        settingsBtn2 = findViewById(R.id.settings_btn_2);
        settingsBtn3 = findViewById(R.id.settings_btn_3);
        settingsBtn4 = findViewById(R.id.settings_btn_4);

        setText();
        setIcons();
        setOnClickListeners();

        setupHamburgerMenu(this);
        setupToolbar(getString(R.string.settings), this);
    }

    private void setText() {
        TextView tv1 = settingsBtn1.findViewById(R.id.text);
        TextView tv2 = settingsBtn2.findViewById(R.id.text);
        TextView tv3 = settingsBtn3.findViewById(R.id.text);
        TextView tv4 = settingsBtn4.findViewById(R.id.text);

        tv1.setText(getResources().getString(R.string.settings_text_1));
        tv2.setText(getResources().getString(R.string.settings_text_2));
        tv3.setText(getResources().getString(R.string.settings_text_3));
        tv4.setText(getResources().getString(R.string.settings_text_4));
    }

    private void setIcons() {
        ImageView settingsIcon1 = settingsBtn1.findViewById(R.id.icon);
        ImageView settingsIcon2 = settingsBtn2.findViewById(R.id.icon);
        ImageView settingsIcon3 = settingsBtn3.findViewById(R.id.icon);
        ImageView settingsIcon4 = settingsBtn4.findViewById(R.id.icon);

        settingsIcon1.setImageResource(R.drawable.icon_settings);
        settingsIcon2.setImageResource(R.drawable.icon_notification);
        settingsIcon3.setImageResource(R.drawable.icon_user);
        settingsIcon4.setImageResource(R.drawable.icon_question);
    }

    private void setOnClickListeners() {
        settingsBtn1.setOnClickListener(view -> {
            Intent i = new Intent(SettingsActivity.this, SettingsGeneralActivity.class);
            startActivity(i);
        });

        settingsBtn2.setOnClickListener(view -> {
            Intent i = new Intent(SettingsActivity.this, SettingsNotificationsActivity.class);
            startActivity(i);
        });

        settingsBtn3.setOnClickListener(view -> {
            Intent i = new Intent(SettingsActivity.this, SettingsProfileActivity.class);
            startActivity(i);
        });

        settingsBtn4.setOnClickListener(view -> {
            Intent i = new Intent(SettingsActivity.this, SettingsHelpActivity.class);
            startActivity(i);
        });
    }
}