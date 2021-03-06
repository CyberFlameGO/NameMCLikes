package net.cyberflame.namemclikes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

public class StartupUtils {
  private Main main;
  
  public StartupUtils(Main main, ConsoleCommandSender console, String pluginName, File dataFolder) {
    this.main = main;
    this.console = console;
    this.pluginName = pluginName;
    this.dataFolder = dataFolder;
  }
  private ConsoleCommandSender console; private String pluginName;
  private File dataFolder;
  
  boolean checkAndCreateConfig() {
    this.dataFolder.mkdir();
    
    File config = new File(this.dataFolder + File.separator + "config.yml");
    
    try {
      if (config.createNewFile()) {
        this.console.sendMessage("[" + this.pluginName + "] No config detected, loading default config.");
        return copyDefaultConfig();
      } 
      
      return true;
    
    }
    catch (IOException ex) {
      haltPlugin(ex);
      return false;
    } 
  }


  
  private boolean copyDefaultConfig() {
    try {
      InputStream defaultConfigStream = this.main.getResource("default-config.yml");
      byte[] fileBuffer = new byte[defaultConfigStream.available()];
      defaultConfigStream.read(fileBuffer);

      
      File newConfig = new File(this.dataFolder + File.separator + "config.yml");

      
      OutputStream outStream = new FileOutputStream(newConfig);
      outStream.write(fileBuffer);
      
      return true;
    }
    catch (IOException ex) {
      
      haltPlugin(ex);
      return false;
    } 
  }

  
  private void haltPlugin(IOException ex) {
    ex.printStackTrace();
    this.console.sendMessage("[" + this.pluginName + "] Failed to create default config. See above for the error details.");
    Bukkit.getPluginManager().disablePlugin((Plugin)this.main);
  }
}
