package si.uni_lj.fe.tnuv.dezurka;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class TradesActivity extends AppCompatActivity {

    private ConstraintLayout filterBtn;
    private ListView availableTradesList;

    private ConstraintLayout tradeTypeBtn;
    private ConstraintLayout sellTypeBtn;
    private ConstraintLayout h00btn;
    private ConstraintLayout h06btn;
    private ConstraintLayout h12btn;
    private ConstraintLayout h18btn;
    private ConstraintLayout cancelBtn;
    private ConstraintLayout confirmBtn;

    private TextView tvTradeType;
    private TextView tvSellType;
    private TextView tvh00;
    private TextView tvh06;
    private TextView tvh12;
    private TextView tvh18;
    private TextView tvCancel;
    private TextView tvConfirm;

    private Spinner groupSp;
    private Spinner homeSp;

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

        ConstraintLayout filterBtn = findViewById(R.id.btn_filter);
        filterBtn.setOnClickListener(view -> {
            openDialog();
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

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.filter_dialog, null);

        builder.setView(v)
                .setTitle(Html.fromHtml("<font color='#FFFFFF'>Filtriraj</font>"));

        AlertDialog dialog = builder.create();
        dialog.show();

        tradeTypeBtn = v.findViewById(R.id.btn_filter_trade);
        sellTypeBtn = v.findViewById(R.id.btn_filter_sell);
        h00btn = v.findViewById(R.id.btn_filter_00);
        h06btn = v.findViewById(R.id.btn_filter_06);
        h12btn = v.findViewById(R.id.btn_filter_12);
        h18btn = v.findViewById(R.id.btn_filter_18);
        cancelBtn = v.findViewById(R.id.btn_filter_cancel);
        confirmBtn = v.findViewById(R.id.btn_filter_confirm);

        tvTradeType = tradeTypeBtn.findViewById(R.id.text);
        tvSellType = sellTypeBtn.findViewById(R.id.text);
        tvh00 = h00btn.findViewById(R.id.text);
        tvh06 = h06btn.findViewById(R.id.text);
        tvh12 = h12btn.findViewById(R.id.text);
        tvh18 = h18btn.findViewById(R.id.text);
        tvCancel = cancelBtn.findViewById(R.id.text);
        tvConfirm = confirmBtn.findViewById(R.id.text);

        tvTradeType.setText("Menjave");
        tvSellType.setText("Oddaje");
        tvh00.setText("00h - 06h");
        tvh06.setText("06h - 12h");
        tvh12.setText("12h - 18h");
        tvh18.setText("18h - 24h");
        tvCancel.setText("Prekliči");
        tvConfirm.setText("Potrdi");

        groupSp = v.findViewById(R.id.spinner_group);
        homeSp = v.findViewById(R.id.spinner_home);
        ArrayAdapter<CharSequence> groupAdapter = ArrayAdapter.createFromResource(this, R.array.group_array, R.layout.spinner_item);
        ArrayAdapter<CharSequence> homeAdapter = ArrayAdapter.createFromResource(this, R.array.homes_array, R.layout.spinner_item);

        groupAdapter.setDropDownViewResource(R.layout.dropdown_item);
        homeAdapter.setDropDownViewResource(R.layout.dropdown_item);
        groupSp.setAdapter(groupAdapter);
        homeSp.setAdapter(homeAdapter);
    }
}