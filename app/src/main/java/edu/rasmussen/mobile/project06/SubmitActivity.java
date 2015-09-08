package edu.rasmussen.mobile.project06;

import android.app.Activity;
import android.content.Context;
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

import java.io.File;
import java.io.FileOutputStream;

public class SubmitActivity extends AppCompatActivity {

    Button submit;
    EditText description, street, city, state, zip, clue1, clue2, clue3, name;
    String descriptionS, streetS, cityS, stateS, zipS, clue1S, clue2s, clue3s, nameS;
    FileOutputStream myFile;
    XmlSerializer serializer;

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
        clue1 = (EditText) findViewById(R.id.inputClue1Box);
        clue2 = (EditText) findViewById(R.id.inputClue2Box);
        clue3 = (EditText) findViewById(R.id.inputClue3Box);
        name = (EditText) findViewById(R.id.inputNameBox);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionS = description.getText().toString();
                streetS = street.getText().toString();
                cityS = city.getText().toString();
                stateS = state.getText().toString();
                zipS = zip.getText().toString();
                clue1S = clue1.getText().toString();
                clue2s = clue2.getText().toString();
                clue3s = clue3.getText().toString();
                nameS = name.getText().toString();
                Context gpsContext = getApplicationContext();
                String gpsMessage = "GPS in Progress, Please Wait.";
                int tDuration;
                tDuration = Toast.LENGTH_SHORT;
                String latitudeS = "0";
                String longitudeS = "0";

                GPSListener gps = new GPSListener(SubmitActivity.this);

                // Check if GPS enabled
                if (gps.canGetLocation()) {

                    latitudeS = String.valueOf(gps.getLatitude());
                    longitudeS = String.valueOf(gps.getLongitude());

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitudeS + "\nLong: " + longitudeS, Toast.LENGTH_LONG).show();
                } else {
                    // Can't get location.
                    // GPS or network is not enabled.
                    // Ask user to enable GPS/network in settings.
                    gps.showSettingsAlert();
                }
                // create the initial xml document if it already does not exist
                try {
                    File filename = new File(getFilesDir(), "locations.xml");

                    if (!filename.exists()) {
                        myFile = openFileOutput("locations.xml", Activity.MODE_APPEND);

                        // create a new XmlSerializer object
                        serializer = Xml.newSerializer();

                        // use myFile as your xml serializer and set to UTF-8 encoding
                        serializer.setOutput(myFile, "UTF-8");

                        // Write <?xml declaration with encoding
                        serializer.startDocument(null, Boolean.valueOf(true));

                        // set indentation option
                        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

                        writeItem(latitudeS, longitudeS);
                    } else {
                        // use myFile as your xml serializer and set to UTF-8 encoding
                        myFile = openFileOutput("locations.xml", Activity.MODE_APPEND);

                        // create a new XmlSerializer object
                        serializer = Xml.newSerializer();

                        // use myFile as your xml serializer and set to UTF-8 encoding
                        serializer.setOutput(myFile, "UTF-8");
                        writeItem(latitudeS, longitudeS);
                    }

                    //serializer.endDocument();

                    // perform the write by flushing
                    serializer.flush();

                    // close the file stream
                    myFile.close();

                    Toast.makeText(getBaseContext(), "Location Saved Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent("android.intent.action.MAINACTIVITY");
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Exception: ", e.getMessage());
                }


            }


        });
    }

    public void writeItem(String latitudeS, String longitudeS) {


        // Write <?xml declaration with encoding

        try {
            // set indentation option
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            // start a new top level tag and other tags
            serializer.startTag(null, "items");
            serializer.startTag(null, "treasure");

            serializer.startTag(null, "name");
            serializer.attribute(null, "name", nameS);
            serializer.endTag(null, "name");

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

            serializer.startTag(null, "latitude");
            serializer.attribute(null, "latitude", latitudeS);
            serializer.endTag(null, "latitude");

            serializer.startTag(null, "longitude");
            serializer.attribute(null, "longitude", longitudeS);
            serializer.endTag(null, "longitude");

            serializer.startTag(null, "clue1");
            serializer.attribute(null, "clue1", clue1S);
            serializer.endTag(null, "clue1");


            serializer.startTag(null, "clue2");
            serializer.attribute(null, "clue2", clue2s);
            serializer.endTag(null, "clue2");


            serializer.startTag(null, "clue3");
            serializer.attribute(null, "clue3", clue3s);
            serializer.endTag(null, "clue3");

            serializer.startTag(null, "points");
            serializer.attribute(null, "points", "100");
            serializer.endTag(null, "points");

            serializer.endTag(null, "treasure");
            serializer.endTag(null, "items");
        } catch (Exception e) {
            Log.e("Exception: ", e.getMessage());
        }

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
