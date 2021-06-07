package com.niwarthana.smartchef.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.niwarthana.smartchef.model.Food;
import com.niwarthana.smartchef.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    public static String DB_NAME = "smart_chef";

    private static DBHelper instance;


    private static final String CREATE_TABLE_FOOD = " CREATE TABLE IF NOT EXISTS "
            + Constants.TABLE_FOOD + "("
            + Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Constants.KEY_NAME + " TEXT,"
            + Constants.KEY_WEIGHT + " REAL,"
            + Constants.KEY_PRICE + " REAL,"
            + Constants.KEY_DESCRIPTION + " TEXT,"
            + Constants.KEY_AVAILABILITY + " INTEGER DEFAULT 0"
            + ")";

    public static synchronized DBHelper getInstance(Context c) {

        if (instance != null) {

        } else {
            instance = new DBHelper(c);

        }

        return instance;
    }

    public DBHelper(Context c) {
        super(c, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "DB Created 1");
        db.execSQL(CREATE_TABLE_FOOD);
        Log.e(TAG, "DB Created 2");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_NAME, food.getName());
        values.put(Constants.KEY_WEIGHT, food.getWeight());
        values.put(Constants.KEY_PRICE, food.getPrice());
        values.put(Constants.KEY_DESCRIPTION, food.getDescription());

        // insert row
        long id = db.insert(Constants.TABLE_FOOD, null, values);

        return id > 0;
    }

    public List<Food> getAllFoods() {
        List<Food> foods = new ArrayList<Food>();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_FOOD + " ORDER BY " + Constants.KEY_NAME + " ASC";

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(c.getInt((c.getColumnIndex(Constants.KEY_ID))));
                food.setName((c.getString(c.getColumnIndex(Constants.KEY_NAME))));
                food.setWeight(c.getDouble(c.getColumnIndex(Constants.KEY_WEIGHT)));
                food.setPrice(c.getDouble(c.getColumnIndex(Constants.KEY_PRICE)));
                food.setDescription(c.getString(c.getColumnIndex(Constants.KEY_DESCRIPTION)));
                food.setAvailability(c.getInt(c.getColumnIndex(Constants.KEY_AVAILABILITY)));

                foods.add(food);
            } while (c.moveToNext());
        }

        return foods;
    }

    public Food getFood(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_FOOD + " WHERE " + Constants.KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Food food = new Food();
        food.setId(c.getInt((c.getColumnIndex(Constants.KEY_ID))));
        food.setName((c.getString(c.getColumnIndex(Constants.KEY_NAME))));
        food.setWeight(c.getDouble(c.getColumnIndex(Constants.KEY_WEIGHT)));
        food.setPrice(c.getDouble(c.getColumnIndex(Constants.KEY_PRICE)));
        food.setDescription(c.getString(c.getColumnIndex(Constants.KEY_DESCRIPTION)));
        food.setAvailability(c.getInt(c.getColumnIndex(Constants.KEY_AVAILABILITY)));
        return food;
    }

    public List<Food> searchProduct(String text) {
        List<Food> foods = new ArrayList<Food>();
        String[] args = new String[2];
        args[0] = "%" + text + "%";
        args[1] = "%" + text + "%";
        String whereClause = "(" + Constants.KEY_NAME + " LIKE ? OR " + Constants.KEY_DESCRIPTION + " LIKE ?)";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  c = db.query(Constants.TABLE_FOOD, null, whereClause, args, null, null, Constants.KEY_NAME + " ASC ");

        if (c.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(c.getInt((c.getColumnIndex(Constants.KEY_ID))));
                food.setName((c.getString(c.getColumnIndex(Constants.KEY_NAME))));
                food.setWeight(c.getDouble(c.getColumnIndex(Constants.KEY_WEIGHT)));
                food.setPrice(c.getDouble(c.getColumnIndex(Constants.KEY_PRICE)));
                food.setDescription(c.getString(c.getColumnIndex(Constants.KEY_DESCRIPTION)));
                food.setAvailability(c.getInt(c.getColumnIndex(Constants.KEY_AVAILABILITY)));

                foods.add(food);
            } while (c.moveToNext());
        }
        System.out.println("getAvailableFoods : " + foods.size());
        return foods;
    }

    public List<Food> getAvailableFoods() {
        List<Food> foods = new ArrayList<Food>();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_FOOD + " WHERE "
                + Constants.KEY_AVAILABILITY + " = " + 1 + " ORDER BY " + Constants.KEY_NAME + " ASC";

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(c.getInt((c.getColumnIndex(Constants.KEY_ID))));
                food.setName((c.getString(c.getColumnIndex(Constants.KEY_NAME))));
                food.setWeight(c.getDouble(c.getColumnIndex(Constants.KEY_WEIGHT)));
                food.setPrice(c.getDouble(c.getColumnIndex(Constants.KEY_PRICE)));
                food.setDescription(c.getString(c.getColumnIndex(Constants.KEY_DESCRIPTION)));
                food.setAvailability(c.getInt(c.getColumnIndex(Constants.KEY_AVAILABILITY)));

                foods.add(food);
            } while (c.moveToNext());
        }
        System.out.println("getAvailableFoods : " + foods.size());
        return foods;
    }

    public boolean updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_NAME, food.getName());
        values.put(Constants.KEY_WEIGHT, food.getWeight());
        values.put(Constants.KEY_PRICE, food.getPrice());
        values.put(Constants.KEY_DESCRIPTION, food.getDescription());
        values.put(Constants.KEY_AVAILABILITY, food.getAvailability());

        // insert row
        long id = db.update(Constants.TABLE_FOOD, values, Constants.KEY_ID + " = ?",
                new String[]{String.valueOf(food.getId())});
        System.out.println("fhgfhgfh : " + id);
        return id > 0;

    }
}
