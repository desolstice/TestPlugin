package me.desolstice.TestPlugin;

import me.desolstice.TestPlugin.Commands.HomeCommands;
import me.desolstice.TestPlugin.Commands.Teleport;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        System.out.println("TestPlugin enabled");

        this.getCommand("teleport").setExecutor(new Teleport());

        this.getCommand("home").setExecutor(new HomeCommands.GotoHome());
        this.getCommand("homes").setExecutor(new HomeCommands.GetHomes());
        this.getCommand("sethome").setExecutor(new HomeCommands.SetHome());

    }
}
