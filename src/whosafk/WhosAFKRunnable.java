/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package whosafk;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jasper
 */
public class WhosAFKRunnable implements Runnable{
	WhosAFK plugin;
	public WhosAFKRunnable(WhosAFK plugin){
		this.plugin = plugin;
	}

	@Override
	public void run() {
		for(Player p:Bukkit.getOnlinePlayers()){
			if(plugin.playerIsAFK(p)){
				return;
			}
			
			if(plugin.getAfkTimes().containsKey(p)){
				plugin.getAfkTimes().replace(p, plugin.getAfkTimes().get(p) + 1);
			}else{
				plugin.getAfkTimes().put(p, 0);
			}
			
			if(plugin.getAfkTimes().get(p) > 60){
				plugin.toggleAFKStatus(p);
			}
		}
	}
}
