package si.uni_lj.fe.tnuv.dezurka;

import static android.content.ContentValues.TAG;
import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    private ProgressBar progressBar;

    private ListView availableDatesList;

    ArrayList<AvailableDate> arrayOfAvailableDates = new ArrayList<>();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static class AvailableDate {
        public String date;
        public ArrayList time;
        public DocumentReference ref;
        public String home;

        public AvailableDate(String date, ArrayList time, DocumentReference ref, String home) {
            this.date = date;
            this.time = time;
            this.ref = ref;
            this.home = home;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_dates);

        progressBar = findViewById(R.id.progress_bar);
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
            adapterView.toString();
            AvailableDate listItem = (AvailableDate) availableDatesList.getItemAtPosition(i);
            openDialog(listItem);
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

    private void openDialog(AvailableDate listItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_reserve_date, null);

        builder.setView(v)
                .setTitle(Html.fromHtml("<font color='#FFFFFF'>Rezervirali boste termin</font>"));

        AlertDialog dialog = builder.create();
        dialog.show();

        tvReserveDate = v.findViewById(R.id.current_date);

        tvReserveDate.setText(listItem.date);

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

        if (listItem.time.contains("00h - 06h")) {
            btn00.setOnClickListener(e -> {
                removeTimeFromDb(listItem, "00h - 06h");
                addDateToMyDates(listItem, "00h - 06h");
                taskComplete();
                dialog.cancel();
            });
        } else {
            btn00.setEnabled(false);
            btn00.setAlpha(0.5f);
        }
        if (listItem.time.contains("06h - 12h")) {
            btn06.setOnClickListener(e -> {
                removeTimeFromDb(listItem, "06h - 12h");
                addDateToMyDates(listItem, "06h - 12h");
                taskComplete();
                dialog.cancel();
            });
        } else {
            btn06.setEnabled(false);
            btn06.setAlpha(0.5f);
        }
        if (listItem.time.contains("12h - 18h")) {
            btn12.setOnClickListener(e -> {
                removeTimeFromDb(listItem, "12h - 18h");
                addDateToMyDates(listItem, "12h - 18h");
                taskComplete();
                dialog.cancel();
            });
        } else {
            btn12.setEnabled(false);
            btn12.setAlpha(0.5f);
        }
        if (listItem.time.contains("18h - 24h")) {
            btn18.setOnClickListener(e -> {
                removeTimeFromDb(listItem, "18h - 24h");
                addDateToMyDates(listItem, "18h - 24h");
                taskComplete();
                dialog.cancel();
            });
        } else {
            btn18.setEnabled(false);
            btn18.setAlpha(0.5f);
        }

        cancelBtn.setOnClickListener(view -> {
            dialog.cancel();
        });
    }

    private void taskComplete() {
        Intent taskComplete = new Intent(AvailableDatesActivity.this, TaskCompleteActivity.class);
        taskComplete.putExtra("first_text", "Rezervacija uspešna");
        taskComplete.putExtra("second_text", "Preostaneta ti še 2 rezervaciji.");
        taskComplete.putExtra("third_text", "DOMOV");
        taskComplete.putExtra("fourth_text", "TERMINI");
        this.finish();
        startActivity(taskComplete);
    }

    private void addDateToMyDates(AvailableDate listItem, String time) {
        Map<String, Object> date = new HashMap<>();
        date.put("date", listItem.date);
        date.put("time", time);
        date.put("dorm", "Dom 5");
        date.put("campus", "Rožna Dolina");
        date.put("owner", db.document("/users/"+mAuth.getCurrentUser().getUid()));
        date.put("is_tradable", false);
        date.put("created_at", FieldValue.serverTimestamp());

        db.collection("dates")
                .add(date)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        DocumentReference currentUserData = db.collection("users").document(mAuth.getCurrentUser().getUid());
                        currentUserData.get().addOnSuccessListener(snapshot -> {
                            Map data = snapshot.getData();
                            ArrayList<DocumentReference> ownedDates = (ArrayList<DocumentReference>) data.get("owned_dates");
                            ownedDates.add(documentReference);
                            currentUserData.update("owned_dates", ownedDates);
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void removeTimeFromDb(AvailableDate listItem, String time) {
        listItem.time.remove(time);
        listItem.ref.update("time", listItem.time);

    }

    private void showDates() {
        AvailableDatesAdapter adapter = new AvailableDatesAdapter(this, arrayOfAvailableDates);

        db.collection("available_dates")
                .orderBy("created_at")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map data = document.getData();
                            ArrayList time = (ArrayList) data.get("time");

                            if (data == null) return;
                                adapter.add(new AvailableDate(
                                        (String) data.get("date"),
                                        (ArrayList) data.get("time"),
                                        (DocumentReference) data.get("ref"),
                                        (String) data.get("campus") + ", Dom " + (String) data.get("dorm"))
                                );
                        }

                       // for (MyDatesActivity.Date date : dates) {

                       // }
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

        availableDatesList.setAdapter(adapter);

        //generateDatesScript();
    }

    public static class AvailableDatesAdapter extends ArrayAdapter<AvailableDate> {
        public AvailableDatesAdapter(Context context, ArrayList<AvailableDate> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AvailableDate date = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_available_date, parent, false);
            }

            TextView tvDate = convertView.findViewById(R.id.available_dates_date);
            TextView tvCounter = convertView.findViewById(R.id.available_dates_counter);

            tvDate.setText(date.date);
            tvCounter.setText(date.time.size() + "/4");

            return convertView;
        }
    }

}