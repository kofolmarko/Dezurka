package si.uni_lj.fe.tnuv.dezurka;

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
import android.widget.TextView;

import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import java.util.ArrayList;

public class MyTradeDealsActivity extends AppCompatActivity {

    private ListView tradeDealsList;

    private ConstraintLayout btnCancel;
    private ConstraintLayout btnConfirm;
    private TextView tvCancel;
    private TextView tvConfirm;
    private TextView tvDialog;

    ArrayList<MyDatesActivity.Date> arrayOfTradeDeals = new ArrayList<MyDatesActivity.Date>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trade_deals);

        setupHamburgerMenu(this);
        setupToolbar("Aktivne ponudbe", this);

        tradeDealsList = findViewById(R.id.available_trade_deals_list);
        showTradeDeals();

        tradeDealsList.setOnItemClickListener(((adapterView, view, i, l) -> {
                confirmDialog(i);
        }));
    }

    private void confirmDialog(int selectedIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_confirm, null);

        builder.setView(v)
                .setTitle(Html.fromHtml("<font color='#FFFFFF'>Ste prepričani da želite sprejeti ponudbo?</font>"));

        AlertDialog dialog = builder.create();
        dialog.show();

        MyDatesActivity.Date selectedDate = arrayOfTradeDeals.get(selectedIndex);

        tvDialog = v.findViewById(R.id.text_confirm);
        tvDialog.setText("Menjava termina " + selectedDate.date + " za " + selectedDate.date);

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

    public static class TradeDealsAdapter extends ArrayAdapter<MyDatesActivity.Date> {
        public TradeDealsAdapter(Context context, ArrayList<MyDatesActivity.Date> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyDatesActivity.Date date = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_my_trade_deals, parent, false);
            }

            TextView tvDescription = convertView.findViewById(R.id.trade_deal_description);
            tvDescription.setText(date.person + " ti ponuja termin");

            TextView tvDate = convertView.findViewById(R.id.trade_deal_date);
            TextView tvTime = convertView.findViewById(R.id.trade_deal_time);
            TextView tvHome = convertView.findViewById(R.id.trade_deal_home);
            tvDate.setText(date.date);
            tvTime.setText(date.time);
            tvHome.setText(date.home);

            TextView tvDateMy = convertView.findViewById(R.id.trade_deal_date_my);
            TextView tvTimeMy = convertView.findViewById(R.id.trade_deal_time_my);
            TextView tvHomeMy = convertView.findViewById(R.id.trade_deal_home_my);
            tvDateMy.setText(date.date);
            tvTimeMy.setText(date.time);
            tvHomeMy.setText(date.home);

            return convertView;
        }
    }

    private void showTradeDeals() {
        TradeDealsAdapter adapter = new TradeDealsAdapter(this, arrayOfTradeDeals);

        MyDatesActivity.Date newDate0 = new MyDatesActivity.Date("21.12.2022", "12h - 18h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        MyDatesActivity.Date newDate1 = new MyDatesActivity.Date("26.12.2022", "18h - 24h", "Jurij Sokol", "Dom 5, Rožna Dolina");
        adapter.add(newDate0);
        adapter.add(newDate1);

        tradeDealsList.setAdapter(adapter);
    }

}