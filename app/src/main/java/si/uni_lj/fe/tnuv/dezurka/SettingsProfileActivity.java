package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsProfileActivity extends AppCompatActivity {

    ConstraintLayout logoutBtn;
    ConstraintLayout myStudentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_profile);

        setupHamburgerMenu(this);
        setupToolbar(getString(R.string.settings_text_3), this);

        logoutBtn = findViewById(R.id.btn_logout);
        myStudentBtn = findViewById(R.id.btn_mojstudent);

        setText();
        setOnClickListeners();
    }

    private void setText() {
        TextView logoutBtnText = logoutBtn.findViewById(R.id.text);
        TextView myStudentBtnText = myStudentBtn.findViewById(R.id.text);

        logoutBtnText.setText(R.string.logout_text);
        myStudentBtnText.setText(R.string.mystudent_text);
    }

    private void setOnClickListeners() {
        logoutBtn.setOnClickListener(view -> {
            //logout code
        });

        myStudentBtn.setOnClickListener(view -> {
            String url = "https://student.sd-lj.si/login";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
    }
}