package si.uni_lj.fe.tnuv.dezurka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

public class MyDatesActivity extends AppCompatActivity {

    ListView myDatesList;
    CalendarView myDatesCalendar;

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

        ConstraintLayout btnList = findViewById(R.id.btn_list);
        ConstraintLayout btnCalendar = findViewById(R.id.btn_calendar);
        TextView myNextDateText = findViewById(R.id.my_dates_text);
        myDatesList = findViewById(R.id.my_dates_list);
        myDatesCalendar = findViewById(R.id.my_dates_calendar);

        TextView listBtn = btnList.findViewById(R.id.text);
        TextView calendarBtn = btnCalendar.findViewById(R.id.text);
        listBtn.setText("Seznam");
        calendarBtn.setText("Koledar");

        Intent intent = getIntent();
        String myNextDate = intent.getStringExtra(DashboardActivity.MYNEXTDATE);

        myNextDateText.setText(myNextDate);

        showDates();

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatesCalendar.setVisibility(View.INVISIBLE);
                myDatesList.setVisibility(View.VISIBLE);
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatesList.setVisibility(View.INVISIBLE);
                myDatesCalendar.setVisibility(View.VISIBLE);
            }
        });

        myDatesList.setOnItemClickListener(((adapterView, view, i, l) -> {
            Date selectedDate = arrayOfDates.get(i);

            Intent detailsIntent = new Intent(this, DetailsActivity.class);
            detailsIntent.putExtra(DETAILSTIME, selectedDate.time);
            detailsIntent.putExtra(DETAILSDATE, selectedDate.date);
            detailsIntent.putExtra(DETAILSPERSON, selectedDate.person);
            detailsIntent.putExtra(DETAILSHOME, selectedDate.home);

            startActivity(detailsIntent);
        }));

    }

    public static class DatesAdapter extends ArrayAdapter<Date> {
        public DatesAdapter(Context context, ArrayList<Date> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Date date = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_dates_item, parent, false);
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