package si.uni_lj.fe.tnuv.dezurka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

public class AddTradeActivity extends AppCompatActivity {

    private ListView myTradesList;
    private ConstraintLayout addTradeBtn;
    private TextView tvAddTradeBtn;

    private ConstraintLayout btnCancel;
    private ConstraintLayout btnConfirm;
    private TextView tvCancel;
    private TextView tvConfirm;
    private TextView tvDialog;

    private CheckBox cbTrade;
    private CheckBox cbSell;
    private String stType;

    ArrayList<MyDatesActivity.Date> arrayOfMyTrades = new ArrayList<MyDatesActivity.Date>();

    public int selectedIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trade);

        setupHamburgerMenu(this);
        setupToolbar("Dodaj ponudbo", this);

        myTradesList = findViewById(R.id.my_trades_list);
        showMyTrades();

        addTradeBtn = findViewById(R.id.confirm_trade_btn);
        tvAddTradeBtn = addTradeBtn.findViewById(R.id.text);
        tvAddTradeBtn.setText("Potrdi");
        addTradeBtn.setOnClickListener(view -> {
            confirmDialog(selectedIndex, stType);
        });

        cbTrade = findViewById(R.id.cb_trade);
        cbSell = findViewById(R.id.cb_sell);

        cbTrade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked && cbSell.isChecked()) {
                    cbSell.toggle();
                    stType = "Menjava";
                }
                if (isChecked) stType = "Menjava";
            }
        });

        cbSell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked && cbTrade.isChecked()) {
                    cbTrade.toggle();
                    stType = "Oddaja";
                }
                if (isChecked) stType = "Oddaja";
            }
        });

        myTradesList.setOnItemClickListener(((adapterView, view, i, l) -> {
            Toast.makeText(this, "asd", Toast.LENGTH_LONG);
        }));
    }

    private void confirmDialog(int selectedIndex, String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_confirm, null);

        builder.setView(v)
                .setTitle(Html.fromHtml("<font color='#FFFFFF'>Ste prepričani da želite objaviti ponudbo?</font>"));

        AlertDialog dialog = builder.create();
        dialog.show();

        MyDatesActivity.Date selectedDate = arrayOfMyTrades.get(selectedIndex);

        tvDialog = v.findViewById(R.id.text_confirm);
        tvDialog.setText(type + " termina " + selectedDate.date);

        btnCancel = v.findViewById(R.id.btn_cancel);
        btnConfirm = v.findViewById(R.id.btn_confirm);
        tvCancel = btnCancel.findViewById(R.id.text);
        tvConfirm = btnConfirm.findViewById(R.id.text);
        tvCancel.setText("Prekliči");
        tvConfirm.setText("Potrdi");

        btnCancel.setOnClickListener(view -> {
            dialog.cancel();
        });
    }

    public static class MyTradesAdapter extends ArrayAdapter<MyDatesActivity.Date> {
        public MyTradesAdapter(Context context, ArrayList<MyDatesActivity.Date> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyDatesActivity.Date date = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_my_dates_for_trade, parent, false);
            }

            TextView tvDate = convertView.findViewById(R.id.date);
            TextView tvTime = convertView.findViewById(R.id.time);

            tvDate.setText(date.date);
            tvTime.setText(date.time);

            String text = "trade_cb" + position;
            CheckBox cbId = convertView.findViewById(R.id.cb_pick_date);
            cbId.setTag(text);

            return convertView;
        }
    }

    private void showMyTrades() {
        MyTradesAdapter adapter = new MyTradesAdapter(this, arrayOfMyTrades);
/*
        MyDatesActivity.Date newDate0 = new MyDatesActivity.Date("21.12.2022", "12h - 18h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate1 = new MyDatesActivity.Date("26.12.2022", "18h - 24h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate2 = new MyDatesActivity.Date("29.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate3 = new MyDatesActivity.Date("30.12.2022", "00h - 06h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        adapter.add(newDate0);
        adapter.add(newDate1);
        adapter.add(newDate2);
        adapter.add(newDate3);
*/
        myTradesList.setAdapter(adapter);
    }

}