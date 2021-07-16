package me.desolstice.TestPlugin.Database.SQLite;

import me.desolstice.TestPlugin.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDatabase implements Database{

    private static SQLiteDatabase database;
    public static SQLiteDatabase GetDatabaseInstance(){
        if(database == null){
            database = new SQLiteDatabase();
        }
        return database;
    }

    private Connection conn;

    @Override
    public Connection GetConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                return conn;
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        try{
            conn = DriverManager.getConnection(Settings.SQLiteDatabase);
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return conn;
    }

    @Override
    public void Close() {
        if(conn != null){
            try{
                if(!conn.isClosed()){
                    conn.close();
                }
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
            conn = null;
        }
    }
}
