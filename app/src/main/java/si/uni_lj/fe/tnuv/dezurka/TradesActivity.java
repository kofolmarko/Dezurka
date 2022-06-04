package si.uni_lj.fe.tnuv.dezurka;

import static android.content.ContentValues.TAG;
import static si.uni_lj.fe.tnuv.dezurka.DezurkaToolbar.setupToolbar;
import static si.uni_lj.fe.tnuv.dezurka.HamburgerMenu.setupHamburgerMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TradesActivity extends AppCompatActivity {

    private ConstraintLayout filterBtn;
    private LinearLayout addTradeBtn;
    private ListView availableTradesList;
    private ListView myDatesList;

    private ConstraintLayout tradeTypeBtn;
    private ConstraintLayout sellTypeBtn;
    private ConstraintLayout h00btn;
    private ConstraintLayout h06btn;
    private ConstraintLayout h12btn;
    private ConstraintLayout h18btn;
    private ConstraintLayout cancelBtn;
    private ConstraintLayout confirmBtn;

    private ConstraintLayout cancelOfferBtn;

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

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<Trade> arrayOfDates = new ArrayList<Trade>();
    ArrayList<MyDatesActivity.Date> arrayOfMyDates = new ArrayList<MyDatesActivity.Date>();

    public static class Trade {
        public String date;
        public String time;
        public String person;
        public String home;
        public DocumentReference ref;
        public DocumentReference owner;

        public Trade(String date, String time, String person, String home, DocumentReference ref, DocumentReference owner) {
            this.date = date;
            this.time = time;
            this.person = person;
            this.home = home;
            this.ref = ref;
            this.owner = owner;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trades);

        setupHamburgerMenu(this);
        setupToolbar(getResources().getString(R.string.trades_btn), this);

        filterBtn = findViewById(R.id.btn_filter);
        TextView filterText = filterBtn.findViewById(R.id.text);
        filterText.setText("FILTRIRAJ ISKANJE");

        //addTradeBtn = findViewById(R.id.btn_add_trade);

        showAvailableTrades();

        filterBtn.setOnClickListener(view -> {
            openDialog();
        });

        //addTradeBtn.setOnClickListener(view -> {
        //    Intent i = new Intent(this, AddTradeActivity.class);
        //    startActivity(i);
        //});

        //availableTradesList.setOnItemClickListener();

    }

    public static class TradesAdapter extends ArrayAdapter<Trade> {
        public TradesAdapter(Context context, ArrayList<Trade> dates) {
            super(context, 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Trade date = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_available_trade, parent, false);
            }

            TextView tvDate = convertView.findViewById(R.id.date);
            TextView tvTime = convertView.findViewById(R.id.time);
            TextView tvPerson = convertView.findViewById(R.id.student);
            TextView tvHome = convertView.findViewById(R.id.location);
            TextView tvType = convertView.findViewById(R.id.type);
            ImageView tradeBtn = convertView.findViewById(R.id.trade_btn);

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
        DocumentReference currentUserData = db.collection("users").document(mAuth.getCurrentUser().getUid());

        db.collection("dates")
                .whereEqualTo("is_tradable", true)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map data = document.getData();
                            DocumentReference owner = (DocumentReference) data.get("owner");
                            String ownerPath = owner.getPath();
                            String currentUserPath = currentUserData.getPath();
                            if (owner == null || ownerPath.equals(currentUserPath)) continue;
                            owner.get().addOnSuccessListener(snapshot -> {
                                Map ownerData = snapshot.getData();
                                adapter.add(new Trade(
                                        (String) data.get("date"),
                                        (String) data.get("time"),
                                        (String) ownerData.get("full_name"),
                                        data.get("campus") + ", " + data.get("dorm"),
                                        document.getReference(),
                                        owner)
                                );
                            });
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
                /*.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map data1 = document.getData();
                            DocumentReference owner = (DocumentReference) data1.get("owner");
                            owner.get().addOnSuccessListener(snapshot -> {
                                Map ownerData = snapshot.getData();
                                String ownerFullName = (String) ownerData.get("full_name");

                                adapter.add(new MyDatesActivity.Date(
                                        (String) data1.get("date"),
                                        (String) data1.get("time"),
                                        ownerFullName,
                                        (String) data1.get("campus") + ", Dom " + (String) data1.get("dorm"))
                                );
                            });
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }*/);

        availableTradesList = findViewById(R.id.available_trades_list);
        availableTradesList.setAdapter(adapter);
        availableTradesList.setOnItemClickListener(((adapterView, view, i, l) -> {
            Trade trade = arrayOfDates.get(i);
            myDatesList = findViewById(R.id.my_dates_list);
            openTradeDialog(trade);
        }));
    }

    private void openTradeDialog(Trade trade) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_offer_trade, null);
        builder.setView(v);
        AlertDialog dialog = builder.create();
        dialog.show();

        myDatesList = v.findViewById(R.id.my_dates_list);
        showDates();
        myDatesList.setOnItemClickListener(((adapterView, view, i, l) -> {
            MyDatesActivity.Date selectedDate = arrayOfMyDates.get(i);
            openConfirmDialog(selectedDate.date, selectedDate.ref);
            dialog.cancel();
            trade.owner.get().addOnSuccessListener(documentSnapshot -> {
                Map data = documentSnapshot.getData();
                ArrayList offers = (ArrayList) data.get("offers");
                Map newOffer = new HashMap();
                newOffer.put("offered_date", selectedDate.ref);
                newOffer.put("your_date", trade.ref);
                offers.add(newOffer);
                trade.owner.update("offers", offers);
            });
        }));

        cancelOfferBtn = v.findViewById(R.id.btn_cancel);
        TextView btnCancelText = cancelOfferBtn.findViewById(R.id.text);
        btnCancelText.setText("Prekliči");
        cancelOfferBtn.setOnClickListener(view -> {
            dialog.cancel();
        });
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_filter, null);

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
        ArrayAdapter<CharSequence> groupAdapter = ArrayAdapter.createFromResource(this, R.array.group_array, R.layout.item_spinner);
        ArrayAdapter<CharSequence> homeAdapter = ArrayAdapter.createFromResource(this, R.array.homes_array, R.layout.item_spinner);

        groupAdapter.setDropDownViewResource(R.layout.item_dropdown);
        homeAdapter.setDropDownViewResource(R.layout.item_dropdown);
        groupSp.setAdapter(groupAdapter);
        homeSp.setAdapter(homeAdapter);

        cancelBtn.setOnClickListener(view -> {
            dialog.cancel();
        });

    }

    private void openConfirmDialog(String date, DocumentReference ref) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_confirm, null);

        builder.setView(v)
                .setTitle(Html.fromHtml("<font color='#FFFFFF'>Ste prepričani, da želite ponuditi termin?</font>"));

        cancelBtn = v.findViewById(R.id.btn_cancel);
        confirmBtn = v.findViewById(R.id.btn_confirm);
        TextView confirmDate = v.findViewById(R.id.text_confirm);
        tvCancel = cancelBtn.findViewById(R.id.text);
        tvConfirm = confirmBtn.findViewById(R.id.text);
        tvCancel.setText("Prekliči");
        tvConfirm.setText("Potrdi");
        confirmDate.setText(date);

        AlertDialog dialog = builder.create();
        dialog.show();

        confirmBtn.setOnClickListener(view -> {

            Intent taskComplete = new Intent(this, TaskCompleteActivity.class);

            taskComplete.putExtra("first_text", "Ponudba uspešno podana");
            taskComplete.putExtra("second_text", "Obveščeni boste ob sprejemu ali zavrnitvi ponudbe.");

            taskComplete.putExtra("third_text", "DOMOV");
            taskComplete.putExtra("fourth_text", "MENJAVE");
            startActivity(taskComplete);
            dialog.cancel();
            this.finish();
        });

        cancelBtn.setOnClickListener(view -> {
            dialog.cancel();
        });
    }

    private void showDates() {
        MyDatesActivity.DatesAdapter adapter = new MyDatesActivity.DatesAdapter(this, arrayOfMyDates);

        if (arrayOfMyDates.size() > 0) {
            myDatesList.setAdapter(adapter);
            return;
        }

        DocumentReference currentUserData = db.collection("users").document(mAuth.getCurrentUser().getUid());

        currentUserData.get().addOnSuccessListener(documentSnapshot -> {
            Map data = documentSnapshot.getData();
            if (data == null) return;
            ArrayList<DocumentReference> ownedDates = (ArrayList<DocumentReference>) data.get("owned_dates");
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
                                        (String) data1.get("campus") + ", Dom " + (String) data1.get("dorm"),
                                        (DocumentReference) ownedDate)
                                );
                            });
                        });
            }
        });

        myDatesList.setAdapter(adapter);
    }
}