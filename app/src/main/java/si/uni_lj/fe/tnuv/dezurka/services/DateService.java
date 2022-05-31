package si.uni_lj.fe.tnuv.dezurka.services;

import android.os.AsyncTask;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Map;

import si.uni_lj.fe.tnuv.dezurka.MyDatesActivity;

public class DateService extends AsyncTask<String, Integer, Map> {
    private WeakReference<MyDatesActivity> activityWeakReference;

    DateService(MyDatesActivity activity) {
        activityWeakReference = new WeakReference<MyDatesActivity>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        MyDatesActivity activity = activityWeakReference.get();
        if (activity == null || activity.isFinishing()) return;

        activity.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Map doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Map map) {
        super.onPostExecute(map);

        MyDatesActivity activity = activityWeakReference.get();
        if (activity == null || activity.isFinishing()) return;

        activity.progressBar.setVisibility(View.INVISIBLE);
    }
}
