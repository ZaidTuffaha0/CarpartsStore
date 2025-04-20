package com.example.carpartsstore;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

public class AddPart extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_part);

        EditText nameInput = findViewById(R.id.editProductName);
        EditText priceInput = findViewById(R.id.editProductPrice);
        EditText quantityInput = findViewById(R.id.editProductQuantity);
        RadioButton radioPerformance = findViewById(R.id.radioPerformance);
        RadioButton radioVisual = findViewById(R.id.radioVisual);
        Switch switchCertified = findViewById(R.id.switchCertified);
        Button saveBtn = findViewById(R.id.btnSaveProduct);

        saveBtn.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String priceStr = priceInput.getText().toString().trim();
            String quantityStr = quantityInput.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int price = Integer.parseInt(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            boolean certified = switchCertified.isChecked();
            String category = radioPerformance.isChecked() ? "Performance" : "Visual";

            // Save product
            Part product = new Part(name, category, price, certified);
            Preferences.saveProduct(this, name);
            Preferences.saveQuantity(this, name, quantity);
            Preferences.saveProductDetails(this, product);

            Toast.makeText(this, "Product saved!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
