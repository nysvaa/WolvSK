package fr.nashoba24.wolvsk.minigames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Minigame {

	private String mgname;
	private ArrayList<Arena> arenas = new ArrayList<Arena>();
	private static HashMap<String, Arena> players = new HashMap<String, Arena>();
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
	
	public boolean addArena(Arena arena, boolean save) {
		if(arenas.contains(arena)) {
			return false;
		}
		for(Arena a : this.getArenas()) {
			if(a.getName().equals(arena.getName())) {
				return false;
			}
		}
		if(arena.getMinigame()==this) {
			arenas.add(arena);
			if(save) {
				Minigames.save(arena.getMinigame());
			}
			arena.updateSigns();
			return true;
		}
		else {
			return false;
		}
	}
	
	public void removeArena(Arena arena, boolean save) {
		if(arenas.contains(arena)) {
			arenas.remove(arena);
			if(save) {
				Minigames.save(arena.getMinigame());
			}
		}
	}
	
	public Arena getArena(String name, boolean ignoreCase) {
		if(ignoreCase) {
			for(Arena a : arenas) {
				if(a.getName().equalsIgnoreCase(name)) {
					return a;
				}
			}
		}
		else {
			for(Arena a : arenas) {
				if(a.getName().equals(name)) {
					return a;
				}
			}
		}
		return null;
	}
	
	public Arena getArena(Player p) {
		if(players.containsKey(p.getName())) {
			return players.get(p.getName());
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
			players.remove(p.getName());
			return;
		}
		players.put(p.getName(), arena);
	}
}
