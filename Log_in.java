package com.example.carpartsstore;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Log_in extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        EditText username = findViewById(R.id.editUsername);
        EditText password = findViewById(R.id.editPassword);
        Button loginBtn = findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(v -> {
            if (username.getText().toString().trim().equals("Zaid") &&
                    password.getText().toString().trim().equals("123")) {
                startActivity(new Intent(Log_in.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
