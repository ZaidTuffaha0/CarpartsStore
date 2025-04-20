package com.example.carpartsstore;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class basket extends Activity {
    ListView cartListView;
    TextView totalPriceText;
    ArrayList<String> rawCart;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket);

        cartListView = findViewById(R.id.cartListView);
        totalPriceText = findViewById(R.id.totalPriceText);
        Button btnCheckout = findViewById(R.id.btnCheckout);

        rawCart = Preferences.getCartList(this);
        updateList();

        cartListView.setOnItemClickListener((parent, view, position, id) -> {
            rawCart.remove(position);
            Preferences.saveCartList(this, rawCart);
            updateList();
        });

        btnCheckout.setOnClickListener(v -> {
            Preferences.clearCart(this);
            Toast.makeText(this, "Order Placed!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void updateList() {
        ArrayList<String> display = new ArrayList<>();
        int total = 0;
        for (String name : rawCart) {
            Part p = Preferences.getProductDetails(this, name);
            if (p != null) {
                display.add(name + " - $" + p.getPrice());
                total += p.getPrice();
            } else {
                display.add(name + " - $?");
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, display);
        cartListView.setAdapter(adapter);
        totalPriceText.setText("Total: $" + total);
    }
}