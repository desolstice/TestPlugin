package me.desolstice.TestPlugin.Commands;

import me.desolstice.TestPlugin.Database.SQLite.Homes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommands {

    public static class SetHome implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

            if(strings.length == 0){
                return false;
            }

            if(commandSender instanceof Player){
                Player player = (Player) commandSender;

                Homes.SetHome(player, strings[0]);
            }
            return true;
        }
    }

    public static class GotoHome implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

            if(strings.length == 0){
                return false;
            }

            if(commandSender instanceof Player){
                Player player = (Player) commandSender;

                Homes.TeleportPlayer(player, strings[0]);
            }
            return true;
        }
    }

    public static class GetHomes implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

            if(commandSender instanceof Player){
                Player player = (Player) commandSender;

                Homes.GetPlayerHomes(player);
            }
            return true;
        }
    }
}
