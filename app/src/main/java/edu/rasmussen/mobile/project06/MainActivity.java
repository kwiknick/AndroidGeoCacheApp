package edu.rasmussen.mobile.project06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity {

    public void newLocation(View view) {
        Intent intent = new Intent(this, SubmitActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //readFile();

        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader("/data/data/edu.rasmussen.mobile.project06/files/example.xml"));

            String line = null;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        } catch (Exception ioe) {
            Log.e("error", "" + ioe.getMessage());
            Toast.makeText(getBaseContext(), "File Not Found", Toast.LENGTH_SHORT).show();
        }
        String content = null;
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(text.toString()));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    System.out.println("Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("Description")) {
                        if(content==null) {
                            content = "Description: " + xpp.getAttributeValue(null, "desc");
                        }
                        else {
                            content = content + "\n**************\nDescription: " + xpp.getAttributeValue(null, "desc");
                        }
                    } else if (xpp.getName().equalsIgnoreCase("street")) {
                        content = content + "\nStreet: " + xpp.getAttributeValue(null, "street");
                    } else if (xpp.getName().equalsIgnoreCase("city")) {
                        content = content + "\nCity: " + xpp.getAttributeValue(null, "city");
                    } else if (xpp.getName().equalsIgnoreCase("state")) {
                        content = content + "\nState: " + xpp.getAttributeValue(null, "state");
                    } else if (xpp.getName().equalsIgnoreCase("zip")) {
                        content = content + "\nZip: " + xpp.getAttributeValue(null, "zip");
                    } else if (xpp.getName().equalsIgnoreCase("latitude")) {
                        content = content + "\nLatitude: " + xpp.getAttributeValue(null, "latitude");
                    } else if (xpp.getName().equalsIgnoreCase("longitude")) {
                        content = content + "\nLongitude: " + xpp.getAttributeValue(null, "longitude");
                    }

                }
                eventType = xpp.next();
            }
            System.out.println("End document");
        } catch (Exception e) {
            Log.e("error", "" + e.getMessage());
        }
        TextView tv = (TextView) findViewById(R.id.display_file);
        if (content != null) {
            tv.setText(content);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
