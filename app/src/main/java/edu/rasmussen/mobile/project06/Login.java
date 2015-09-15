package edu.rasmussen.mobile.project06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private static EditText username;
    private static EditText password;
    private static Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SubmitButton();
    }
    public void SubmitButton() {
        username = (EditText)findViewById(R.id.TFuserName);
        password = (EditText)findViewById(R.id.TFpassword);
        submitButton = (Button)findViewById(R.id.submitButton);

        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (username.getText().toString().equals("admin") &&
                                password.getText().toString().equals("password")) {
                            Toast.makeText(Login.this, "Username and Password are Correct!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("android.intent.action.MAINACTIVITY");
                            intent.putExtra("username", "admin");
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Username and Password are Incorrect!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
