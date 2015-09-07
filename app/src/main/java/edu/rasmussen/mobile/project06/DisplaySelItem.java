package edu.rasmussen.mobile.project06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplaySelItem extends AppCompatActivity {

    Button getClueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sel_item);

        getClueButton = (Button) findViewById(R.id.getCluesButton);

        getClueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClues();
            }
        });

    }

    public void getClues(){

         Item selectedItem = (Item) getIntent().getSerializableExtra("selectedItem");
        TextView tv = (TextView) findViewById(R.id.inputAnswer2Box);
          if(tv.getText().equals(selectedItem.getName())){
              Toast.makeText(getApplicationContext(), "You Guessed it you get " + selectedItem.getPrice() + " points", Toast.LENGTH_LONG).show();
          }else{
               selectedItem.setGuess(selectedItem.getGuess() + 1);

               if(selectedItem.getGuess()==1){
                   Toast.makeText(getApplicationContext(), "Clue: " + selectedItem.getClue1() , Toast.LENGTH_LONG).show();
                   selectedItem.setPrice("50");
               }else if (selectedItem.getGuess()==2){
                   Toast.makeText(getApplicationContext(), "Clue: " + selectedItem.getClue2() , Toast.LENGTH_LONG).show();
                   selectedItem.setPrice("2");

               }

          }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_sel_item, menu);
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
