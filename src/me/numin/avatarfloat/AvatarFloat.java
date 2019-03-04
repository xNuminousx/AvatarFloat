package me.numin.avatarfloat;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.avatar.AvatarState;
import com.projectkorra.projectkorra.configuration.ConfigManager;

public class AvatarFloat {
	
	boolean playGlow;
	boolean playSphere;
	boolean applyPotion;
	int currPoint;
	int potDuration;
	long time;
	long duration;
	DustOptions blue = new DustOptions(Color.fromRGB(0, 255, 247), 1);
	DustOptions white = new DustOptions(Color.fromRGB(255, 255, 255), 1);
	Player player;
	
	public AvatarFloat(Player player) {
		playGlow = ConfigManager.getConfig().getBoolean("ExtraAbilities.AvatarFloat.PlayGlow");
		playSphere = ConfigManager.getConfig().getBoolean("ExtraAbilities.AvatarFloat.PlaySphere");
		duration = ConfigManager.getConfig().getLong("ExtraAbilities.AvatarFloat.Duation")*1000;
		potDuration = ConfigManager.getConfig().getInt("ExtraAbilities.AvatarFloat.LevitationDuration");
		time = System.currentTimeMillis();
		applyPotion = true;
		this.player = player;
		new BukkitRunnable() {
			@Override
			public void run() {
				if (player.isDead() || !player.isOnline() || !player.hasPermission("bending.ability.avatarfloat") || !player.isOp()) {
					remove(player);
					cancel();
				}
				if (!CoreAbility.hasAbility(player, AvatarState.class)) {
					remove(player);
					cancel();
				}
				if (System.currentTimeMillis() > time + duration) {
					remove(player);
					cancel();
				}
				progress(player);
			}
		}.runTaskTimer(Main.plugin, 0, 0);
	}
	public void progress(Player player) {
		if (playSphere) {
			circles(player);
		}
		if (playGlow) {
			eyeGlow(player);
		}
		if (applyPotion) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, potDuration*20, 1), true);
			applyPotion = false;
		}
		if (System.currentTimeMillis() > time + potDuration*1000) {
			Vector vec = player.getLocation().getDirection().normalize().multiply(0);
			player.setVelocity(vec);
		}
	}
	public void eyeGlow(Player player) {
		Location rightEye = GeneralMethods.getRightSide(player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(0.36)), 0.18);
		Location leftEye = GeneralMethods.getLeftSide(player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(0.36)), 0.18);
		player.getWorld().spawnParticle(Particle.REDSTONE, rightEye, 1, 0, 0, 0, 0, blue);
		player.getWorld().spawnParticle(Particle.REDSTONE, leftEye, 1, 0, 0, 0, 0, blue);
		player.getWorld().spawnParticle(Particle.REDSTONE, rightEye, 1, 0, 0, 0, 0, white);
		player.getWorld().spawnParticle(Particle.REDSTONE, leftEye, 1, 0, 0, 0, 0, white);
	}
	public void circles(Player player) {
		player.getWorld().spawnParticle(Particle.SPELL_MOB_AMBIENT, player.getLocation().add(0, 4, 0), 3, 0.5, 0.5, 0.5, 0);
		rotateCircle(player, 2, 3);
		rotateCircle(player,3, 2);
		rotateCircle(player,4, 1);
		rotateCircle(player,3, 0);
		rotateCircle(player,2, -1);
		player.getWorld().spawnParticle(Particle.SPELL_MOB_AMBIENT, player.getLocation().add(0, -2, 0), 3, 0.5, 0.5, 0.5, 0);
		player.getWorld().spawnParticle(Particle.SPELL, player.getLocation().add(0, -1, 0), 3, 4, 4, 4, 0);
	}
	public void rotateCircle(Player player, double size, double height) {
		Location location = player.getLocation();
		for (int i = 0; i < 6; i++) {
			currPoint += 360/180;
			if (currPoint > 360) {
				currPoint = 0;
			}
			double angle = currPoint * Math.PI / 180;
			double x = size * Math.cos(angle);
			double z = size * Math.sin(angle);
			location.add(x, height, z);
			player.getWorld().spawnParticle(Particle.SPELL_MOB_AMBIENT, location, 1, 0.2, 0.2, 0.2, 0);
			location.subtract(x, height, z);
		}
	}
	public void remove(Player player) {
		if (player.hasPotionEffect(PotionEffectType.LEVITATION)) {
			player.removePotionEffect(PotionEffectType.LEVITATION);
		}
	}
}
