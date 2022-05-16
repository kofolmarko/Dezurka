package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class TradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trades);

        setupHamburgerMenu(this);
        setupToolbar(getResources().getString(R.string.trades_btn), this);
    }
}