package fun.triviamania;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;


public class MainMenu extends Activity {
    private StartTrivia actOK;
    private Spinner categories = null;
    final HashMap<String, String> catMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button button = (Button) findViewById(R.id.start);
        categories = (Spinner) findViewById(R.id.category);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.spinner_category);
        adapter.setDropDownViewResource(R.layout.dropdown_category);
        categories.setAdapter(adapter);
        catMap.put("General Knowledge", "9");
        catMap.put("Books", "10");
        catMap.put("Film", "11");
        catMap.put("Music", "12");
        catMap.put("Musicals & Theatres", "13");
        catMap.put("Television", "14");
        catMap.put("Video Games", "15");
        catMap.put("Board Games", "16");
        catMap.put("Science & Nature", "17");
        catMap.put("Computers", "18");
        catMap.put("Mathematics", "19");
        catMap.put("Mythology", "20");
        catMap.put("Sports", "21");
        catMap.put("Geography", "22");
        catMap.put("History", "23");
        catMap.put("Politics", "24");
        catMap.put("Art", "25");
        catMap.put("Celebrities", "26");
        catMap.put("Animals", "27");
        catMap.put("Vehicles", "28");
        catMap.put("Comics", "29");
        catMap.put("Gadgets", "30");
        catMap.put("Japanese Anime & Manga", "31");
        catMap.put("Cartoon & Animations", "32");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, StartTrivia.class);
                intent.putExtra("Category", categories.getSelectedItem().toString());
                intent.putExtra("Category_Map", catMap);
                intent.putExtra("Category_ID", categories.getSelectedItemPosition());
                startActivity(intent);
            }
        });


    }


}
