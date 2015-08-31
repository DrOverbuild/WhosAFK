/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package whosafk;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author jasper
 */
public class WhosAFKEventHandler implements Listener{
	
	WhosAFK plugin;
	
	public WhosAFKEventHandler(WhosAFK plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void playerLeft(PlayerQuitEvent e){
		try{
			if(plugin.playerIsAFK(e.getPlayer())){
				plugin.removeAFKStatus(e.getPlayer());
			}
		}catch(Exception ex){
			plugin.getLogger().info("An error has occurred but don't worry because it has been handled.");
			// ^ not really
		}
		plugin.getAfkTimes().remove(e.getPlayer());
	}
	
	/**
	 * Will remove a the player's AFK status from them upon login if the player has the AFK status.
	 * 
	 * In normal cases a player should never be in the afkers team upon login, as the server automatically removes his/her
	 * AFK status once the player leaves. This event, however, is handled in the rare case of a crash, where the server
	 * did not get the chance to completely disconnect each player and/or run the PlayerQuitEvent on each player.
	 * @param e 
	 */
	@EventHandler
	public void playerJoins(PlayerJoinEvent e){
		if(plugin.playerIsAFK(e.getPlayer())){
			plugin.removeAFKStatus(e.getPlayer());
		}
	}
	
	@EventHandler
	public void playerExecutesCommand(PlayerCommandPreprocessEvent e){
		try{
			if(e.getMessage().startsWith("/msg ")||e.getMessage().startsWith("/w ")||e.getMessage().startsWith("/tell ")){
				String recipientName = e.getMessage().split(" ")[1];
				for(Player p: plugin.getServer().getOnlinePlayers()){
					if(p.getName().equals(recipientName)&&plugin.playerIsAFK(p)){
						e.getPlayer().sendMessage(recipientName+" is AFK and may not respond immediately.");
					}
				}
			}
		}catch(Exception ex){
			
		}
	}
	
	@EventHandler
	public void playerDamaged(EntityDamageEvent e){
		if(e.getEntityType().equals(EntityType.PLAYER)){
			Player p = (Player)e.getEntity();
			if(plugin.playerIsAFK(p)){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void playerMoves(PlayerMoveEvent e){
		plugin.getAfkTimes().replace(e.getPlayer(), 0);
		if(plugin.playerIsAFK(e.getPlayer())){
			if(			!(  e.getFrom().getX()==e.getTo().getX()
						  &&e.getFrom().getY()==e.getTo().getY()
						  &&e.getFrom().getZ()==e.getTo().getZ()
					     )
			  ){
				plugin.removeAFKStatus(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void playerChats(PlayerChatEvent e){
		plugin.getAfkTimes().replace(e.getPlayer(), 0);
		if(plugin.playerIsAFK(e.getPlayer())){
			plugin.removeAFKStatus(e.getPlayer());
		}
	}
}
