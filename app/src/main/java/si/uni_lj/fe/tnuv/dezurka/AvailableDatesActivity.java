package si.uni_lj.fe.tnuv.dezurka;

import static android.content.ContentValues.TAG;
import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

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

    private ListView availableDatesList;

    ArrayList<MyDatesActivity.Date> arrayOfAvailableDates = new ArrayList<MyDatesActivity.Date>();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        availableDatesList = findViewById(R.id.available_dates_list);
        showDates();

        availableDatesList.setOnItemClickListener(((adapterView, view, i, l) -> {
            openDialog();
        }));

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
        View v = inflater.inflate(R.layout.dialog_reserve_date, null);

        builder.setView(v)
                .setTitle(Html.fromHtml("<font color='#FFFFFF'>Rezervirali boste termin</font>"));

        AlertDialog dialog = builder.create();
        dialog.show();

        tvReserveDate = v.findViewById(R.id.current_date);

        tvReserveDate.setText("15.06.2022");

        cancelBtn = v.findViewById(R.id.trade_cancel);
        //confirmBtn = v.findViewById(R.id.trade_confirm);
        tvCancel = cancelBtn.findViewById(R.id.text);
        //tvConfirm = confirmBtn.findViewById(R.id.text);
        tvCancel.setText("Prekliči");
        //tvConfirm.setText("Potrdi");

        ConstraintLayout btn00 = v.findViewById(R.id.reserve_btn_1);
        ConstraintLayout btn06 = v.findViewById(R.id.reserve_btn_2);
        ConstraintLayout btn12 = v.findViewById(R.id.reserve_btn_3);
        ConstraintLayout btn18 = v.findViewById(R.id.reserve_btn_4);
        TextView tv00 = btn00.findViewById(R.id.text);
        TextView tv06 = btn06.findViewById(R.id.text);
        TextView tv12 = btn12.findViewById(R.id.text);
        TextView tv18 = btn18.findViewById(R.id.text);
        tv00.setText("00h - 06h");
        tv06.setText("06h - 12h");
        tv12.setText("12h - 18h");
        tv18.setText("18h - 24h");

        cancelBtn.setOnClickListener(view -> {
            dialog.cancel();
        });
    }

    private void showDates() {
        AvailableDatesAdapter adapter = new AvailableDatesAdapter(this, arrayOfAvailableDates);

        db.collection("dates")
                .whereEqualTo("owner", null)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map data = document.getData();
                            if (data == null) return;
                                adapter.add(new MyDatesActivity.Date(
                                        (String) data.get("date"),
                                        (String) data.get("time"),
                                        (String) data.get("owner"),
                                        (String) data.get("campus") + ", Dom " + (String) data.get("dorm"))
                                );
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

        availableDatesList.setAdapter(adapter);

        //generateDatesScript();
    }

    public static class AvailableDatesAdapter extends ArrayAdapter<MyDatesActivity.Date> {
        public AvailableDatesAdapter(Context context, ArrayList<MyDatesActivity.Date> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyDatesActivity.Date date = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_available_date, parent, false);
            }

            TextView tvDate = convertView.findViewById(R.id.available_dates_date);
            TextView tvCounter = convertView.findViewById(R.id.available_dates_counter);

            tvDate.setText(date.date);
            tvCounter.setText("3" + "/4");

            return convertView;
        }
    }

}