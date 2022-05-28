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
import java.util.Map;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MyDatesActivity extends AppCompatActivity {

    private ListView myDatesList;
    private TextView tvAvailableTrades;
    private ConstraintLayout btnShowDeals;
    private TextView tvShowDeals;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("dates")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map data = document.getData();
                                String date = data.get("term").toString();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Date newDate0 = new Date(date, "12h - 18h", "Jurij Sokol", "Dom 5, Rožna Dolina");
                                adapter.add(newDate0);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        Date newDate1 = new Date("26.12.2022", "18h - 24h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        Date newDate2 = new Date("29.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        Date newDate3 = new Date("30.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");

        adapter.add(newDate1);
        adapter.add(newDate2);
        adapter.add(newDate3);

        myDatesList.setAdapter(adapter);
    }

}