package com.example.carpartsstore;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class FindPart extends Activity {

    private ArrayList<String> allProductKeys;
    private ArrayList<String> filteredDisplayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_part);

        EditText nameInput = findViewById(R.id.editTextName);
        RadioButton radioPerf = findViewById(R.id.radioPerformance);
        RadioButton radioVisual = findViewById(R.id.radioVisual);
        Button btnSearch = findViewById(R.id.btnDoSearch);
        ListView resultsList = findViewById(R.id.searchResultsList);

        allProductKeys = new ArrayList<>(Preferences.getAllProductKeys(this));
        filteredDisplayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredDisplayList);
        resultsList.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String searchText = nameInput.getText().toString().trim().toLowerCase();
            boolean filterPerf = radioPerf.isChecked();
            boolean filterVisual = radioVisual.isChecked();

            filteredDisplayList.clear();

            for (String key : allProductKeys) {
                Part p = Preferences.getProductDetails(this, key);
                if (p == null) continue;

                String category = p.getCategory(); // should be "Performance" or "Visual"
                String lowerName = p.getName().toLowerCase();

                boolean matchesName = searchText.isEmpty() || lowerName.contains(searchText);
                boolean matchesCategory =
                        (!filterPerf && !filterVisual) || // no filter
                                (filterPerf && category.equals("Performance")) ||
                                (filterVisual && category.equals("Visual"));

                if (matchesName && matchesCategory) {
                    int qty = Preferences.getQuantity(this, key, 0);
                    filteredDisplayList.add(p.getName() + " - $" + p.getPrice() + " (Qty: " + qty + ")");
                }
            }

            if (filteredDisplayList.isEmpty()) {
                filteredDisplayList.add("No matching parts");
            }

            adapter.notifyDataSetChanged();
        });

        resultsList.setOnItemClickListener((parent, view, pos, id) -> {
            String selected = filteredDisplayList.get(pos);
            if (selected.equals("No matching parts")) return;

            String key = selected.split(" - ")[0]; // product name
            int qty = Preferences.getQuantity(this, key, 0);

            if (qty > 0) {
                ArrayList<String> cart = Preferences.getCartList(this);
                cart.add(key);
                Preferences.saveCartList(this, cart);
                Preferences.saveQuantity(this, key, qty - 1);
                Toast.makeText(this, key + " added to cart", Toast.LENGTH_SHORT).show();
                btnSearch.performClick(); // refresh
            } else {
                Toast.makeText(this, "Out of stock", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
