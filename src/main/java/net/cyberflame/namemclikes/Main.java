package net.cyberflame.namemclikes;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class Main
  extends JavaPlugin
{
  private static Main instance;
  private static ArrayList<String> currentlyChecking = new ArrayList<String>();
  private ConsoleCommandSender console = Bukkit.getConsoleSender();

  
  public void onEnable() {
    instance = this;

    
    StartupUtils utils = new StartupUtils(this, this.console, "NameMCLikes", getDataFolder());
    if (!utils.checkAndCreateConfig()) {
      return;
    }

    
    Messages M = Messages.getInstance(getConfig());

    
    getCommand("namemc").setExecutor(new NameMCCommand());

    
    this.console.sendMessage(M.PREFIX + "NameMCLikes loaded successfully.");
  }

  
  public void onDisable() {
    instance = null;
    this.console.sendMessage("[NameMCLikes] NameMCLikes has been disabled.");
  }
  
  public static Main getInstance() {
    return instance;
  }





  
  public static boolean isBeingChecked(String uuid) {
    return currentlyChecking.contains(uuid);
  }

  
  public static void addPlayerToCheckingList(String uuid) {
    currentlyChecking.add(uuid);
  }

  
  public static void removePlayerFromCheckingList(String uuid) {
    currentlyChecking.remove(uuid);
  }

  
  public static boolean hasAlreadyLiked(String uuid) {
    return instance.getConfig().getStringList("uuid-list").contains(uuid);
  }

  
  public static void addPlayerToConfirmedList(String uuid) {
    List<String> currentList = instance.getConfig().getStringList("uuid-list");
    currentList.add(uuid);
    
    instance.getConfig().set("uuid-list", currentList);
    instance.saveConfig();
  }
}
