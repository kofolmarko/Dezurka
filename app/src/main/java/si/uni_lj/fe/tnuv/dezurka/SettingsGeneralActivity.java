package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class SettingsGeneralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_general);

        setupDropdownMenu();

        setupHamburgerMenu(this);
        setupToolbar(getString(R.string.settings_text_1), this);
    }

    private void setupDropdownMenu() {
        Spinner dropDownMenu = findViewById(R.id.dropdown_menu);
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.languages, R.layout.dropdown_menu_item);
        adapter.setDropDownViewResource(R.layout.dropdown_menu_item);
        dropDownMenu.setAdapter(adapter);
    }
}