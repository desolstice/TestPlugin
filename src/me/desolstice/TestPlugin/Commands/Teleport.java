package me.desolstice.TestPlugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {

        if(strings.length == 0){
            return false;
        }

        String playerName = strings[0];

        //This is called whenever we call our command
        if(commandSender instanceof Player){

            //It isn't the console sending the command
            Player player = (Player) commandSender;

            Player targetPlayer = Bukkit.getPlayer(strings[0]);
            if(targetPlayer == null){
                System.out.println("Could not find player with name: " + playerName);
                player.sendRawMessage("Could not find player with name: " + playerName);
                return true;
            }

            player.teleport(targetPlayer);
        }
        return true;
    }
}
