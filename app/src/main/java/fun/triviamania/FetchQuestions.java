package fun.triviamania;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static fun.triviamania.StartTrivia.Parse;

public class FetchQuestions extends AsyncTask<Integer, Integer, JSONObject> {
    WeakReference<Activity> StartTrivia;
    public AsyncResponse delegate = null;
    static OkHttpClient oKClient = new OkHttpClient();
    static Request triviaAccess;
    static Response okResponse;
    static String reString;
    static JSONObject questions = new JSONObject();

    public FetchQuestions(Activity activity) {
        StartTrivia = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        Activity activity = StartTrivia.get();
        if (activity != null) {
            TextView qBox = (TextView) activity.findViewById(R.id.qBox);
            TextView cBox = (TextView) activity.findViewById(R.id.cBox);
            qBox.setVisibility(View.INVISIBLE);
            cBox.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected JSONObject doInBackground(Integer... params) {

        int amountQ = params[0];

        try {
            triviaAccess = new Request.Builder().url("https://opentdb.com/api.php?amount=" + amountQ).build();
            okResponse = oKClient.newCall(triviaAccess).execute();
            reString = okResponse.body().string();

            questions = new JSONObject(reString);

        } catch (JSONException f) {
            Log.e("JSON", "Error Detected: JSON", f);
            return null;

        } catch (IOException e) {

            Log.d("IO", "Error Detected: IOException");
            return null;
        }

        return questions;

    }

    @Override
    protected void onPostExecute(JSONObject questions) {
        delegate.processFinish(questions);
    }


}


