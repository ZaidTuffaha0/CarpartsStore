package com.example.carpartsstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends Activity {
    private ArrayList<String> actualProductKeys;
    private ArrayList<String> productDisplayNames;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = findViewById(R.id.carListView);
        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnCart = findViewById(R.id.btnCart);
        Button btnAdd = findViewById(R.id.btnAddNewProduct);

        actualProductKeys = new ArrayList<>();
        productDisplayNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productDisplayNames);
        list.setAdapter(adapter);

        // Add to cart on tap
        list.setOnItemClickListener((parent, view, pos, id) -> {
            String key = actualProductKeys.get(pos);
            int qty = Preferences.getQuantity(this, key, 0);
            if (qty > 0) {
                ArrayList<String> cart = Preferences.getCartList(this);
                cart.add(key);
                Preferences.saveCartList(this, cart);
                Preferences.saveQuantity(this, key, qty - 1);
                loadProducts();
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Out of stock", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete product on long press
        list.setOnItemLongClickListener((parent, view, pos, id) -> {
            String key = actualProductKeys.get(pos);
            Preferences.removeProduct(this, key);
            loadProducts();
            Toast.makeText(this, "Deleted: " + key, Toast.LENGTH_SHORT).show();
            return true;
        });

        btnSearch.setOnClickListener(v -> startActivity(new Intent(this, FindPart.class)));
        btnCart.setOnClickListener(v -> startActivity(new Intent(this, basket.class)));
        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, AddPart.class)));

        loadProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    private void loadProducts() {
        Set<String> keys = Preferences.getAllProductKeys(this);
        actualProductKeys.clear();
        productDisplayNames.clear();
        for (String k : keys) {
            int qty = Preferences.getQuantity(this, k, 0);
            actualProductKeys.add(k);
            productDisplayNames.add(k + " (Qty: " + qty + ")");
        }
        adapter.notifyDataSetChanged();
    }
}
