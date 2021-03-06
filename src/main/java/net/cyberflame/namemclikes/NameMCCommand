package net.cyberflame.namemclikes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class NameMCCommand
  implements CommandExecutor
{
  private Messages M = Messages.getInstance();
  private ConsoleCommandSender console = Bukkit.getConsoleSender();
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      return true;
    }

    
    Player s = (Player)sender;

    
    String uuid = s.getUniqueId().toString();

    
    if (Main.hasAlreadyLiked(uuid)) {
      s.sendMessage(this.M.PREFIX + this.M.ALREADY_LIKED);
      return true;
    } 

    
    if (Main.isBeingChecked(uuid)) {
      s.sendMessage(this.M.PREFIX + this.M.ALREADY_BEING_CHECKED);
      return true;
    } 

    
    s.sendMessage(this.M.PREFIX + this.M.NOW_CHECKING);
    
    Main.addPlayerToCheckingList(uuid);
    boolean result = checkPlayer(uuid);
    Main.removePlayerFromCheckingList(uuid);
    
    if (!result) {
      
      s.sendMessage(this.M.PREFIX + this.M.CHECK_FAILED);
    }
    else {
      
      s.sendMessage(this.M.PREFIX + this.M.CHECK_SUCCEEDED);

      
      Main.addPlayerToConfirmedList(uuid);

      
      List<String> commandsToExecute = Main.getInstance().getConfig().getStringList("command-list");
      
      for (String command : commandsToExecute) {

        
        command = command.replace("%player%", s.getName());
        Bukkit.dispatchCommand((CommandSender)this.console, command);
      } 
    } 

    
    return true;
  }
  
  private boolean checkPlayer(String uuid) {
    String serverName = Main.getInstance().getConfig().getString("server-name");
    
    try {
      URL url = new URL("https://api.namemc.com/server/" + serverName + "/likes?profile=" + uuid);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("GET");
      
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      
      int status = con.getResponseCode();
      
      if (status < 300) {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String input = in.readLine();
        in.close();
        
        return input.equals("true");
      } 

      
      this.console.sendMessage(this.M.PREFIX + "NameMC returned an error response (" + status + "). Please report this to Sulphate on the Spigot forums.");
      return false;
    
    }
    catch (Exception ex) {
      ex.printStackTrace();
      this.console.sendMessage(this.M.PREFIX + "There was an error checking for likes. Please report the above error to Sulphate on the Spigot forums.");
      return false;
    } 
  }
}
