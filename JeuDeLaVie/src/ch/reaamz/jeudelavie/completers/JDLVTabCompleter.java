package ch.reaamz.jeudelavie.completers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class JDLVTabCompleter implements TabCompleter
{
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) 
	{
		return Arrays.asList("start","stop","settime");
	}
}
