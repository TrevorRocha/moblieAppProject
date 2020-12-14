package ca.gbc.comp3074.group_project_restaurantapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "Restaurant_Table";
    public static final String TAG_TABLE_NAME = "Tag_Table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TAGS = "tags";
    public static final String COLUMN_RATING = "rating";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Restaurant_Table (ID INTEGER PRIMARY KEY, name TEXT, address TEXT, description TEXT, tags TEXT, rating TEXT)");
        db.execSQL("CREATE TABLE Tag_Table (ID INTEGER PRIMARY KEY, tags TEXT NOT NULL UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Restuarant_Table ");
        onCreate(db);
    }

    public boolean addRestaurant(String name, String address, String description, String tags, String rating){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("description", description);
        contentValues.put("tags", tags);
        contentValues.put("rating", rating);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean addTags(){
        String query = "INSERT OR IGNORE INTO Tag_Table (tags) VALUES " +
                "('Fast Food'), ('Chinese'), ('Sushi'), ('Pizza'), ('Thai'), " +
                "('Indian'), ('Healthy'), ('Italian'), ('Greek'), ('Vegan'), ('Desserts')";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        return true;
    }

    public ArrayList<String> loadTags(){
        ArrayList<String> tagList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT tags FROM Tag_Table ORDER BY tags ASC ", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            tagList.add(cursor.getString(cursor.getColumnIndex("tags")));
            cursor.moveToNext();
        }
        return tagList;
    }

    public ArrayList<String> getRestaurantList(){
        ArrayList<String> restaurantList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            restaurantList.add(cursor.getString((cursor.getColumnIndex(COLUMN_ID))) + " - " +
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)) + " | " +
                    cursor.getString(cursor.getColumnIndex(COLUMN_RATING)) + " | " +
                    cursor.getString(cursor.getColumnIndex(COLUMN_TAGS)));
            cursor.moveToNext();
        }
        return restaurantList;
    }

    public String getColumnName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
    }

    public String getColumnAddress(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
    }

    public String getColumnDescription(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
    }

    public String getColumnTags(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(COLUMN_TAGS));
    }

    public String getColumnRating(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(COLUMN_RATING));
    }

    public boolean editRestaurant(Integer id, String name, String address, String description, String tags, String rating ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("description", description);
        contentValues.put("tags", tags);
        contentValues.put("rating", rating);
        db.update(TABLE_NAME, contentValues, "id = ?", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean deleteRestaurant(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"id = ?", new String[]{Integer.toString(id)});
        return true;
    }
}
