package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class AvailableDatesActivity extends AppCompatActivity {

    private ConstraintLayout reserveBtn1;
    private ConstraintLayout reserveBtn2;
    private ConstraintLayout reserveBtn3;
    private ConstraintLayout reserveBtn4;
    private CheckBox filter1;
    private CheckBox filter2;
    private CheckBox filter3;
    private CheckBox filter4;

    private ConstraintLayout cancelBtn;
    private ConstraintLayout confirmBtn;
    private TextView tvCancel;
    private TextView tvConfirm;

    private TextView tvReserveDate;
    private TextView tvReserveTime;
    private TextView tvReserveHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_dates);
/*
        reserveBtn1 = findViewById(R.id.reserve_btn_1);
        reserveBtn2 = findViewById(R.id.reserve_btn_2);
        reserveBtn3 = findViewById(R.id.reserve_btn_3);
        reserveBtn4 = findViewById(R.id.reserve_btn_4);*/
        filter1 = findViewById(R.id.filter_1);
        filter2 = findViewById(R.id.filter_2);
        filter3 = findViewById(R.id.filter_3);
        filter4 = findViewById(R.id.filter_4);

        setText();
        setOnclickListeners();

        setupHamburgerMenu(this);
        setupToolbar(getResources().getString(R.string.available_dates_btn), this);
    }

    private void setText() {/*
        TextView text1 = reserveBtn1.findViewById(R.id.text);
        text1.setText(R.string.hours_filter_1);
        TextView text2 = reserveBtn2.findViewById(R.id.text);
        text2.setText(R.string.hours_filter_2);
        TextView text3 = reserveBtn3.findViewById(R.id.text);
        text3.setText(R.string.hours_filter_3);
        TextView text4 = reserveBtn4.findViewById(R.id.text);
        text4.setText(R.string.hours_filter_4);*/
    }

    private void setOnclickListeners() {/*
        reserveBtn1.setOnClickListener(view -> {
            openDialog();
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
        });*/
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_reserve, null);

        builder.setView(v)
                .setTitle(Html.fromHtml("<font color='#FFFFFF'>Rezervirali boste termin</font>"));

        AlertDialog dialog = builder.create();
        dialog.show();

        tvReserveDate = v.findViewById(R.id.reserve_date);
        tvReserveTime = v.findViewById(R.id.reserve_time);
        tvReserveHome = v.findViewById(R.id.reserve_home);

        tvReserveDate.setText("Rezerviran datum");
        tvReserveTime.setText("Rezerviran čas");
        tvReserveHome.setText("Rezerviran dom");

        cancelBtn = v.findViewById(R.id.trade_cancel);
        confirmBtn = v.findViewById(R.id.trade_confirm);
        tvCancel = cancelBtn.findViewById(R.id.text);
        tvConfirm = confirmBtn.findViewById(R.id.text);

        tvCancel.setText("Prekliči");
        tvConfirm.setText("Potrdi");

        cancelBtn.setOnClickListener(view -> {
           dialog.cancel();
        });
    }

}