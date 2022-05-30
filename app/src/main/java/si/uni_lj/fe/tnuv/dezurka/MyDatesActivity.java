package si.uni_lj.fe.tnuv.dezurka;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MyDatesActivity extends AppCompatActivity {

    private ListView myDatesList;
    private TextView tvAvailableTrades;
    private ConstraintLayout btnShowDeals;
    private TextView tvShowDeals;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<Date> arrayOfDates = new ArrayList<Date>();

    static String DETAILSDATE = "date";
    static String DETAILSTIME = "time";
    static String DETAILSPERSON = "person";
    static String DETAILSHOME = "home";

    public static class Date {
        public String date;
        public String time;
        public String person;
        public String home;

        public Date(String date, String time, String person, String home) {
            this.date = date;
            this.time = time;
            this.person = person;
            this.home = home;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dates);

        setupHamburgerMenu(this);
        setupToolbar(getResources().getString(R.string.my_dates_btn), this);

        myDatesList = findViewById(R.id.my_dates_list);

        tvAvailableTrades = findViewById(R.id.available_trades_text);
        int numOfTrades = 2;
        tvAvailableTrades.setText("Aktivnih ponudb za menjavo: " + numOfTrades);

        btnShowDeals = findViewById(R.id.btn_show_trade_deals);
        tvShowDeals = btnShowDeals.findViewById(R.id.text);
        tvShowDeals.setText("Prikaži ponudbe");

        Intent intent = getIntent();
        String myNextDate = intent.getStringExtra(DashboardActivity.MYNEXTDATE);
        TextView myNextDateText = findViewById(R.id.my_dates_text);
        myNextDateText.setText(myNextDate);

        showDates();

        myDatesList.setOnItemClickListener(((adapterView, view, i, l) -> {
            Date selectedDate = arrayOfDates.get(i);

            Intent detailsIntent = new Intent(this, MyDateDetailsActivity.class);
            detailsIntent.putExtra(DETAILSTIME, selectedDate.time);
            detailsIntent.putExtra(DETAILSDATE, selectedDate.date);
            detailsIntent.putExtra(DETAILSPERSON, selectedDate.person);
            detailsIntent.putExtra(DETAILSHOME, selectedDate.home);

            startActivity(detailsIntent);
        }));

        btnShowDeals.setOnClickListener(view -> {
            Intent availableTrades = new Intent(this, MyTradeDealsActivity.class);
            startActivity(availableTrades);
        });
    }

    public static class DatesAdapter extends ArrayAdapter<Date> {
        public DatesAdapter(Context context, ArrayList<Date> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Date date = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_my_dates, parent, false);
            }

            TextView tvDate = convertView.findViewById(R.id.date);
            TextView tvTime = convertView.findViewById(R.id.time);

            tvDate.setText(date.date);
            tvTime.setText(date.time);

            return convertView;
        }
    }

    private void showDates() {
        DatesAdapter adapter = new DatesAdapter(this, arrayOfDates);

        DocumentReference currentUserData = db.collection("users").document(mAuth.getCurrentUser().getUid());

        currentUserData.get().addOnSuccessListener(documentSnapshot -> {
            Map data = documentSnapshot.getData();
            if (data == null) return;
            ArrayList<DocumentReference> ownedDates = (ArrayList<DocumentReference>) data.get("owned_dates");
            for (DocumentReference ownedDate : ownedDates) {
                ownedDate.get()
                        .addOnSuccessListener(documentSnapshot1 -> {
                            Map data1 = documentSnapshot1.getData();
                            adapter.add(new MyDatesActivity.Date(
                                    (String) data1.get("date"),
                                    (String) data1.get("time"),
                                    (String) data1.get("owner"),
                                    (String) data1.get("campus") + ", Dom " + (String) data1.get("dorm"))
                            );
                        });
            }
        });

        myDatesList.setAdapter(adapter);

        //generateDatesScript();
    }

void generateDatesScript() {

    for (int i = 0; i < 15; i++) {

        String d = i+10 + ". 6. 2022";
        String t;
        if(i%2 == 0) {
            t = "18h - 24h";
        } else {
            t = "06h - 12h";
        }

        String dorm = i+1 + "";

        Map<String, Object> date = new HashMap<>();
        date.put("date", d);
        date.put("time", t);
        date.put("dorm", dorm);
        date.put("campus", "Rožna Dolina");
        date.put("owner", null);
        date.put("is tradable", false);

// Add a new document with a generated ID
        db.collection("dates")
                .add(date)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
}