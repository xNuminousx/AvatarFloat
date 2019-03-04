package me.numin.avatarfloat;

import org.bukkit.plugin.java.JavaPlugin;

import com.projectkorra.projectkorra.configuration.ConfigManager;

import me.numin.avatarfloat.FloatListener;

public class Main extends JavaPlugin {

	public static Main plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(new FloatListener(), this);
		ConfigManager.getConfig().addDefault("ExtraAbilities.AvatarFloat.PlayGlow", true);
		ConfigManager.getConfig().addDefault("ExtraAbilities.AvatarFloat.PlaySphere", true);
		ConfigManager.getConfig().addDefault("ExtraAbilities.AvatarFloat.Duation", 6);
		ConfigManager.getConfig().addDefault("ExtraAbilities.AvatarFloat.LevitationDuration", 5);
		ConfigManager.defaultConfig.save();
		plugin.getLogger().info("Successfully enabled AvatarFloat");
	}
	
	@Override
	public void onDisable() {
		plugin.getLogger().info("Successfully disabled AvatarFloat");
	}
}
