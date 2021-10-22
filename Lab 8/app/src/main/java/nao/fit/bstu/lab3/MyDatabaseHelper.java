package nao.fit.bstu.lab3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import nao.fit.bstu.lab3.Room.Cybersport;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cybersport.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "users"; // название таблицы в бд
    public static final String COLUMN_ID = "_id";

    public static final String NAME = "name";
    public static final String LAST_NAME = "last_anme";
    public static final String AGE = "age";
    public static final String SALARY = "salary";
    public static final String PHONE_NUMBER ="phone_number";
    public static final String PHOTO ="photo";
    public static final String EMAIL ="email";
    public static final String INSTAGRAM ="instagram";
    Cursor userCursor;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE+ "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT, "
                + LAST_NAME + " TEXT, "
                + AGE + " INTEGER, "
                + SALARY + " REAL, "
                + PHONE_NUMBER + " TEXT, "
                + PHOTO + " TEXT, "
                + EMAIL + " TEXT, "
                + INSTAGRAM + " TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public void addItemToSQLLite(SQLiteDatabase db, String name, String lastName, String age,String salary, String phoneNumber,
                                    String photo, String email, String instagram){
        db.execSQL("INSERT INTO "+ TABLE +" ("
                + NAME + ", "
                + LAST_NAME + ", "
                + AGE + ", "
                + SALARY + ", "
                + PHONE_NUMBER + ", "
                + PHOTO + ", "
                + EMAIL + ", "
                + INSTAGRAM  + ") VALUES ( '" + name+ "','"+lastName+"' , '"+age+"','"+salary+"','"+phoneNumber+"','" + photo+ "','"+email+"','"+instagram+"');");
    }

    public Cursor selectAllItems(SQLiteDatabase db){
        userCursor = db.query(true, TABLE, new String[] {COLUMN_ID,NAME,LAST_NAME,AGE,SALARY,PHONE_NUMBER,PHOTO,EMAIL,INSTAGRAM},
                COLUMN_ID,
                null, null, null, null,
                null);
        userCursor.moveToFirst();;
        return userCursor;
    }

    public Cursor selectAllItemsWithOrder(SQLiteDatabase db,String desc){
        userCursor = db.query(true, TABLE, new String[] {COLUMN_ID,NAME,LAST_NAME,AGE,SALARY,PHONE_NUMBER,PHOTO,EMAIL,INSTAGRAM},
                COLUMN_ID,
                null, null, null, COLUMN_ID+desc,
                null);
        userCursor.moveToFirst();;
        return userCursor;
    }

    @SuppressLint("Range")
    public List<Cybersport> getAllItems(Cursor cursor){
        List<Cybersport> cybersportList=new ArrayList<>();
        if(cursor.getCount()>0) {
            do {
                cybersportList.add(new Cybersport(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(NAME)),
                        cursor.getString(cursor.getColumnIndex(LAST_NAME)),
                        cursor.getInt(cursor.getColumnIndex(AGE)),
                        cursor.getDouble(cursor.getColumnIndex(SALARY)),
                        cursor.getString(cursor.getColumnIndex(PHONE_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(EMAIL)),
                        cursor.getString(cursor.getColumnIndex(INSTAGRAM)),
                        cursor.getString(cursor.getColumnIndex(PHOTO))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cybersportList;
    }

    @SuppressLint("Range")
    public Cybersport getItem(Cursor cursor) {
        return new Cybersport(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(NAME)),
                cursor.getString(cursor.getColumnIndex(LAST_NAME)),
                cursor.getInt(cursor.getColumnIndex(AGE)),
                cursor.getDouble(cursor.getColumnIndex(SALARY)),
                cursor.getString(cursor.getColumnIndex(PHONE_NUMBER)),
                cursor.getString(cursor.getColumnIndex(EMAIL)),
                cursor.getString(cursor.getColumnIndex(INSTAGRAM)),
                cursor.getString(cursor.getColumnIndex(PHOTO)));
    }


    public Cursor selectItemById(SQLiteDatabase db, String id){

        userCursor = db.query(true, TABLE, new String[] {COLUMN_ID,NAME,LAST_NAME,AGE,SALARY,PHONE_NUMBER,PHOTO,EMAIL,INSTAGRAM},
                COLUMN_ID + " LIKE ?",
                new String[] {"%"+ id+"%" }, null, null, null,
                null);
        userCursor.moveToFirst();
        return userCursor;
    }
    public Cursor selectAllItemsWithSalary(SQLiteDatabase db){
        userCursor = db.query(true, TABLE, new String[] {COLUMN_ID,NAME,LAST_NAME,AGE,SALARY,PHONE_NUMBER,PHOTO,EMAIL,INSTAGRAM},
                SALARY + " > ?",
                new String[] {"0"}, null, null, null,
                null);
        userCursor.moveToFirst();
        return userCursor;
    }
//    public Cursor SelectAllItemsCheck(SQLiteDatabase db, String check){
//        userCursor = db.rawQuery("select * from "+ TABLE + " WHERE " +ISCHECK + "=?", new String[]{String.valueOf(check)});
//        userCursor.moveToFirst();
//        return userCursor;
//    }
    public void updateItem(SQLiteDatabase db,String id, ContentValues cv){
        db.update(TABLE,cv,COLUMN_ID + "=" + String.valueOf(id),null);
    }
    public void deleteItem(SQLiteDatabase db,String id){
        db.delete(TABLE, "_id = ?", new String[]{String.valueOf(id)});
    }
    public void addItem(SQLiteDatabase db,ContentValues cv){
        db.insert(TABLE,null,cv);
    }

}
