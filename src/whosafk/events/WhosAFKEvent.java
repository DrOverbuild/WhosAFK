package whosafk.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by jasper on 8/17/16.
 */
public class WhosAFKEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	String message;
	Player player;

	public WhosAFKEvent(String message, Player player) {
		this.message = message;
		this.player = player;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		cancelled = b;
	}

	public String getMessage() {
		return message;
	}

	public Player getPlayer() {
		return player;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public static HandlerList getHandlerList(){
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
