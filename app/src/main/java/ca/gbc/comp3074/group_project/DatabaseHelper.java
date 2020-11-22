package ca.gbc.comp3074.group_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "restaurant.db";
    private static final String DB_TABLE = "restaurant_table";

    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String ADDRESS = "ADDRESS";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String TAGS = "TAGS";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE +" (" + ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT, " + ADDRESS +" TEXT, " + DESCRIPTION + " TEXT, " + TAGS + " TEXT " + ")";


    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE);
        onCreate(db);
    }

    public boolean insertData(String id, String name, String address, String description, String tags){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(ADDRESS, address);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(TAGS, tags);
        db.update(DB_TABLE, contentValues, "ID = ?", new String[] { id });
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DB_NAME, "ID = ?", new String[] {id});
    }
}
