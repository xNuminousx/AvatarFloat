package me.numin.avatarfloat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.event.AbilityStartEvent;

public class FloatListener implements Listener {
	@EventHandler
	public void onAvatarState(AbilityStartEvent event) {
		Ability ability = event.getAbility();
		Player user = ability.getPlayer();
		if (user.equals(null) || ability.equals(null)) {
			return;
		} else if (ability.getName().equalsIgnoreCase("AvatarState")) {
			new AvatarFloat(user);
		}
	}
}
