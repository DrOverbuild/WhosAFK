package whosafk;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scoreboard.Team;

/**
 * Created by jasper on 11/30/16.
 */
public class ConfigManager {

	WhosAFK plugin;

	FileConfiguration config;

	public ConfigManager(WhosAFK plugin) {
		this.plugin = plugin;

		plugin.saveConfig();
		config = plugin.getConfig();

		config.addDefault("auto-afk.enabled", true);
		config.addDefault("auto-afk.timeout", 60);
		config.addDefault("allow-movement-while-afk", true);
		config.addDefault("movement-affects-afk", true);
		config.addDefault("allow-collisions", false);

		config.options().copyDefaults(true);
		plugin.saveConfig();
	}

	public boolean getAutoAFKEnabled(){
		return config.getBoolean("auto-afk.enabled", true);
	}

	public int getAutoAFKTimeOut(){
		return config.getInt("auto-afk.timeout", 60);
	}

	public boolean getAllowMovementWhileAFK(){
		return config.getBoolean("allow-movement-while-afk", true);
	}

	public boolean getMovementAffectsAFK(){
		return config.getBoolean("movement-affects-afk", true);
	}

	public boolean getAllowCollisions(){
		return config.getBoolean("allow-collisions", false);
	}

	public void loadConfig(){
		plugin.reloadConfig();
		config = plugin.getConfig();
		plugin.getLogger().info("--- Loading WhosAFK ---");
		plugin.getLogger().info("auto-afk.enabled: " + getAutoAFKEnabled());
		plugin.getLogger().info("auto-afk.timeout: " + getAutoAFKTimeOut());
		plugin.getLogger().info("allow-movement-while-afk: " + getAllowMovementWhileAFK());
		plugin.getLogger().info("movement-affects-afk: " + getMovementAffectsAFK());
		plugin.getLogger().info("allow-collisions: " + getAllowCollisions());
		plugin.getLogger().info("----- Load complete -----");

		Team team = plugin.getServer().getScoreboardManager().getMainScoreboard().getTeam("afkers");

		if (getAllowCollisions()){
			team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
		} else {
			team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
		}
	}
}
