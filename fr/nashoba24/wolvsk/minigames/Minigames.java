package fr.nashoba24.wolvsk.minigames;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import fr.nashoba24.wolvsk.WolvSK;

public class Minigames implements Listener {
	
	static ArrayList<Minigame> arr = new ArrayList<Minigame>();
	static HashMap<String, Minigame> map = new HashMap<String, Minigame>();
	static HashMap<Player, Minigame> games = new HashMap<Player, Minigame>();
	static HashMap<String, Minigame> commands = new HashMap<String, Minigame>();
	static HashMap<String, Integer> lvl = new HashMap<String, Integer>();
	static HashMap<String, ItemStack[]> inv = new HashMap<String, ItemStack[]>();
	static HashMap<String, Location> ancLoc = new HashMap<String, Location>();
	static boolean init = false;
	
	public static Minigame createMinigame(String name, String command, String prefix) {
		name = name.replaceAll(" ", "-");
		command = command.replaceAll(" ", "-");
		if(map.containsKey(name)) {
			return null;
		}
		if(!init) {
			init = true;
			Minigames.init();
		}
		Minigame mg = new Minigame(name, command.toLowerCase(), prefix);
		arr.add(mg);
		map.put(name, mg);
		commands.put(command.toLowerCase(), mg);
		return mg;
	}
	
	public static Minigame getByName(String name) {
		if(map.containsKey(name)) {
			return map.get(name);
		}
		else {
			return null;
		}
	}
	
	public static Minigame[] getAllMinigames() {
		if(arr.size()==0) {
			return new Minigame[] {};
		}
		Minigame[] list = new Minigame[arr.size()];
		list = arr.toArray(list);
		return list;
	}
	
