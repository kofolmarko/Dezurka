package si.uni_lj.fe.tnuv.dezurka;

import androidx.appcompat.app.AppCompatActivity;
import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddTradeActivity extends AppCompatActivity {

    ListView myTradesList;

    ArrayList<MyDatesActivity.Date> arrayOfMyTrades = new ArrayList<MyDatesActivity.Date>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trade);

        myTradesList = findViewById(R.id.my_trades_list);

        showMyTrades();

    }

    public static class MyTradesAdapter extends ArrayAdapter<MyDatesActivity.Date> {
        public MyTradesAdapter(Context context, ArrayList<MyDatesActivity.Date> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyDatesActivity.Date date = getItem(position);

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

    private void showMyTrades() {
        MyDatesActivity.DatesAdapter adapter = new MyDatesActivity.DatesAdapter(this, arrayOfMyTrades);

        MyDatesActivity.Date newDate0 = new MyDatesActivity.Date("21.12.2022", "12h - 18h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate1 = new MyDatesActivity.Date("26.12.2022", "18h - 24h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate2 = new MyDatesActivity.Date("29.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate3 = new MyDatesActivity.Date("30.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        adapter.add(newDate0);
        adapter.add(newDate1);
        adapter.add(newDate2);
        adapter.add(newDate3);

        myTradesList.setAdapter(adapter);
    }
}