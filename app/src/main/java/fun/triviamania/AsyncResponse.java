package fun.triviamania;

import org.json.JSONObject;

public interface AsyncResponse {
    void processFinish(JSONObject output);
    void processFinish(Boolean output);
}
