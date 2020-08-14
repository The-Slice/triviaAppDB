package fun.triviamania;

import org.json.JSONException;
import org.json.JSONObject;

public interface AsyncResponse {
    void processFinishQuestions(JSONObject output) ;
    void processFinishInternet(Boolean output);
    void processFinishToken(String output);
}
