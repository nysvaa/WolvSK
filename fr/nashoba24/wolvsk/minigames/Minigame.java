package fr.nashoba24.wolvsk.minigames;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Minigame {

	private String mgname;
	private ArrayList<Arena> arenas = new ArrayList<Arena>();
	private static HashMap<Player, Arena> players = new HashMap<Player, Arena>();
	private String cmd;
	private String mgprefix;
	
	public Minigame(String name, String command, String prefix) {
		mgname = name;
		cmd = command.toLowerCase();
		mgprefix = prefix;
	}
	
	public String getName() {
		return mgname;
	}
	
	public Arena[] getArenas() {
		if(arenas.size()==0) {
			return new Arena[]{};
		}
		Arena[] list = new Arena[arenas.size()];
		list = arenas.toArray(list);
		return list;
	}
	
	public boolean addArena(Arena arena) {
		if(arenas.contains(arena)) {
			return false;
		}
		if(arena.getMinigame()==this) {
			arenas.add(arena);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Arena getArena(String name) {
		for(Arena a : arenas) {
			if(a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}
	
	public Arena getArena(Player p) {
		if(players.containsKey(p)) {
			return players.get(p);
		}
		else {
			return null;
		}
	}
	
	public String getCommand() {
		return cmd;
	}
	
	public String getPrefix() {
		return mgprefix;
	}
	
	public String getFullPrefix() {
		return ChatColor.RESET + "" + ChatColor.AQUA + "[" + mgprefix + ChatColor.RESET + ChatColor.AQUA + "]";
	}
	
	public void setArena(Player p, Arena arena) {
		if(arena==null) {
			players.remove(p);
			return;
		}
		players.put(p, arena);
	}
}
