package fun.triviamania;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchToken extends AsyncTask<Integer, Integer, String> {
    public AsyncResponse delegate = null;
    static OkHttpClient oKClient = new OkHttpClient();
    static Request triviaAccess;
    static Response okResponse;
    static String reString;
    static JSONObject token = new JSONObject();

    @Override
    protected String doInBackground(Integer... integers) {

        try {

            triviaAccess = new Request.Builder().url("https://opentdb.com/api_token.php?command=request").build();
            okResponse = oKClient.newCall(triviaAccess).execute();
            reString = okResponse.body().string();
            token = new JSONObject(reString);
            reString = token.get("token").toString();

        } catch (JSONException f) {
            Log.e("JSON", "Error Detected: JSON", f);
            return null;

        } catch (IOException e) {

            Log.d("IO", "Error Detected: IOException");
            return null;
        }

        return reString;
    }

    @Override
    protected void onPostExecute(String token) {
        delegate.processFinishToken(token);
    }
}

