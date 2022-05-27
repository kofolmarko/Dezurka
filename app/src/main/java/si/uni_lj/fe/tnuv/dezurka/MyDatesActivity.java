package si.uni_lj.fe.tnuv.dezurka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

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

        Date newDate0 = new Date("21.12.2022", "12h - 18h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        Date newDate1 = new Date("26.12.2022", "18h - 24h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        Date newDate2 = new Date("29.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        Date newDate3 = new Date("30.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        adapter.add(newDate0);
        adapter.add(newDate1);
        adapter.add(newDate2);
        adapter.add(newDate3);

        myDatesList.setAdapter(adapter);
    }

}