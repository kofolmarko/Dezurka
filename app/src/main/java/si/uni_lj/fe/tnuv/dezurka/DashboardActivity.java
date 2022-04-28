package si.uni_lj.fe.tnuv.dezurka;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import java.util.Date;

public class DashboardActivity extends AppCompatActivity {

    private ConstraintLayout myDatesBtn;
    private ConstraintLayout availableDatesBtn;
    private ConstraintLayout tradesBtn;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setButtonText();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void setButtonText() {
        myDatesBtn = findViewById(R.id.my_dates_btn);
        availableDatesBtn = findViewById(R.id.available_dates_btn);
        tradesBtn = findViewById(R.id.trades_btn);

        String[] myDatesText = generateMyDatesText(myDatesBtn);

        setText(myDatesBtn, myDatesText);
        // setText(availableDatesBtn);
        // setText(tradesBtn);
    }

    private String[] generateMyDatesText(ConstraintLayout myDatesBtn) {
        String[] res = new String[3];
        res[0] = (getResources().getString(R.string.my_dates_btn));
        res[1] = getResources().getString(R.string.my_dates_text2_p1) +
                    getMyNextDate() +
                    getResources().getString(R.string.my_dates_text2_p2);
        res[2] = getMyNextDate().toString();

        return res;
    }

    private Date getMyNextDate() {
        Date res = new Date();
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