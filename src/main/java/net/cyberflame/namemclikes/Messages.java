package net.cyberflame.namemclikes;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;





public class Messages
{
  private static Messages instance;
  String PREFIX;
  String ALREADY_LIKED;
  String ALREADY_BEING_CHECKED;
  String NOW_CHECKING;
  String CHECK_FAILED;
  String CHECK_SUCCEEDED;
  
  private Messages(FileConfiguration config) {
    this.PREFIX = colourise(config.getString("prefix"));
    this.ALREADY_LIKED = colourise(config.getString("already-liked"));
    this.ALREADY_BEING_CHECKED = colourise(config.getString("already-being-checked"));
    this.NOW_CHECKING = colourise(config.getString("now-checking"));
    this.CHECK_FAILED = colourise(config.getString("check-failed"));
    this.CHECK_SUCCEEDED = colourise(config.getString("check-succeeded"));
  }

  
  static Messages getInstance(FileConfiguration config) {
    if (instance == null) {
      instance = new Messages(config);
    }
    
    return instance;
  }

  
  static Messages getInstance() {
    return instance;
  }
  
  private String colourise(String toColourise) {
    return ChatColor.translateAlternateColorCodes('&', toColourise);
  }
}
