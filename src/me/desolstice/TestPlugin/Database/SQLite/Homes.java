package me.desolstice.TestPlugin.Database.SQLite;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Homes {

    private static boolean initialized = false;
    private static void Initialize(){
        if(!initialized) {
            Connection conn = SQLiteDatabase.GetDatabaseInstance().GetConnection();
            try {
                Statement stmt = conn.createStatement();
                stmt.execute(Queries.CreateHomesTable);
            } catch (SQLException e) {
                System.out.println("SQL error while attempting to create homes table \n" + e.getMessage());
            }
            initialized = true;
        }
    }

    public static void SetHome(Player player, String homeName){
        Initialize();

        Connection conn = SQLiteDatabase.GetDatabaseInstance().GetConnection();
        try{
            PreparedStatement stmt = conn.prepareStatement(Queries.InsertHomes);

            stmt.setString(1, player.getUniqueId().toString());
            stmt.setString(2, homeName);

            Location playerLocation = player.getLocation();

            stmt.setString(3, playerLocation.getWorld().getName());
            stmt.setDouble(4, playerLocation.getX());
            stmt.setDouble(5, playerLocation.getY());
            stmt.setDouble(6, playerLocation.getZ());
            stmt.setDouble(7, playerLocation.getYaw());
            stmt.setDouble(8, playerLocation.getPitch());

            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL error while attempting to insert home into homes table \n" + e.getMessage());
        }
    }

    public static void GetPlayerHomes(Player player){
        Initialize();

        Connection conn = SQLiteDatabase.GetDatabaseInstance().GetConnection();
        try{
            PreparedStatement stmt = conn.prepareStatement(Queries.GetHomes);

            stmt.setString(1, player.getUniqueId().toString());
            System.out.println(player.getUniqueId().toString());

            ResultSet rs = stmt.executeQuery();

            player.sendRawMessage("Your homes are: ");
            while(rs.next()){
                String homeName = rs.getString("homeName");
                String world = rs.getString("world");
                double xCoordinate = rs.getDouble("xCoordinate");
                double yCoordinate = rs.getDouble("yCoordinate");
                double zCoordinate = rs.getDouble("zCoordinate");
                double yaw = rs.getDouble("yaw");
                double pitch = rs.getDouble("pitch");

                System.out.println("Homename: " + homeName + " world: " + world + " x: " + xCoordinate + " y: " + yCoordinate + " z: " + zCoordinate + " yaw: " + yaw + " pitch: " + pitch);
                player.sendRawMessage("Home name: " + homeName + " world: " + world + " x: " + xCoordinate + " y: " + yCoordinate + " z: " + zCoordinate);
            }

        }catch(SQLException e){
            System.out.println("SQL error while attempting to get homes from homes table \n" + e.getMessage());
        }
    }

    public static void TeleportPlayer(Player player, String home){
        Initialize();

        Connection conn = SQLiteDatabase.GetDatabaseInstance().GetConnection();
        try{
            PreparedStatement stmt = conn.prepareStatement(Queries.GetHome);

            stmt.setString(1, player.getUniqueId().toString());
            stmt.setString(2, home);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String world = rs.getString("world");
                double xCoordinate = rs.getDouble("xCoordinate");
                double yCoordinate = rs.getDouble("yCoordinate");
                double zCoordinate = rs.getDouble("zCoordinate");
                float yaw = rs.getFloat("yaw");
                float pitch = rs.getFloat("pitch");

                Location location = new Location(Bukkit.getWorld(world), xCoordinate, yCoordinate, zCoordinate, yaw, pitch);
                player.teleport(location);
            }

        }catch(SQLException e){
            System.out.println("SQL error while attempting to get homes from homes table \n" + e.getMessage());
        }
    }

    private static class Queries{
        public static final String CreateHomesTable =
                  "CREATE TABLE IF NOT EXISTS homes (\n"
                + "  uid text NOT NULL,\n"
                + "  homeName text NOT NULL,\n"
                + "  world text, \n"
                + "  xCoordinate real,\n"
                + "  yCoordinate real,\n"
                + "  zCoordinate real,\n"
                + "  yaw real,\n"
                + "  pitch real,\n"
                + "  PRIMARY KEY (uid, homeName)\n"
                + ")";

        public static final String InsertHomes = "INSERT INTO homes(uid, homeName, world, xCoordinate, yCoordinate, zCoordinate, yaw, pitch) VALUES (?,?,?,?,?,?,?,?)";

        public static final String GetHomes =
                  "SELECT homeName, world, xCoordinate, yCoordinate, zCoordinate, yaw, pitch \n"
                + "FROM homes \n"
                + "WHERE uid = ?";

        public static final String GetHome =
                "SELECT world, xCoordinate, yCoordinate, zCoordinate, yaw, pitch \n"
                + "FROM homes \n"
                + "WHERE uid = ? \n"
                + "  AND homeName = ?";

    }

}
