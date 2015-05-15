package ch.reaamz.jeudelavie;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.reaamz.jeudelavie.commands.JDLVCommandExecutor;
import ch.reaamz.jeudelavie.completers.JDLVTabCompleter;
import ch.reaamz.jeudelavie.listeners.ClicListener;

public class JeuDeLaVie extends JavaPlugin
{
	//TODO Empecher le double start
	public static Plugin instance;
	
	@Override
	public void onEnable()
	{
		instance = this;
		
		getLogger().info(Utils.PLUGIN_NAME + " Loaded");
		
		JDLVCommandExecutor executor = new JDLVCommandExecutor();
		JDLVTabCompleter completer = new JDLVTabCompleter();
		
		getCommand("jdlv").setExecutor(executor);
		getCommand("jdlv").setTabCompleter(completer);
		
		Bukkit.getPluginManager().registerEvents(new ClicListener(), this);
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info(Utils.PLUGIN_NAME + " Unloaded");
	}
}
