package si.uni_lj.fe.tnuv.dezurka;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DezurkaToolbar {
    static void setupToolbar(String tbText, AppCompatActivity view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (tbText == "") {
            toolbar.findViewById(R.id.back_btn).setVisibility(View.GONE);
        }
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(tbText);
    }
}
