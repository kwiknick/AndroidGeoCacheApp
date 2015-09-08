package edu.rasmussen.mobile.project06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class GameBoard extends AppCompatActivity {

    TextView treasure1, treasure2, treasure3, treasure4;
    List<Item> items;

    public void displayItem(View view, Item selectedItem) {
        Intent intent = new Intent(this, DisplaySelItem.class);
        intent.putExtra("selectedItem", selectedItem);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GPSListener gps = new GPSListener(GameBoard.this);
        String latitudeS= "0";
        String  longitudeS="0";

        // Check if GPS enabled
        if(gps.canGetLocation()) {

            latitudeS = String.valueOf(gps.getLatitude());
             longitudeS = String.valueOf(gps.getLongitude());

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitudeS + "\nLong: " + longitudeS, Toast.LENGTH_LONG).show();

             items =  findNearbylocations(latitudeS, longitudeS);



            //Calculate each items distance
               calculateDistance(latitudeS, longitudeS);

             for(int j=0; j<items.size(); j++){
                   if(items.get(j).getDistance()>50){
                         items.remove(j);
                   }
             }

            setContentView(R.layout.activity_game_board);
             for (int i=0; i<items.size(); i++){
                   int count = i + 1;
                 TextView tv=null;
                 if(count==1) {
                    tv = (TextView) findViewById(R.id.display_treasure1);
                 }else if (count==2){
                     tv = (TextView) findViewById(R.id.display_treasure2);

             }else if (count==3){
                      tv = (TextView) findViewById(R.id.display_treasure3);
                 }else if (count==4){
                 tv = (TextView) findViewById(R.id.display_treasure4);
                 }

                 tv.setText(items.get(i).getDesc());
             }




        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }
        treasure1 = (TextView) findViewById(R.id.display_treasure1);
        treasure2 = (TextView) findViewById(R.id.display_treasure2);
        treasure3 = (TextView) findViewById(R.id.display_treasure3);
        treasure4 = (TextView) findViewById(R.id.display_treasure4);
        treasure1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item selectedItem = null;
                selectedItem = items.get(0);
                displayItem(v,selectedItem);

            }
        });

        treasure2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item selectedItem = null;
                selectedItem = items.get(1);
                displayItem(v,selectedItem);

            }
        });

        treasure3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item selectedItem = null;
                selectedItem = items.get(2);
                displayItem(v, selectedItem);

        }
        });

        treasure4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item selectedItem = null;
                selectedItem = items.get(3);
                displayItem(v, selectedItem);

            }
        });

    }

     private void  calculateDistance(String latitude, String longitude){


             for(int i=0; i<items.size(); i++){

                     double lat1 = Double.valueOf(latitude)/1E6;
                     double lat2 = Double.valueOf(longitude)/1E6;
                     double lon1 = Double.valueOf(items.get(i).getLat())/1E6;
                     double lon2 = Double.valueOf(items.get(i).getLon())/1E6;
                     double dLat = Math.toRadians(lat2-lat1);
                     double dLon = Math.toRadians(lon2-lon1);
                     double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                             Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                                     Math.sin(dLon/2) * Math.sin(dLon/2);
                     double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    items.get(i).setDistance( 3959 * c);

             }
     }

     private List<Item> findNearbylocations( String latitude, String longitude){

             String text = readFile();
         List itemList = new ArrayList<Item>();
         List contentList = new ArrayList<String>();
         String content = null;
         try {

             XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
             factory.setNamespaceAware(true);
             XmlPullParser xpp = factory.newPullParser();
             xpp.setInput(new StringReader(text));
             int eventType = xpp.getEventType();
             Item item = new Item();


             while (eventType != XmlPullParser.END_DOCUMENT) {
                 if (eventType == XmlPullParser.START_DOCUMENT) {
                     System.out.println("Start document");
                 } else if (eventType == XmlPullParser.START_TAG) {
                     if (xpp.getName().equalsIgnoreCase("Description")) {
                         item.setDesc(xpp.getAttributeValue(null, "desc"));
                     } else if (xpp.getName().equalsIgnoreCase("name")) {
                         item.setName(xpp.getAttributeValue(null, "name"));

                 } else if (xpp.getName().equalsIgnoreCase("clue1")) {
                     item.setClue1(xpp.getAttributeValue(null, "clue1"));
                 }else if (xpp.getName().equalsIgnoreCase("clue2")) {
                         item.setClue2(xpp.getAttributeValue(null, "clue2"));

                 }else if (xpp.getName().equalsIgnoreCase("clue3")) {
                     item.setClue3(xpp.getAttributeValue(null, "clue3"));
                 }else if (xpp.getName().equalsIgnoreCase("points")) {
                         item.setPrice(xpp.getAttributeValue(null, "points"));
                     }
//                 } else if (xpp.getName().equalsIgnoreCase("latitude")) {
//                         item.setLat(xpp.getAttributeValue(null, "latitude"));
                     //}
                     //}
                     else if (xpp.getName().equalsIgnoreCase("longitude")) {
                         item.setLon(xpp.getAttributeValue(null, "longitude"));
                         itemList.add(item);
                         item = new Item();

                     }
                 }
                 eventType = xpp.next();

             }

             System.out.println("End document");
         } catch (Exception e) {
             System.out.println( e);
         }
         return itemList;
     }

     public String readFile(){

         //Read text from file
         StringBuilder text = new StringBuilder();

         try {
             BufferedReader br = new BufferedReader(new FileReader("/data/data/edu.rasmussen.mobile.project06/files/example1.xml"));

             String line = null;

             while ((line = br.readLine()) != null) {
                 text.append(line);
             }
             br.close();
         } catch (Exception ioe) {
             Log.e("error", "" + ioe.getMessage());
             Toast.makeText(getBaseContext(), "File Not Found", Toast.LENGTH_SHORT).show();
         }

         return text.toString();
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_board, menu);
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

    public void printToast() {
        Toast.makeText(getBaseContext(), "Text View Displayed", Toast.LENGTH_SHORT).show();
    }
}
