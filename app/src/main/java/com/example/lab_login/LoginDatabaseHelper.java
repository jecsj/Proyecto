package com.example.lab_login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LoginDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Usuario.db";
    public static final String TABLE_NAME="Register_table";
    public static final String COL1="ID";
    public static final String COL2="FULLNAME";
    public static final String COL3="USERNAME";
    public static final String COL4="EMAIL";
    public static final String COL5="PASSWORD";
    public static final String COL6="TIPOUSUARIO";
    public static final String COL7="ESTADO";
    public static final String COL8="PROCONTAG";
    public static final String COL9="SINTOMAS";
    public static final String TABLE_NAME2="SQLiteGPS";
    private static final int BD_version=1;
    public static final String COL11="IDf";

    public static final String COL12="id";
    public static final String COL22="direccion";
    public static final String COL32="fechahora";
    public static final String COL42="hora";

    public LoginDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table "+ TABLE_NAME+" ("+COL1+" INTEGER, "+
                COL2+ " TEXT, "+COL3+" TEXT, "+COL4+" TEXT, "+COL5+" TEXT, "+ COL6+" INTEGER, " +
                COL7+" INTEGER, "+ COL8+ " REAL, "+COL9+" TEXT)");
        String sql ="create table "+ TABLE_NAME2
                +" ("+COL11+" INTEGER, "+COL12+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                COL22+ " VARCHAR, "+ COL32+" VARCHAR, " +COL42+" INTEGER)";

        db.execSQL(sql);
    }


    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);

        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2);
        onCreate(db);
    }
    public void initData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        onUpgrade(db,1,2);
    }
    public boolean insertData(User user)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL1,user.getId());
        contentValues.put(COL2,user.getFullName());
        contentValues.put(COL3,user.getUserName());
        contentValues.put(COL4,user.geteMail());
        contentValues.put(COL5,user.getPassword());
        contentValues.put(COL6,user.getTipoUsuario());
        contentValues.put(COL7,user.getEstado());
        contentValues.put(COL8,user.getProcontag());
        contentValues.put(COL9,"ninguna");
       long result= db.insert(TABLE_NAME,null,contentValues);
       if(result==-1)
           return false;
       else
           return true;
    }
    public boolean addData(int insert,String insert2,String insert3,int insert4)

    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL11,insert);
        contentValues.put(COL22,insert2);
        contentValues.put(COL32,insert3);
        contentValues.put(COL42,insert4);
        long result= db.insert(TABLE_NAME2,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getProbabilidad(String direccion,String fecha, int hora){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select a.ESTADO,b.direccion,b.fechahora, b.hora from SQLiteGPS b, Register_table a where" +
                " a.id = b.IDf and b.direccion ='"+
                direccion+"' and b.fechahora = '"+fecha+"' and b.hora BETWEEN "+
                (hora-1)+" and "+(hora+1), null);
        return result;
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+ TABLE_NAME, null);
        return result;
    }


    public Cursor getAllDataU(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+ TABLE_NAME +" WHERE TIPOUSUARIO = 1", null);
        return result;
    }
    public Cursor getEstado(int idc){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select ESTADO, PROCONTAG from "+ TABLE_NAME +" WHERE ID= "+idc, null);
        return result;
    }

    public Cursor getData(int id)
    {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor res= db.rawQuery("select * from "+ TABLE_NAME+ " where id="+id+"",null);
        return res;
    }
    public Cursor findDataByEmail(String email,String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME+" where "+COL4+"= '"+email+"'" +" and "+COL5+"= '"+password+"'",null);
        return res;
    }
    public void actualizarprobabilidad(int id, double probabilidad)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        //Establecemos los campos-valores a actualizar
        ContentValues valores = new ContentValues();
        valores.put(COL8,probabilidad);

        //Actualizamos el registro en la base de datos
        db.update(TABLE_NAME, valores, COL1+"='"+id+"'", null);

    }
    public void actualizarsintomas(int id, String sintomas)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        //Establecemos los campos-valores a actualizar
        ContentValues valores = new ContentValues();
        valores.put(COL9,sintomas);
        //Actualizamos el registro en la base de datos
        db.update(TABLE_NAME, valores, COL1+"='"+id+"'", null);

    }
    public void actualizarestado(String email, int estado)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        //Establecemos los campos-valores a actualizar
        ContentValues valores = new ContentValues();
        valores.put(COL7,estado);

//Actualizamos el registro en la base de datos
        db.update(TABLE_NAME, valores, COL4+"='"+email+"'", null);

    }
    public Cursor findByEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME+" where "+COL4+"= '"+email+"'",null);
        return res;
    }
    public Cursor findEstados(int  estados)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME+" where "+COL7+"= '"+estados+"' and TIPOUSUARIO=1",null);
        return res;
    }
}
