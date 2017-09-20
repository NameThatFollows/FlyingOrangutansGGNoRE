package edu.gatech.cs2340.rational.rational;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void verifyLogin(View view) {
        String username = ((EditText) findViewById((R.id.editTextUsername))).getText().toString();
        String password = ((EditText) findViewById((R.id.editTextPassword))).getText().toString();
        if (username.equals("user") && password.equals("pass")) {
            Intent intent = new Intent(this, Dashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Log.d("Tag", "YOU DUN MESSED UP AY AY RON");
        }
    }

    public void cancelLogin(View view) {
        finish();
    }
}