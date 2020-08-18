package fun.triviamania;

import android.app.Activity;

import android.graphics.PorterDuff;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4;

public class StartTrivia extends Activity implements AsyncResponse {
    static String category;
    static int catID;
    static HashMap<String, String> catMap;
    static TestInternet test;
    static FetchQuestions fetch;
    static FetchToken token;
    static final int amountQ = 10;
    static int QuestionNumber = 0;
    static int score = 0;
    static String check = "false";
    static String storeToken;
    static JSONObject questions = new JSONObject();
    static QuestionObject q = new QuestionObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setCategory();
        setCategoryMap();
        StartTrivia actOK = new StartTrivia();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_start);
        final Button tButton = (Button) findViewById(R.id.tButton);
        final Button fButton = (Button) findViewById(R.id.fButton);
        final Button cButton = (Button) findViewById(R.id.cButton);
        final Button dButton = (Button) findViewById(R.id.dButton);
        final Button startOver = (Button) findViewById(R.id.startOver);
        final Button nButton = (Button) findViewById(R.id.nButton);
        tButton.getBackground().setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
        fButton.getBackground().setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
        cButton.getBackground().setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
        dButton.getBackground().setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
        Spinner categories = (Spinner) findViewById(R.id.category);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.spinner_category);
        adapter.setDropDownViewResource(R.layout.dropdown_category);
        categories.setAdapter(adapter);
        categories.setSelection(getIntent().getIntExtra("Category_ID", 0));
        startOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTrivia actOK = new StartTrivia();

                startGame(actOK);

                startOver.setVisibility(View.INVISIBLE);

            }
        });

        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = tButton.getText().toString();

                if (checkAnswer(check)) {

                    tButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.DARKEN);

                } else {

                    tButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.DARKEN);
                }

                setInvisible("t");


            }
        });
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = fButton.getText().toString();
                if (checkAnswer(check)) {

                    fButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.DARKEN);

                } else {

                    fButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.DARKEN);
                }

                setInvisible("f");


            }
        });


        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = cButton.getText().toString();
                if (checkAnswer(check)) {

                    cButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.DARKEN);

                } else {

                    cButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.DARKEN);
                }

                setInvisible("c");


            }
        });

        dButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = dButton.getText().toString();
                if (checkAnswer(check)) {

                    dButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.DARKEN);

                } else {

                    dButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.DARKEN);
                }

                setInvisible("d");

            }
        });

        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setQuestions();
                nButton.setVisibility(View.INVISIBLE);
                tButton.getBackground().setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
                fButton.getBackground().setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
                cButton.getBackground().setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
                dButton.getBackground().setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);


            }
        });

        startGame(actOK);
    }


    protected void displayQuestion(QuestionObject q) {
        TextView qBox = (TextView) findViewById(R.id.qBox);
        qBox.setVisibility(View.VISIBLE);
        qBox.setText(q.getQuestion());
        qBox.invalidate();
    }

    public static QuestionObject Parse(JSONObject Question) throws JSONException {

        QuestionObject qObject = new QuestionObject();

        qObject.setType(unescapeHtml4(Question.getString("type")).equals("boolean"));

        qObject.setQuestion(unescapeHtml4(Question.getString("question").trim()));

        qObject.setAnswers(Question.getJSONArray("incorrect_answers"));

        qObject.appendAnswer(unescapeHtml4(Question.getString("correct_answer")));

        qObject.setCorrectAnswer(unescapeHtml4(Question.getString("correct_answer")));

        qObject.shuffleAnswers();

        return qObject;

    }

    public StartTrivia startGame(final StartTrivia actOK) {
        q = null;
        QuestionNumber = 0;
        score = 0;
        Button tButton = (Button) findViewById(R.id.tButton);
        Spinner categories = (Spinner) findViewById(R.id.category);
        Button fButton = (Button) findViewById(R.id.fButton);
        Button cButton = (Button) findViewById(R.id.cButton);
        Button dButton = (Button) findViewById(R.id.dButton);
        TextView cBox = (TextView) findViewById(R.id.cBox);
        TextView qBox = (TextView) findViewById(R.id.qBox);
        categories.setVisibility(View.INVISIBLE);
        cBox.setVisibility(View.INVISIBLE);
        qBox.setVisibility(View.INVISIBLE);
        cButton.setVisibility(View.INVISIBLE);
        dButton.setVisibility(View.INVISIBLE);
        fButton.setVisibility(View.INVISIBLE);
        tButton.setVisibility(View.INVISIBLE);
        cBox.setText("Score: " + score);
        category = categories.getSelectedItem().toString();
        test = new TestInternet();
        test.delegate = this;
        test.execute();
        return actOK;
    }

    public void setQuestions() {

        try {

            q = Parse(questions.getJSONArray("results").getJSONObject(QuestionNumber));

        } catch (JSONException f) {

        }
        Button tButton = (Button) findViewById(R.id.tButton);
        Button fButton = (Button) findViewById(R.id.fButton);
        Button cButton = (Button) findViewById(R.id.cButton);
        Button dButton = (Button) findViewById(R.id.dButton);
        Button startOver = (Button) findViewById(R.id.startOver);
        TextView cBox = (TextView) findViewById(R.id.cBox);
        TextView qBox = (TextView) findViewById(R.id.qBox);
        Spinner categories = (Spinner) findViewById(R.id.category);
        cBox.setVisibility(View.VISIBLE);

        if (QuestionNumber == amountQ) {
            categories.setVisibility(View.VISIBLE);
            qBox.setVisibility(View.INVISIBLE);
            cButton.setVisibility(View.INVISIBLE);
            dButton.setVisibility(View.INVISIBLE);
            fButton.setVisibility(View.INVISIBLE);
            tButton.setVisibility(View.INVISIBLE);

            cBox.setText("Final Score: " + score);

            startOver.setVisibility(View.VISIBLE);

            fButton.setText("");
            tButton.setText("");
            cButton.setText("");
            dButton.setText("");

        } else {

            setAnswers(q);

            displayQuestion(q);

            QuestionNumber++;

        }

    }

    public void setAnswers(QuestionObject q) {

        Button tButton = (Button) findViewById(R.id.tButton);
        Button fButton = (Button) findViewById(R.id.fButton);
        Button cButton = (Button) findViewById(R.id.cButton);
        Button dButton = (Button) findViewById(R.id.dButton);
        TextView cBox = (TextView) findViewById(R.id.cBox);
        TextView qBox = (TextView) findViewById(R.id.qBox);

        tButton.setEnabled(true);
        fButton.setEnabled(true);
        cButton.setEnabled(true);
        dButton.setEnabled(true);


        if (q.isType() == false) {

            fButton.setVisibility(View.VISIBLE);
            tButton.setVisibility(View.VISIBLE);
            dButton.setVisibility(View.VISIBLE);
            cButton.setVisibility(View.VISIBLE);
            fButton.setText(q.getAnswers().get(0));
            tButton.setText(q.getAnswers().get(1));
            cButton.setText(q.getAnswers().get(2));
            dButton.setText(q.getAnswers().get(3));

        } else {

            fButton.setVisibility(View.VISIBLE);
            tButton.setVisibility(View.VISIBLE);
            cButton.setVisibility(View.INVISIBLE);
            dButton.setVisibility(View.INVISIBLE);
            tButton.setText("True");
            fButton.setText("False");

        }

    }

    public boolean checkAnswer(String check) {

        TextView cBox = (TextView) findViewById(R.id.cBox);

        if (q.getCorrectAnswer().equals(check)) {

            scoreAdd();
            cBox.setText("Score: " + score);

            return true;

        }

        return false;
    }

    public void setInvisible(String select) {
        Button tButton = (Button) findViewById(R.id.tButton);
        Button fButton = (Button) findViewById(R.id.fButton);
        Button cButton = (Button) findViewById(R.id.cButton);
        Button dButton = (Button) findViewById(R.id.dButton);
        Button nButton = (Button) findViewById(R.id.nButton);

        tButton.setEnabled(false);
        fButton.setEnabled(false);
        cButton.setEnabled(false);
        dButton.setEnabled(false);


        if (select == "t") {

            cButton.setVisibility(View.INVISIBLE);
            dButton.setVisibility(View.INVISIBLE);
            fButton.setVisibility(View.INVISIBLE);
            nButton.setVisibility(View.VISIBLE);

        } else if (select == "f") {

            cButton.setVisibility(View.INVISIBLE);
            dButton.setVisibility(View.INVISIBLE);
            tButton.setVisibility(View.INVISIBLE);
            nButton.setVisibility(View.VISIBLE);

        } else if (select == "c") {

            dButton.setVisibility(View.INVISIBLE);
            fButton.setVisibility(View.INVISIBLE);
            tButton.setVisibility(View.INVISIBLE);
            nButton.setVisibility(View.VISIBLE);

        } else if (select == "d") {

            cButton.setVisibility(View.INVISIBLE);
            fButton.setVisibility(View.INVISIBLE);
            tButton.setVisibility(View.INVISIBLE);
            nButton.setVisibility(View.VISIBLE);

        }


        if (q.getCorrectAnswer().equals(tButton.getText())) {


            tButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.DARKEN);
            tButton.setVisibility(View.VISIBLE);

        } else if (q.getCorrectAnswer().equals(fButton.getText())) {


            fButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.DARKEN);
            fButton.setVisibility(View.VISIBLE);


        } else if (q.getCorrectAnswer().equals(cButton.getText())) {


            cButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.DARKEN);
            cButton.setVisibility(View.VISIBLE);

        } else if (q.getCorrectAnswer().equals(dButton.getText())) {


            dButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.DARKEN);
            dButton.setVisibility(View.VISIBLE);

        }

    }

    public static void scoreAdd() {
        score++;
    }

    public static int getScore() {
        return score;
    }

    public void setCategory() {
        category = getIntent().getStringExtra("Category");
    }

    public static String getCategory() {
        return category;
    }

    public void setCategoryMap() {
        catMap = (HashMap<String, String>) getIntent().getSerializableExtra("Category_Map");
    }

    public static HashMap<String, String> getCategoryMap() {
        return catMap;
    }


    @Override
    public void processFinishInternet(@NonNull Boolean output) {
        Button startOver = (Button) findViewById(R.id.startOver);
        if (output) {
            if (storeToken != null) {
                fetch = new FetchQuestions();
                fetch.delegate = this;
                fetch.execute(storeToken);
            } else {
                startOver.setText("Start Over");
                token = new FetchToken();
                token.delegate = this;
                token.execute();
            }
        } else {
            TextView qBox = (TextView) findViewById(R.id.qBox);
            qBox.setText("Check your internet connection!");
            qBox.setVisibility(View.VISIBLE);
            startOver.setVisibility(View.VISIBLE);
            startOver.setText("Retry");
        }
    }

    @Override
    public void processFinishToken(String output) {
        storeToken = output;
        fetch = new FetchQuestions();
        fetch.delegate = this;
        fetch.execute(storeToken);
    }

    @Override
    public void processFinishQuestions(JSONObject output) {
        try {
            if (output.get("response_code").equals(3) || output.get("response_code").equals(4)) {
                token = new FetchToken();
                token.delegate = this;
                token.execute();
            } else {
                questions = output;
                setQuestions();
            }
        } catch (JSONException e) {
            Log.e("JSON", "Error Detected: JSON", e);
        }
    }


}
