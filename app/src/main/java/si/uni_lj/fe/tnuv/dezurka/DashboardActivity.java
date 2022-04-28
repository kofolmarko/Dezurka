package si.uni_lj.fe.tnuv.dezurka;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.View;
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
    private ConstraintLayout hamburgerBtn;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        myDatesBtn = findViewById(R.id.my_dates_btn);
        availableDatesBtn = findViewById(R.id.available_dates_btn);
        tradesBtn = findViewById(R.id.trades_btn);
        hamburgerBtn = findViewById(R.id.hamburger_menu_btn);

        setButtonText();
        setIconBlendMode();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void setIconBlendMode() {
        ImageView myDatesIcon = myDatesBtn.findViewById(R.id.dashboard_btn_icon);
        ImageView availableDatesIcon = availableDatesBtn.findViewById(R.id.dashboard_btn_icon);
        ImageView tradesIcon = tradesBtn.findViewById(R.id.dashboard_btn_icon);
        ImageView hamburgerIcon = hamburgerBtn.findViewById(R.id.hamburger_menu_btn_icon);

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
}