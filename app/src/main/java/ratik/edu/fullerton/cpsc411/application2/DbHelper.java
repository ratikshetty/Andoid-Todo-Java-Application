package ratik.edu.fullerton.cpsc411.application2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todo.db";
    public static final String  TABLE_NAME =" todo_data";
    public static final String  COL1 = "ID";
    public static final String  COL2 = "RECORD";
    public static final String COL3= "DELETED";

    public DbHelper(Context context){super(context,DATABASE_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable="Create Table" + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, RECORD TEXT, DELETED BOOLEAN)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addData(String data){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL2,data);
        cv.put(COL3,false);

        long result= db.insert(TABLE_NAME,null,cv);

        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public  boolean updateData(String data, String id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL2,data);

        long result=db.update(TABLE_NAME,cv,COL1+"="+id,null);

        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("Select * from " + TABLE_NAME + " Where "+COL3+"=0",null);
        return data;
    }

    public boolean deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL3,1);

        long result=db.update(TABLE_NAME,cv,COL1+"="+id,null);

        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }
}
