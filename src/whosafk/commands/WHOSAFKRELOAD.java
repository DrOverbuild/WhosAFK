package whosafk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import whosafk.WhosAFK;

/**
 * Created by jasper on 11/30/16.
 */
public class WHOSAFKRELOAD implements CommandExecutor {
	WhosAFK plugin;

	public WHOSAFKRELOAD(WhosAFK plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		plugin.getConfigManager().loadConfig();
		sender.sendMessage("Reloaded the WhosAFK config.");
		return true;
	}
}
