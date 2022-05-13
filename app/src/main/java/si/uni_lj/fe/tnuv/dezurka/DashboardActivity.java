package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.graphics.BlendMode;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity {

    private ConstraintLayout myDatesBtn;
    private ConstraintLayout availableDatesBtn;
    private ConstraintLayout tradesBtn;

    public static final String MYNEXTDATE = "date";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        myDatesBtn = findViewById(R.id.my_dates_btn);
        availableDatesBtn = findViewById(R.id.available_dates_btn);
        tradesBtn = findViewById(R.id.trades_btn);

        setButtonText();
        setOnClickListeners();
        setIcons();
        setupHamburgerMenu(this);
        setupToolbar("", this);
    }

    private void setOnClickListeners() {
        myDatesBtn.setOnClickListener(view -> {
            Intent i = new Intent(DashboardActivity.this, MyDatesActivity.class);

            String[] text = generateMyDatesText();
            i.putExtra(MYNEXTDATE, text[1]);

            startActivity(i);
        });

        availableDatesBtn.setOnClickListener(view -> {
            Intent i = new Intent(DashboardActivity.this, AvailableDatesActivity.class);
            startActivity(i);
        });

        tradesBtn.setOnClickListener(view -> {
            Intent i = new Intent(DashboardActivity.this, TradesActivity.class);
            startActivity(i);
        });
    }

    private void setIcons() {
        ImageView myDatesIcon = myDatesBtn.findViewById(R.id.dashboard_btn_icon);
        ImageView availableDatesIcon = availableDatesBtn.findViewById(R.id.dashboard_btn_icon);
        ImageView tradesIcon = tradesBtn.findViewById(R.id.dashboard_btn_icon);

        myDatesIcon.setImageResource(R.drawable.mydates_btn_icon);
        availableDatesIcon.setImageResource(R.drawable.availabledates_btn_icon);
        tradesIcon.setImageResource(R.drawable.trades_btn_icon);

        // Code to apply OVERLAY blend mode
    }

    private void setButtonText() {
        String[] myDatesText = generateMyDatesText();
        String[] availableDatesText = generateAvailableDatesText();
        String[] tradesText = generateTradesText();

        setText(myDatesBtn, myDatesText);
        setText(availableDatesBtn, availableDatesText);
        setText(tradesBtn, tradesText);
    }

    private String[] generateTradesText() {
        String[] res = new String[3];
        res[0] = getResources().getString(R.string.trades_btn);
        res[1] = getResources().getString(R.string.trades_text2);
        res[2] = getNumberOfTrades() + getResources().getString(R.string.trades_text3);

        return res;
    }

    private String[] generateAvailableDatesText() {
        String[] res = new String[3];
        res[0] = (getResources().getString(R.string.available_dates_btn));
        res[1] = getResources().getString(R.string.available_dates_text2_p1) +
                getMyNextDate() +
                getResources().getString(R.string.available_dates_text2_p2);
        res[2] = getMyNextDate().toString();

        return res;
    }

    private String[] generateMyDatesText() {
        String[] res = new String[3];
        res[0] = (getResources().getString(R.string.my_dates_btn));
        res[1] = getResources().getString(R.string.my_dates_text2_p1) +
                    getMyNextDate() +
                    getResources().getString(R.string.my_dates_text2_p2);
        res[2] = getMyNextDate().toString();

        return res;
    }

    private String getNumberOfTrades() {
        return "25";
    }

    private String getMyNextDate() {
        // Adjust to calculate remaining days, create methods for other required dates
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd");
        String res = format.format(date);
        return res;
    }

    private void setText(ConstraintLayout layout, String[] text) {
        TextView tv1 = layout.findViewById(R.id.dashboard_btn_text_1);
        TextView tv2 = layout.findViewById(R.id.dashboard_btn_text_2);
        TextView tv3 = layout.findViewById(R.id.dashboard_btn_text_3);

        tv1.setText(text[0]);
        tv2.setText(text[1]);
        tv3.setText(text[2]);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(R.string.exit_popup_dialog);
        builder.setPositiveButton(R.string.exit_popup_dialog_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
        builder.setNegativeButton(R.string.exit_popup_dialog_cancel,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}