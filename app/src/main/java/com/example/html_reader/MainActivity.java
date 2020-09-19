package com.example.html_reader;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    TextView codeShow1;
    ImageButton imageButton;
    Dialog dialog;

    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText link1 = (EditText) findViewById(R.id.link);           //vvod ssyslki
         codeShow1 = (TextView) findViewById(R.id.codeShow);   // vivod coda

        Button doit1=(Button)findViewById(R.id.doit);
        Button doitclr1=(Button)findViewById(R.id.doitclr);

        imageButton=(ImageButton)findViewById(R.id.imageButton);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        doit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(ProgressBar.VISIBLE);
                final String sLink = String.valueOf(link1.getText());

                if (sLink.equals("")) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Пусто", Toast.LENGTH_SHORT);
                    toast.show();
                    progressBar.setVisibility(ProgressBar.INVISIBLE);

                } else {

                    Ion.with(getApplicationContext()).load(sLink).asString().setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            codeShow1.setText(result);
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }
                    });
                }
            }
        });

      doitclr1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              codeShow1.setText("");
              link1.setText("");

              Toast toast = Toast.makeText(getApplicationContext(), "Очищено", Toast.LENGTH_SHORT);
              toast.show();

          }
      });
      imageButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              showDialog();
          }
      });
        loadText();
    }
    void showDialog  () {
        dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Заголовок диалога");
        dialog.setContentView(R.layout.dialog);
        dialog.show();
    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT,codeShow1.getText().toString());
        ed.commit();
    }
    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        codeShow1.setText(savedText);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }

}
