package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TradesActivity extends AppCompatActivity {

    private ConstraintLayout filterBtn;
    private ListView availableTradesList;

    ArrayList<MyDatesActivity.Date> arrayOfDates = new ArrayList<MyDatesActivity.Date>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trades);

        setupHamburgerMenu(this);
        setupToolbar(getResources().getString(R.string.trades_btn), this);

        filterBtn = findViewById(R.id.btn_filter);
        TextView filterText = filterBtn.findViewById(R.id.text);
        filterText.setText("FILTRIRAJ ISKANJE");

        showAvailableTrades();

        ConstraintLayout addTradeBtn = findViewById(R.id.btn_filter);
        addTradeBtn.setOnClickListener(view -> {
            DialogFragment filterDialog = new FilterDialog();
            filterDialog.show(getSupportFragmentManager(), "Filtriraj");
        });

    }

    public static class TradesAdapter extends ArrayAdapter<MyDatesActivity.Date> {
        public TradesAdapter(Context context, ArrayList<MyDatesActivity.Date> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyDatesActivity.Date date = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.available_trade_item, parent, false);
            }

            TextView tvDate = convertView.findViewById(R.id.available_trade_date);
            TextView tvTime = convertView.findViewById(R.id.available_trade_time);
            TextView tvPerson = convertView.findViewById(R.id.available_trade_person);
            TextView tvHome = convertView.findViewById(R.id.available_trade_home);
            TextView tvType = convertView.findViewById(R.id.available_trade_type);

            tvDate.setText(date.date);
            tvTime.setText(date.time);
            tvPerson.setText(date.person);
            tvHome.setText(date.home);
            tvType.setText("Menjava");

            return convertView;
        }
    }

    private void showAvailableTrades() {
        TradesAdapter adapter = new TradesAdapter(this, arrayOfDates);

        MyDatesActivity.Date newDate0 = new MyDatesActivity.Date("21.12.2022", "12h - 18h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate1 = new MyDatesActivity.Date("26.12.2022", "18h - 24h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate2 = new MyDatesActivity.Date("29.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate3 = new MyDatesActivity.Date("30.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate4 = new MyDatesActivity.Date("30.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");

        adapter.add(newDate0);
        adapter.add(newDate1);
        adapter.add(newDate2);
        adapter.add(newDate3);
        adapter.add(newDate4);

        availableTradesList = findViewById(R.id.available_trades_list);
        availableTradesList.setAdapter(adapter);
    }

    public static class FilterDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>Filtriraj</font>"))
                    .setView(R.layout.filter_dialog);

            return builder.create();
        }
    }
}