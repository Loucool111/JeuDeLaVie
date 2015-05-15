package ch.reaamz.jeudelavie.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import ch.reaamz.jeudelavie.Utils;
import ch.reaamz.jeudelavie.game.Game;

public class ClicListener implements Listener
{
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getPlayer().getInventory().getItemInHand() != null)
		{
			if (event.getPlayer().getInventory().getItemInHand().getType().equals(Utils.CLIC_ITEM))
			{
				if (event.getAction().equals(Action.LEFT_CLICK_BLOCK))
				{
					Block b = event.getClickedBlock();
					Game.point1 = b;
					Utils.sendCustomMessage(event.getPlayer(), ChatColor.AQUA + "Le point n°1 à été défini ! X: " + b.getX() + " Y: " + b.getY() + " Z: " + b.getZ());
				}
				if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
				{
					Block b =event.getClickedBlock();
					Game.point2 = b;
					Utils.sendCustomMessage(event.getPlayer(), ChatColor.AQUA + "Le point n°2 à été défini ! X: " + b.getX() + " Y: " + b.getY() + " Z: " + b.getZ());
				}
			}
		}		
	}
}
