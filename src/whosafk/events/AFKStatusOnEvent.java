package whosafk.events;

import org.bukkit.entity.Player;

/**
 * Created by jasper on 8/17/16.
 */
public class AFKStatusOnEvent extends WhosAFKEvent {
	public AFKStatusOnEvent(String message, Player player) {
		super(message, player);
	}
}
