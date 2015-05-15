package ch.reaamz.jeudelavie.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.reaamz.jeudelavie.Utils;
import ch.reaamz.jeudelavie.game.Game;

public class JDLVCommandExecutor implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player)sender;
			if (cmd.getName().equals("jdlv"))
			{
				if (args.length < 1) return false;
				
				if (args[0].equals("start"))
				{
					if (!(Game.point1 == null) && !(Game.point2 == null) && !Game.isStarted)
					{
						Game.init();
						Game.start();
						Utils.sendCustomMessage(player, ChatColor.AQUA + "Le jeu à démarré !");
					}
					else
						Utils.sendCustomMessage(player, ChatColor.RED + "Certains points n'ont pas été définis ou le jeu à déjà été démmaré !");
					
					return true;
				}
				
				if (args[0].equals("stop"))
				{
					boolean canStop = Game.stop();
					
					if (!canStop)
						Utils.sendCustomMessage(player, ChatColor.RED + "Vous ne pouvez stopper sans avoir démarrer !");
					else
						Utils.sendCustomMessage(player, ChatColor.AQUA + "Le jeu est arreté");
					
					return true;
				}

				if (args[0].equals("settime"))
				{
					if (args.length < 2) return false;
					
					long periodTemp = 0;
					
					try 
					{
						periodTemp = Long.parseLong(args[1]);
						Game.stop();
					}
					catch (NumberFormatException ex) 
					{
						ex.getStackTrace();
						periodTemp = Game.period;
						Utils.sendCustomMessage(player, ChatColor.RED + "La periode doit être un nombre !");
					}
					finally
					{
						Game.period = periodTemp;
						Game.init();
						Game.start();
					}
					
					return true;
				}
			}
		}
		return false;
	}
}