	public static boolean start(Minigame mg, Arena arena, boolean force) {
		if(!force) {
			if(arena.playersCount()>=arena.getMin()) {
				if(arena.getMinigame()==mg) {
					arena.setStarted(true);
					WolvSK.getInstance().getServer().getPluginManager().callEvent(new ArenaStartEvent(mg, arena));
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			if(arena.getMinigame()==mg) {
				arena.setStarted(true);
				WolvSK.getInstance().getServer().getPluginManager().callEvent(new ArenaStartEvent(mg, arena));
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public static boolean stop(Minigame mg, Arena arena) {
		if(arena.getMinigame()==mg) {
			arena.setStarted(false);
			Player[] list = arena.getAllPlayers();
			for(Player p : list) {
				Minigames.leave(p, true, false, null);
			}
			WolvSK.getInstance().getServer().getPluginManager().callEvent(new ArenaStopEvent(mg, arena));
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean join(Player p, Minigame mg, Arena arena) {
		if(arena.getMinigame()!=mg) {
			return false;
		}
		if(Minigames.inGame(p)) {
			return false;
		}
		else {
			if(arena.isStarted()) {
				return false;
			}
			else {
				if(arena.playersCount()>=arena.getMax()) {
					return false;
				}
				else {
					mg.setArena(p, arena);
					arena.addPlayer(p);
					games.put(p, mg);
					lvl.put(p.getName(), p.getLevel());
					inv.put(p.getName(), p.getInventory().getContents());
					ancLoc.put(p.getName(), p.getLocation());
					p.getInventory().clear();
					p.updateInventory();
					if(arena.getLobby()!=null) {
						Location loc = arena.getLobby();
						loc.setPitch(0);
						loc.setYaw(0);
						p.teleport(loc);
					}
					WolvSK.getInstance().getServer().getPluginManager().callEvent(new PlayerJoinArenaEvent(mg, arena, p));
					return true;
				}
			}
		}
	}
	
	public static boolean leave(Player p, boolean force, boolean msg, String prefix) {
		if(!force) {
			if(!Minigames.inGame(p)) {
				if(msg) {
					p.sendMessage(ChatColor.RED + "[" + prefix + "] You are not in a game!");
				}
				return false;
			}
			else {
				if(Minigames.getMinigame(p).getArena(p).isStarted()) {
					if(msg) {
						p.sendMessage(ChatColor.RED + "[" + prefix + "] The game is started!");
					}
					return false;
				}
				else {
					WolvSK.getInstance().getServer().getPluginManager().callEvent(new PlayerLeaveArenaEvent(Minigames.getMinigame(p), Minigames.getMinigame(p).getArena(p), p));
					Minigames.getMinigame(p).getArena(p).removePlayer(p);
					Minigames.getMinigame(p).setArena(p, null);
					games.remove(p);
					if(lvl.containsKey(p.getName())) {
						p.setLevel(lvl.get(p.getName()));
					}
					if(inv.containsKey(p.getName())) {
						p.getInventory().setContents(inv.get(p.getName()));
						p.updateInventory();
					}
					if(ancLoc.containsKey(p.getName())) {
						p.teleport(ancLoc.get(p.getName()));
					}
					return true;
				}
			}
		}
		else {
			if(Minigames.inGame(p)) {
				WolvSK.getInstance().getServer().getPluginManager().callEvent(new PlayerLeaveArenaEvent(Minigames.getMinigame(p), Minigames.getMinigame(p).getArena(p), p));
				Minigames.getMinigame(p).getArena(p).removePlayer(p);
				Minigames.getMinigame(p).setArena(p, null);
				games.remove(p);
				if(lvl.containsKey(p.getName())) {
					p.setLevel(lvl.get(p.getName()));
				}
				if(inv.containsKey(p.getName())) {
					p.getInventory().setContents(inv.get(p.getName()));
					p.updateInventory();
				}
				if(ancLoc.containsKey(p.getName())) {
					p.teleport(ancLoc.get(p.getName()));
				}
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public static boolean inGame(Player p) {
		if(games.containsKey(p)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Minigame getMinigame(Player p) {
		if(games.containsKey(p)) {
			return games.get(p);
		}
		else {
			return null;
		}
	}
	
	public static void init() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(WolvSK.getInstance(), new Runnable() {
			@Override
			public void run() {
				Minigame[] list = Minigames.getAllMinigames();
				for(Minigame mg : list) {
					Arena[] list2 = mg.getArenas();
					for(Arena a : list2) {
						if(!a.isStarted()) {
							a.timer();
						}
					}
				}
			}
		}, 0L, 20L);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(Minigames.inGame(p)) {
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(Minigames.inGame(p)) {
			e.setCancelled(true);
			Minigames.getMinigame(p).getArena(p).broadcast(p.getDisplayName() + " > " + e.getMessage());
			System.out.println("[Arena: " + Minigames.getMinigame(p).getArena(p).getName() + ", Minigame: " + Minigames.getMinigame(p).getName() + "] " + p.getName() + " > " + e.getMessage());
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		String[] list = e.getMessage().split(" ");
		final Player p = e.getPlayer();
		if(commands.containsKey(list[0].replaceFirst("/", "").toLowerCase())) {
			e.setCancelled(true);
			String cmd = list[0].replaceFirst("/", "").toLowerCase();
			String[] args = new String[list.length - 1];
			Integer i = 0;
			boolean first = true;
			for(String s : list) {
				if(!first) {
					args[i] = s.toLowerCase();
					++i;
				}
				else {
					first = false;
				}
			}
			Minigame mg = commands.get(cmd);
			if(args.length==0) {
				p.sendMessage(ChatColor.AQUA + "----- " + mg.getFullPrefix() + ChatColor.RESET + ChatColor.AQUA + " -----");
				if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " join <arena>");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".leave") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " leave");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " create <arena> <min> <max>");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".setlobby") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setlobby <arena>");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".setmin") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmin <arena> <min>");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".setmax") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmax <arena> <min>");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".list") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " list");
				}
			}
			else if(args.length==1) {
				if(args[0].equals("leave")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".leave") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						boolean s = Minigames.leave(p, false, true, mg.getPrefix());
						if(s) {
							p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] You left the game!");
							Minigames.getMinigame(p).getArena(p).broadcast(mg.getFullPrefix() + " " + ChatColor.GOLD + p.getName() + ChatColor.AQUA + " left the game!");
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				if(args[0].equals("join")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " join <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("create")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " create <arena> <min> <max>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("setlobby")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setlobby") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setlobby <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("setmin")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmin") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmin <arena> <min>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("setmax")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmax") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmax <arena> <min>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("list")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".list") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						Arena[] l = mg.getArenas();
						ArrayList<String> f = new ArrayList<String>();
						for(Arena a : l) {
							f.add(a.getName());
						}
						p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] All arenas avaibles: " + ChatColor.GOLD + String.join(ChatColor.GREEN + ", " + ChatColor.GOLD, f));
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("help")) {
					WolvSK.getInstance().getServer().dispatchCommand(p, cmd);
				}
				else {
					WolvSK.getInstance().getServer().dispatchCommand(p, cmd);
				}
			}
			else if(args.length==2) {
				if(args[0].equals("join")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						if(!Minigames.inGame(p)) {
							boolean success = Minigames.join(p, mg, mg.getArena(args[1]));
							if(success) {
								mg.getArena(args[1]).broadcast(mg.getFullPrefix() + ChatColor.GOLD + " " + p.getName() + ChatColor.AQUA + " joined the game!");
							}
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("create")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " create <arena> <min> <max>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("setlobby")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setlobby") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1])!=null) {
							mg.getArena(args[1]).setLobby(p.getLocation());
							p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] The lobby of the arena " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " has been set to your location!");
						}
						else {
							p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The arena " + args[1] + " doesn't exists!");
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("setmin")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmin") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmin <arena> <min>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("setmax")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmax") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmax <arena> <min>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else {
					WolvSK.getInstance().getServer().dispatchCommand(p, cmd);
				}
			}
			else if(args.length==3) {
				if(args[0].equals("join")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " join <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("create")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " create <arena> <min> <max>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("setlobby")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setlobby") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setlobby <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("setmin")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmin") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1])!=null) {
							if(isInteger(args[2])) {
								Integer min = Integer.parseInt(args[2]);
								if(mg.getArena(args[1]).getMax()>=min) {
									mg.getArena(args[1]).setMin(min);
									p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] The minimum of player of the arena " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " is now " +  ChatColor.DARK_GREEN + min + ChatColor.GREEN + "!");
								}
								else {
									p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The minimum of player cannot be greater than the maximum of player");
								}
							}
							else {
								p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The minimum of player must be an integer!");
							}
						}
						else {
							p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The arena " + args[1] + " doesn't exists!");
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("setmax")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmax") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1])!=null) {
							if(isInteger(args[2])) {
								Integer max = Integer.parseInt(args[2]);
								if(max>=mg.getArena(args[1]).getMin()) {
									mg.getArena(args[1]).setMax(max);
									p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] The maximum of player of the arena " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " is now " +  ChatColor.DARK_GREEN + max + ChatColor.GREEN + "!");
								}
								else {
									p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The maximum of player cannot be smaller than the minimum of player");
								}
							}
							else {
								p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The maximum of player must be an integer!");
							}
						}
						else {
							p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The arena " + args[1] + " doesn't exists!");
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else {
					WolvSK.getInstance().getServer().dispatchCommand(p, cmd);
				}
			}
			else if(args.length==4) {
				if(args[0].equals("join")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " join <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("setlobby")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setlobby") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setlobby <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("create")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1])==null) {
							if(isInteger(args[2])) {
								if(isInteger(args[3])) {
									Integer min = Integer.parseInt(args[2]);
									Integer max = Integer.parseInt(args[3]);
									if(min>max) {
										p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The minimum of player cannot be greater than the maximum of player");
										return;
									}
									mg.addArena(new Arena(mg, args[1], min, max));
									p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] The arena " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " has been created!");
								}
								else {
									p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The maximum of player must be an integer!");
								}
							}
							else {
								p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The minimum of player must be an integer!");
							}
						}
						else {
							p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] An arena with the name " + ChatColor.DARK_RED +  args[1] + ChatColor.RED + " already exists!");
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else {
					WolvSK.getInstance().getServer().dispatchCommand(p, cmd);
				}
			}
			else {
				if(args[0].equals("join")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " join <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equals("create")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " create <arena> <min> <max>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else {
					WolvSK.getInstance().getServer().dispatchCommand(p, cmd);
				}
			}
		}
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
	
	public static void saveAll() {
		createFolder();
		Minigame[] list = Minigames.getAllMinigames();
		for(Minigame mg : list) {
			save(mg);
		}
	}
	
	public static void save(Minigame mg) {
		createFolder();
		File file = new File(WolvSK.getInstance().getDataFolder() + "/minigames.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection section = config.createSection(mg.getName());
		section.set("name", mg.getName());
		section.set("prefix", mg.getPrefix());
		section.set("command", mg.getCommand());
		Arena[] list = mg.getArenas();
		for(Arena a : list) {
			ConfigurationSection section2 = section.createSection(a.getName());
			section2.set("name", a.getName());
			section2.set("max", a.getMax());
			section2.set("min", a.getMin());
			section2.set("lobby.x", a.getLobby().getBlockX());
			section2.set("lobby.y", a.getLobby().getBlockY());
			section2.set("lobby.z", a.getLobby().getBlockZ());
			section2.set("lobby.world", a.getLobby().getWorld().getName());
		}
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void load() {
		File file = new File(WolvSK.getInstance().getDataFolder() + "/minigames.yml");
		if(file.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			Set<String> mgs = config.getKeys(false);
			for(String mg : mgs) {
				ConfigurationSection section = config.getConfigurationSection(mg);
				if(section.isSet("name") && section.isSet("prefix") && section.isSet("command")) {
					Minigame minigame = Minigames.createMinigame(section.getString("name"), section.getString("command"), section.getString("prefix"));
					Set<String> arenas = section.getKeys(false);
					for(String a : arenas) {
						ConfigurationSection section2 = config.getConfigurationSection(a);
						if(section2.isSet("name") && section2.isSet("max") && section2.isSet("min")) {
							Arena arena = new Arena(minigame, section.getString("name"), section.getInt("min"), section.getInt("max"));
							minigame.addArena(arena);
							if(section2.isSet("lobby.x") && section2.isSet("lobby.y") && section2.isSet("lobby.z") && section2.isSet("lobby.world")) {
								World w = WolvSK.getInstance().getServer().getWorld(section2.getString("lobby.world"));
								if(w!=null) {
									arena.setLobby(new Location(w, section2.getInt("lobby.x"), section2.getInt("lobby.y"), section2.getInt("lobby.z")));
								}
							}
						}
					}
				}
			}
		}
	}
	
	static void createFolder() {
		File file = new File(WolvSK.getInstance().getDataFolder() + "/");
		if(!file.exists()) {
			file.mkdir();
		}
		file = new File(WolvSK.getInstance().getDataFolder() + "/minigames.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
