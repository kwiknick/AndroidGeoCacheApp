package edu.rasmussen.mobile.project06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SubmitActivity extends AppCompatActivity {

    Button submit;
    EditText description, street, city, state, zip;
    String descriptionS, streetS, cityS, stateS, zipS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        submit = (Button) findViewById(R.id.submitButton);
        description = (EditText) findViewById(R.id.inputDescBox);
        street = (EditText) findViewById(R.id.inputAddressBox);
        city = (EditText) findViewById(R.id.inputCityBox);
        state = (EditText) findViewById(R.id.inputStateBox);
        zip = (EditText) findViewById(R.id.inputZipCodeBox);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionS = description.getText().toString();
                streetS = street.getText().toString();
                cityS = city.getText().toString();
                stateS = state.getText().toString();
                zipS = zip.getText().toString();

                try {
                    FileOutputStream myFile = openFileOutput("locations.xml", SubmitActivity.MODE_APPEND);

                    // create a new XmlSerializer object
                    XmlSerializer serializer = Xml.newSerializer();

                    try {

                        // use myFile as your xml serializer and set to UTF-8 encoding
                        serializer.setOutput(myFile, "UTF-8");

                        // Write <?xml declaration with encoding
                        serializer.startDocument(null, Boolean.valueOf(true));

                        // set indentation option
                        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

                        // start a new top level tag and other tags
                        serializer.startTag(null, "items");
                        serializer.startTag(null, "treasure");

                        serializer.startTag(null, "description");
                        serializer.attribute(null, "desc", descriptionS);
                        serializer.endTag(null, "description");

                        serializer.startTag(null, "street");
                        serializer.attribute(null, "street", streetS);
                        serializer.endTag(null, "street");

                        serializer.startTag(null, "city");
                        serializer.attribute(null, "city", cityS);
                        serializer.endTag(null, "city");

                        serializer.startTag(null, "state");
                        serializer.attribute(null, "state", stateS);
                        serializer.endTag(null, "state");

                        serializer.startTag(null, "zip");
                        serializer.attribute(null, "zip", zipS);
                        serializer.endTag(null, "zip");

                        serializer.endTag(null, "treasure");
                        serializer.endTag(null, "items");

                        serializer.endDocument();

                        // perform the write by flushing
                        serializer.flush();

                        // close the file stream
                        myFile.close();

                        Toast.makeText(getBaseContext(), "Location Saved Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent("android.intent.action.MAINACTIVITY");
                        startActivity(intent);

                    } catch (Exception e) {
                        Log.e("error", "" + e.getMessage());
                    }
                } catch (FileNotFoundException e) {
                    Log.e("error", "" + e.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
