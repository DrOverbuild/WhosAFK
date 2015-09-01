/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package whosafk;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public class WhosAFK extends JavaPlugin{
	
	WhosAFKEventHandler handler;
	WhosAFKCommandExecutor executor;
	
	Map<Player, Integer> afkTimes = new HashMap<>();
	
	@Override
	public void onEnable() {
		handler = new WhosAFKEventHandler(this);
		executor = new WhosAFKCommandExecutor(this);
		
		getServer().getPluginManager().registerEvents(handler, this);
		getCommand("afk").setExecutor(executor);
		
		try {
			Team team = getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("afkers");
			team.setPrefix(ChatColor.BLUE + "");
			team.setSuffix(" (AFK)");
		} catch (IllegalArgumentException e) {
			// Team will already have existed. We will use the already existing team.
		}
		
		getServer().getScheduler().runTaskTimer(this, new WhosAFKRunnable(this), 100, 100);
	}

	@Override
	public void onDisable() {
		saveConfig();

		getLogger().info("WhosAFK is being disabled. If you are uninstalling WhosAFK, please remember to\n"
				       + "use the command \"/scoreboard teams remove afkers\" to remove the scoreboard.");
	}

	public Map<Player, Integer> getAfkTimes() {
		return afkTimes;
	}

	public void toggleAFKStatus(Player p){
		Team team = getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers");
		if(playerIsAFK(p)){
				removeAFKStatus(p);
		}else{
			getServer().broadcastMessage("* " + ChatColor.BLUE + p.getName() + " is now AFK.");
			team.addPlayer(getServer().getOfflinePlayer(p.getUniqueId()));
		}
	}
	
	public void removeAFKStatus(Player p){
		getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers").removePlayer(getServer().getOfflinePlayer(p.getUniqueId()));
		getServer().broadcastMessage("* " + ChatColor.BLUE + p.getName() + " is no longer AFK.");
	}
	
	public boolean playerIsAFK(Player p){
		Team team = getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers");
		return team.hasPlayer(getServer().getOfflinePlayer(p.getUniqueId()));
	}
	
	public Player getPlayer(String playerName){
		for(Player p:getServer().getOnlinePlayers()){
			if(p.getName().equals(playerName)){
				return p;
			}
		}
		return null;
	}
}
