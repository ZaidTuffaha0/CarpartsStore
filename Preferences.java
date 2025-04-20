package com.example.carpartsstore;

import android.content.Context;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Preferences {

    public static void saveQuantity(Context context, String key, int quantity) {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt("qty_" + key, quantity).apply();
    }

    public static int getQuantity(Context context, String key, int defaultVal) {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt("qty_" + key, defaultVal);
    }

    public static void saveProduct(Context context, String key) {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> set = prefs.getStringSet("products", new HashSet<>());
        set = new HashSet<>(set); // make mutable
        set.add(key);
        prefs.edit().putStringSet("products", set).apply();
    }

    public static Set<String> getAllProductKeys(Context context) {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getStringSet("products", new HashSet<>());
    }

    public static void saveProductDetails(Context context, Part product) {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = new Gson().toJson(product);
        prefs.edit().putString("product_" + product.getName(), json).apply();
    }

    public static Part getProductDetails(Context context, String name) {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString("product_" + name, null);
        if (json == null) return null;
        return new Gson().fromJson(json, Part.class);
    }

    public static void saveCartList(Context context, ArrayList<String> cartList) {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = new Gson().toJson(cartList);
        prefs.edit().putString("cart_list", json).apply();
    }

    public static ArrayList<String> getCartList(Context context) {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString("cart_list", "[]");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    public static void clearCart(Context context) {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().remove("cart_list").apply();
    }

    public static void removeProduct(Context context, String name) {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);


        Set<String> products = prefs.getStringSet("products", new HashSet<>());
        products = new HashSet<>(products); // make mutable
        products.remove(name);
        prefs.edit().putStringSet("products", products).apply();

        prefs.edit()
                .remove("product_" + name)
                .remove("qty_" + name)
                .apply();
    }
}
