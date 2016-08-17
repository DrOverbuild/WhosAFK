package whosafk.events;

import org.bukkit.entity.Player;

/**
 * Created by jasper on 8/17/16.
 */
public class AFKStatusOffEvent extends WhosAFKEvent {
	public AFKStatusOffEvent(String message, Player player) {
		super(message, player);
	}
}
