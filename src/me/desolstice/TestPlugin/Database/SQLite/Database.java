package me.desolstice.TestPlugin.Database.SQLite;

import java.sql.Connection;

public interface Database {
    Connection GetConnection();
    void Close();
}
