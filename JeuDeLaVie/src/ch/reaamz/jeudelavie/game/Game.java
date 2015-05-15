package ch.reaamz.jeudelavie.game;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import ch.reaamz.jeudelavie.JeuDeLaVie;
import ch.reaamz.jeudelavie.Utils;

import com.google.common.collect.Maps;

public class Game 
{
	public static long period = 20L;
	
	public static Block point1;
	public static Block point2;
	
	public static Block[][] blocks;
	
	private static int id = -1;
	
	public static boolean isStarted = false;
	
	private static boolean isSurLAxeX = false;
	
	private static boolean isNegativeAxe = false;
	private static boolean isNegativeHauteur = false;
	
	public static void init()
	{
		int diffAxe;
		
		int diffHauteur = point1.getY() - point2.getY();
		
		if (point1.getZ() == point2.getZ())
			isSurLAxeX = true;
		
		if (isSurLAxeX)
			diffAxe = point1.getX() - point2.getX();
		else
			diffAxe = point1.getZ() - point2.getZ();

		if (diffAxe < 0)
		{
			diffAxe *= -1;
			isNegativeAxe = true;
		}
		
		if (diffHauteur < 0)
		{
			diffHauteur *= -1;
			isNegativeHauteur = true;
		}
		
		blocks = new Block[diffAxe + 1][diffHauteur + 1];
		
		blocks[0][0] = point1;
		blocks[diffAxe][diffHauteur] = point2;
		
		for (int da = 0; da <= diffAxe; da++)
		{
			for (int dh = 0; dh <= diffHauteur; dh++)
			{
				if (isSurLAxeX && !isNegativeAxe && !isNegativeHauteur)
				{
					blocks[da][dh] = point1.getWorld().getBlockAt(point1.getX() - da, point1.getY() - dh, point1.getZ());
				}
				if (!isSurLAxeX && !isNegativeAxe && !isNegativeHauteur)
				{
					blocks[da][dh] = point1.getWorld().getBlockAt(point1.getX(), point1.getY() - dh, point1.getZ() - da);
				}
				if (isSurLAxeX && isNegativeAxe && !isNegativeHauteur)
				{
					blocks[da][dh] = point1.getWorld().getBlockAt(point1.getX() + da, point1.getY() - dh, point1.getZ());
				}
				if (!isSurLAxeX && isNegativeAxe && !isNegativeHauteur)
				{
					blocks[da][dh] = point1.getWorld().getBlockAt(point1.getX(), point1.getY() - dh, point1.getZ() + da);
				}
				
				if (isSurLAxeX && !isNegativeAxe && isNegativeHauteur)
				{
					blocks[da][dh] = point1.getWorld().getBlockAt(point1.getX() - da, point1.getY() + dh, point1.getZ());
				}
				if (!isSurLAxeX && !isNegativeAxe && isNegativeHauteur)
				{
					blocks[da][dh] = point1.getWorld().getBlockAt(point1.getX(), point1.getY() + dh, point1.getZ() - da);
				}
				if (isSurLAxeX && isNegativeAxe && isNegativeHauteur)
				{
					blocks[da][dh] = point1.getWorld().getBlockAt(point1.getX() + da, point1.getY() + dh, point1.getZ());
				}
				if (!isSurLAxeX && isNegativeAxe && isNegativeHauteur)
				{
					blocks[da][dh] = point1.getWorld().getBlockAt(point1.getX(), point1.getY() + dh, point1.getZ() + da);
				}
			}
		}
		
		isNegativeAxe = false;
		isNegativeHauteur = false;
	}
	
	public static void start()
	{
		if (isStarted)
		{
			return;
		}
		
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(JeuDeLaVie.instance, new Runnable()
		{
			HashMap<Block, Boolean> isGoingToDie = Maps.newHashMap();
			HashMap<Block, Boolean> isGoingToCreateNewOne = Maps.newHashMap();
			
			@Override
			public void run() 
			{
				for (Block[] bloc : blocks)
				{
					for (Block blc : bloc)
					{
						isGoingToDie.put(blc, false);
						isGoingToCreateNewOne.put(blc, false);
						
						if (blc.getType().equals(Utils.GAME_BLOCK) && (getNeibours(blc) < 2 || getNeibours(blc) > 3))
						{
							isGoingToDie.put(blc, true);
						}
						
						if (blc.getType().equals(Utils.BASE_BLOCK) && getNeibours(blc) == 3)
						{
							isGoingToCreateNewOne.put(blc, true);
						}
						
						if (blc.getLocation().equals(point2.getLocation()))
						{
							moveBlocks(isGoingToDie, isGoingToCreateNewOne);
						}
					}
				}
			}
		}, 0L, period);
		
		isStarted = true;
	}
	
