package si.uni_lj.fe.tnuv.dezurka;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

public class MyDatesActivity extends AppCompatActivity {

    private ListView myDatesList;
    private TextView tvAvailableTrades;
    private ConstraintLayout btnShowDeals;
    private TextView tvShowDeals;
    public ProgressBar progressBar;

    private String numOfTrades;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<Date> arrayOfDates = new ArrayList<Date>();

    static String DATEREFERENCE = "ref";
    static String DETAILSDATE = "date";
    static String DETAILSTIME = "time";
    static String DETAILSPERSON = "person";
    static String DETAILSHOME = "home";

    public static class Date {
        public String date;
        public String time;
        public String person;
        public String home;
        public DocumentReference ref;

        public Date(String date, String time, String person, String home, DocumentReference ref) {
            this.date = date;
            this.time = time;
            this.person = person;
            this.home = home;
            this.ref = ref;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dates);

        setupHamburgerMenu(this);
        setupToolbar(getResources().getString(R.string.my_dates_btn), this);

        myDatesList = findViewById(R.id.my_dates_list);
        progressBar = findViewById(R.id.progress_bar);

        tvAvailableTrades = findViewById(R.id.available_trades_text);

        btnShowDeals = findViewById(R.id.btn_show_trade_deals);
        tvShowDeals = btnShowDeals.findViewById(R.id.text);
        tvShowDeals.setText("Prikaži ponudbe");
        btnShowDeals.setEnabled(false);
        btnShowDeals.setAlpha(0.5f);

        Intent intent = getIntent();
        String myNextDate = intent.getStringExtra(DashboardActivity.MYNEXTDATE);
        TextView myNextDateText = findViewById(R.id.my_dates_text);
        myNextDateText.setText(myNextDate);

        showDates();

        myDatesList.setOnItemClickListener(((adapterView, view, i, l) -> {
            Date selectedDate = arrayOfDates.get(i);

            Intent detailsIntent = new Intent(this, MyDateDetailsActivity.class);
            detailsIntent.putExtra(DATEREFERENCE, selectedDate.ref.getPath());
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
            ImageView isTradable = convertView.findViewById(R.id.is_tradable);

            tvDate.setText(date.date);
            tvTime.setText(date.time);

            date.ref.get().addOnSuccessListener(documentSnapshot -> {
               boolean isTradableB = (boolean) documentSnapshot.get("is_tradable");
               if (isTradableB) {
                   isTradable.setVisibility(View.VISIBLE);
               }
            });

            return convertView;
        }
    }

    private void showDates() {
        DatesAdapter adapter = new DatesAdapter(this, arrayOfDates);

        DocumentReference currentUserData = db.collection("users").document(mAuth.getCurrentUser().getUid());

        currentUserData.get().addOnSuccessListener(documentSnapshot -> {
            Map data = documentSnapshot.getData();
            if (data == null) return;
            ArrayList offers = (ArrayList) data.get("offers");
            if (offers != null && offers.size() > 0) {
                numOfTrades = "" + offers.size();
                btnShowDeals.setEnabled(true);
                btnShowDeals.setAlpha(1);
            } else {
                numOfTrades = "0";
            }
            tvAvailableTrades.setText("Aktivnih ponudb za menjavo: " + numOfTrades);
            ArrayList<DocumentReference> ownedDates = (ArrayList<DocumentReference>) data.get("owned_dates");
            if (ownedDates != null) {
                for (DocumentReference ownedDate : ownedDates) {
                    ownedDate.get()
                            .addOnSuccessListener(documentSnapshot1 -> {
                                Map data1 = documentSnapshot1.getData();
                                DocumentReference owner = (DocumentReference) data1.get("owner");
                                owner.get().addOnSuccessListener(ownerDoc -> {
                                    Map ownerData = ownerDoc.getData();
                                    adapter.add(new MyDatesActivity.Date(
                                            (String) data1.get("date"),
                                            (String) data1.get("time"),
                                            (String) ownerData.get("full_name"),
                                            (String) data1.get("campus") + ", " + (String) data1.get("dorm"),
                                            (DocumentReference) ownedDate)
                                    );
                                });
                            });
                }
            }
            progressBar.setVisibility(View.INVISIBLE);
        });

        myDatesList.setAdapter(adapter);
        //generateDatesScript();
    }

void generateDatesScript() {

        for (int i = 1; i < 30; i++) {

            String d = i + ". 6. 2022";
            String t[] = {
                    "00h - 06h",
                    "06h - 12h",
                    "12h - 18h",
                    "18h - 24h"
            };

                Map<String, Object> date = new HashMap<>();
                date.put("date", d);
                date.put("time", Arrays.asList(t));
                date.put("dorm", "Dom 5");
                date.put("campus", "Rožna Dolina");
                date.put("ref", (DocumentReference) null);
                date.put("is_tradable", false);
                date.put("created_at", FieldValue.serverTimestamp());

                db.collection("available_dates")
                        .add(date)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                documentReference.update("ref", documentReference);
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
