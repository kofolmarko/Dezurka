package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class AvailableDatesActivity extends AppCompatActivity {

    private ConstraintLayout reserveBtn1;
    private ConstraintLayout reserveBtn2;
    private ConstraintLayout reserveBtn3;
    private ConstraintLayout reserveBtn4;
    private CheckBox filter1;
    private CheckBox filter2;
    private CheckBox filter3;
    private CheckBox filter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_dates);

        reserveBtn1 = findViewById(R.id.reserve_btn_1);
        reserveBtn2 = findViewById(R.id.reserve_btn_2);
        reserveBtn3 = findViewById(R.id.reserve_btn_3);
        reserveBtn4 = findViewById(R.id.reserve_btn_4);
        filter1 = findViewById(R.id.filter_1);
        filter2 = findViewById(R.id.filter_2);
        filter3 = findViewById(R.id.filter_3);
        filter4 = findViewById(R.id.filter_4);

        setText();
        setOnclickListeners();

        setupHamburgerMenu(this);
        setupToolbar(getResources().getString(R.string.available_dates_btn), this);

    }

    private void setText() {
        TextView text1 = reserveBtn1.findViewById(R.id.text);
        text1.setText(R.string.hours_filter_1);
        TextView text2 = reserveBtn2.findViewById(R.id.text);
        text2.setText(R.string.hours_filter_2);
        TextView text3 = reserveBtn3.findViewById(R.id.text);
        text3.setText(R.string.hours_filter_3);
        TextView text4 = reserveBtn4.findViewById(R.id.text);
        text4.setText(R.string.hours_filter_4);
    }

    private void setOnclickListeners() {
        reserveBtn1.setOnClickListener(view -> {
            DialogFragment reserveDialog = new ReserveDialog();
            reserveDialog.show(getSupportFragmentManager(), "Rezerviraj termin");
            reserveBtn1.setEnabled(false);
            reserveBtn1.setAlpha(0.5f);
        });
        reserveBtn2.setOnClickListener(view -> {
            reserveBtn2.setEnabled(false);
            reserveBtn2.setAlpha(0.5f);
        });
        reserveBtn3.setOnClickListener(view -> {
            reserveBtn3.setEnabled(false);
            reserveBtn3.setAlpha(0.5f);
        });
        reserveBtn4.setOnClickListener(view -> {
            reserveBtn4.setEnabled(false);
            reserveBtn4.setAlpha(0.5f);
        });
    }

    public static class ReserveDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>Rezervirali boste termin</font>"))
                    .setView(R.layout.reserve_dialog);

            return builder.create();
        }
    }

}