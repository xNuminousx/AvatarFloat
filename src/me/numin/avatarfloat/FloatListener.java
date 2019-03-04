package me.numin.avatarfloat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.event.AbilityStartEvent;

public class FloatListener implements Listener {
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if (event.isCancelled() || player.equals(null)) {
			return;

		}
		player.sendMessage("shift worked");
	}
	
	@EventHandler
	public void onAvatarState(AbilityStartEvent event) {
		Ability ability = event.getAbility();
		Player user = ability.getPlayer();
		if (user.equals(null) || ability.equals(null)) {
			return;
		} else if (ability.getName().equalsIgnoreCase("AvatarState")) {
			user.sendMessage(ability.getName() + " activated");
			new AvatarFloat(user);
		}
	}
}
