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
import whosafk.commands.AFK;
import whosafk.events.AFKStatusOffEvent;

public class WhosAFK extends JavaPlugin{
	public static WhosAFK instance;
	
	WhosAFKEventHandler handler;
	AFK afkCommand;
	
	Map<Player, Integer> afkTimes = new HashMap<>();
	
	@Override
	public void onEnable() {
		handler = new WhosAFKEventHandler(this);
		afkCommand = new AFK(this);
		
		getServer().getPluginManager().registerEvents(handler, this);
		getCommand("afk").setExecutor(afkCommand);
		
		try {
			Team team = getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("afkers");
			team.setPrefix(ChatColor.BLUE + "");
			team.setSuffix(" (AFK)");
		} catch (IllegalArgumentException e) {
			// Team will already have existed. We will use the already existing team.
		}
		
		getServer().getScheduler().runTaskTimer(this, new WhosAFKRunnable(this), 100, 100);

		instance = this;
	}

	@Override
	public void onDisable() {
		saveConfig();

		afkTimes.clear();

		getLogger().info("WhosAFK is being disabled. If you are uninstalling WhosAFK, please remember to\n"
				       + "use the command \"/scoreboard teams remove afkers\" to remove the scoreboard.");
	}

	public Map<Player, Integer> getAfkTimes() {
		return afkTimes;
	}

	public void toggleAFKStatus(Player p){
		if(playerIsAFK(p)){
				removeAFKStatus(p);
		}else{
			addAFKStatus(p);
		}
	}
	
	public void removeAFKStatus(Player p){
		AFKStatusOffEvent event = new AFKStatusOffEvent("* " + ChatColor.BLUE + p.getName() + " is no longer AFK.", p);
		getServer().getPluginManager().callEvent(event);

		if(!event.isCancelled()) {
			if(event.getPlayer() != null) {
				getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers")
						.removePlayer(getServer().getOfflinePlayer(event.getPlayer().getUniqueId()));
			}

			if (event.getMessage() != null) {
				getServer().broadcastMessage(event.getMessage());
			}
		}
	}

	public void addAFKStatus(Player p){
		AFKStatusOffEvent event = new AFKStatusOffEvent("* " + ChatColor.BLUE + p.getName() + " is now AFK.", p);
		getServer().getPluginManager().callEvent(event);

		if(!event.isCancelled()) {
			if(event.getPlayer() != null) {
				Team team = getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers");
				team.addPlayer(getServer().getOfflinePlayer(p.getUniqueId()));
			}

			if(event.getMessage() != null){
				getServer().broadcastMessage(event.getMessage());
			}
		}
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
