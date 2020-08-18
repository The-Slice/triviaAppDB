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
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static fun.triviamania.StartTrivia.Parse;

public class FetchQuestions extends AsyncTask<String, Integer, JSONObject> {
    public AsyncResponse delegate = null;
    static OkHttpClient oKClient = new OkHttpClient();
    static Request triviaAccess;
    static Response okResponse;
    static String reString;
    static JSONObject questions;
    static String category;
    static HashMap<String, String> catMap;


    @Override
    protected void onPreExecute() {
        category = StartTrivia.getCategory();
        catMap = StartTrivia.getCategoryMap();
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        try {

            if (!category.equals("Random")) {
                triviaAccess = new Request.Builder().url("https://opentdb.com/api.php?amount=10&category=" + catMap.get(category) + "&token=" + params[0]).build();
            } else {
                triviaAccess = new Request.Builder().url("https://opentdb.com/api.php?amount=10&token=" + params[0]).build();
            }

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

        delegate.processFinishQuestions(questions);
    }


}


