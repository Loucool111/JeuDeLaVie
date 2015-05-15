package ch.reaamz.jeudelavie;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Utils 
{
	public static final String PLUGIN_NAME = "JDLV";
	
	public static final Material CLIC_ITEM = Material.WOOD_SWORD;
	public static final Material GAME_BLOCK = Material.LAPIS_BLOCK;
	public static final Material BASE_BLOCK = Material.STONE;
	
	public static void sendCustomMessageAllPlayers(String message)
	{
		for(Player p : Bukkit.getServer().getOnlinePlayers())
		{
			p.sendMessage(ChatColor.GOLD + "[" + PLUGIN_NAME + "] " + ChatColor.RESET + message);
		}
	}
	
	public static void sendMessageAllPlayers(String message)
	{
		for(Player p : Bukkit.getServer().getOnlinePlayers())
		{
			p.sendMessage(message);
		}
	}
	
	public static void sendCustomMessage(Player p , String message)
	{
		p.sendMessage(ChatColor.GOLD + "[" + PLUGIN_NAME + "] " + ChatColor.RESET + message);
	}
}
