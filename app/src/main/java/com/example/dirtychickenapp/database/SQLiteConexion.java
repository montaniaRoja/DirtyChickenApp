package com.example.dirtychickenapp.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dirtychickenapp.objetos.Cliente;

public class SQLiteConexion extends SQLiteOpenHelper
{
    public SQLiteConexion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        // Crear los objectos de base de datos
        sqLiteDatabase.execSQL(Transacciones.CreateTableClientes);
        sqLiteDatabase.execSQL(Transacciones.CreateTablePedidos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(Transacciones.DropTableClientes);
        sqLiteDatabase.execSQL(Transacciones.DropTablePedidos);
        onCreate(sqLiteDatabase);

    }
    /*
    @SuppressLint("Range")
    public Cliente getContactoById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cliente cliente = null;

        String[] columns = {Transacciones.id_cliente, Transacciones.nombre_cliente, Transacciones.tel_cliente, Transacciones.dir_cliente ,Transacciones.lat_cliente, Transacciones.long_cliente,Transacciones.correo_cliente};
        String selection = Transacciones.id_cliente + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(Transacciones.Tabla1, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {

            cliente = new Cliente();
            cliente.setId(cursor.getInt(cursor.getColumnIndex(Transacciones.id_cliente)));
            cliente.setNombre(cursor.getString(cursor.getColumnIndex(Transacciones.nombre_cliente)));

            cliente.setTelefono(cursor.getString(cursor.getColumnIndex(Transacciones.tel_cliente)));
            cliente.setDireccion(cursor.getString(cursor.getColumnIndex(Transacciones.dir_cliente)));

            cliente.setLatitud(cursor.getDouble(cursor.getColumnIndex(Transacciones.lat_cliente)));
            cliente.setLongitud(cursor.getDouble(cursor.getColumnIndex(Transacciones.long_cliente)));
            cliente.setCorreo(cursor.getString(cursor.getColumnIndex(Transacciones.correo_cliente)));
        }

        cursor.close();
        db.close();

        return cliente;
    }
    */



}