	public static boolean stop()
	{
		if (id != -1)
		{
			Bukkit.getScheduler().cancelTask(id);
			id = -1;
			isStarted = false;
			return true;
		}
		else
			return false;
	}
	
	private static void moveBlocks(HashMap<Block, Boolean> isGoingToDie, HashMap<Block, Boolean> isGoingToCreateNewOne)
	{
		for (Entry<Block, Boolean> dead : isGoingToDie.entrySet())
		{
			if (dead.getValue())
			{
				dead.getKey().setType(Utils.BASE_BLOCK);
			}
		}
		
		for(Entry<Block, Boolean> newOne : isGoingToCreateNewOne.entrySet())
		{
			if (newOne.getValue())
			{
				newOne.getKey().setType(Utils.GAME_BLOCK);
			}
		}
	}
	
	private static int getNeibours(Block block)
	{
		int counter = 0;
		
		Block b3;
		Block b4;
		
		if (block.getRelative(BlockFace.UP).getType() == Utils.GAME_BLOCK)
			counter++;
		
		if (block.getRelative(BlockFace.DOWN).getType() == Utils.GAME_BLOCK)
			counter++;
		
		if (isSurLAxeX)
		{
			if (block.getRelative(BlockFace.EAST).getType() == Utils.GAME_BLOCK)
				counter++;
			
			if (block.getRelative(BlockFace.WEST).getType() == Utils.GAME_BLOCK)
				counter++;
			
			b3 = point1.getWorld().getBlockAt(block.getRelative(BlockFace.EAST).getX(), block.getRelative(BlockFace.EAST).getY(), block.getRelative(BlockFace.EAST).getZ());
			b4 = point1.getWorld().getBlockAt(block.getRelative(BlockFace.WEST).getX(), block.getRelative(BlockFace.WEST).getY(), block.getRelative(BlockFace.WEST).getZ());
			
			if (b3.getRelative(BlockFace.UP).getType() == Utils.GAME_BLOCK)
				counter++;
			
			if (b3.getRelative(BlockFace.DOWN).getType() == Utils.GAME_BLOCK)
				counter++;
			
			if (b4.getRelative(BlockFace.UP).getType() == Utils.GAME_BLOCK)
				counter++;
			
			if (b4.getRelative(BlockFace.DOWN).getType() == Utils.GAME_BLOCK)
				counter++;
		}
		else
		{
			if (block.getRelative(BlockFace.NORTH).getType() == Utils.GAME_BLOCK)
				counter++;
			
			if (block.getRelative(BlockFace.SOUTH).getType() == Utils.GAME_BLOCK)
				counter++;
			
			b3 = point1.getWorld().getBlockAt(block.getRelative(BlockFace.NORTH).getX(), block.getRelative(BlockFace.NORTH).getY(), block.getRelative(BlockFace.NORTH).getZ());
			b4 = point1.getWorld().getBlockAt(block.getRelative(BlockFace.SOUTH).getX(), block.getRelative(BlockFace.SOUTH).getY(), block.getRelative(BlockFace.SOUTH).getZ());
			
			if (b3.getRelative(BlockFace.UP).getType() == Utils.GAME_BLOCK)
				counter++;
			
			if (b3.getRelative(BlockFace.DOWN).getType() == Utils.GAME_BLOCK)
				counter++;
			
			if (b4.getRelative(BlockFace.UP).getType() == Utils.GAME_BLOCK)
				counter++;
			
			if (b4.getRelative(BlockFace.DOWN).getType() == Utils.GAME_BLOCK)
				counter++;
		}
		return counter;
	}
}
