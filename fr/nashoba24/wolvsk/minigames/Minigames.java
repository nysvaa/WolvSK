package fr.nashoba24.wolvsk.minigames;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import fr.nashoba24.wolvsk.WolvSK;

public class Minigames implements Listener, CommandExecutor {
	
	static ArrayList<Minigame> arr = new ArrayList<Minigame>();
	static HashMap<String, Minigame> map = new HashMap<String, Minigame>();
	static HashMap<String, Minigame> commands = new HashMap<String, Minigame>();
	static HashMap<String, Minigame> games = new HashMap<String, Minigame>();
	static HashMap<String, Integer> lvl = new HashMap<String, Integer>();
	static HashMap<String, Float> exp = new HashMap<String, Float>();
	static HashMap<String, ItemStack[]> inv = new HashMap<String, ItemStack[]>();
	static HashMap<String, Location> ancLoc = new HashMap<String, Location>();
	public static String chatFormat = "%player% > %message%";
	public static String messageFormat = "&6[%minigame%] &r&b%message%";
	static boolean init = false;
	public static String xSecsLeft = "The game will start in %secs% seconds!";
	public static String OneSecLeft = "The game will start in 1 second!";
	public static String playerJoined = "%player% joined the game!";
	public static String playerLeft = "%player% left the game!";
	public static boolean customChatFormat = true;
	
	public static Minigame createMinigame(String name, String command, String prefix, boolean save) {
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
		if(save) {
			Minigames.save(mg);
		}
		return mg;
	}
	
	public static void removeMinigame(String name, boolean save) {
		name = name.replaceAll(" ", "-");
		if(!map.containsKey(name)) {
			return;
		}
		Minigame mg = Minigames.getByName(name);
		for(Arena a : mg.getArenas()) {
			Minigames.stop(mg, a);
		}
		arr.remove(name);
		commands.remove(mg.getCommand());
		if(save) {
			createFolder();
			File file = new File(WolvSK.getInstance().getDataFolder() + "/minigames.yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			if(config.isSet(mg.getName())) {
				config.set(mg.getName(), null);
			}
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
					for(Player p : arena.getAllPlayers()) {
						p.setLevel(0);
						p.setExp(0F);
					}
					WolvSK.getInstance().getServer().getPluginManager().callEvent(new ArenaStartEvent(mg, arena));
					arena.updateSigns();
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
				arena.updateSigns();
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
			arena.updateSigns();
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean join(Player p, Minigame mg, Arena arena, boolean message) {
		if(arena.getMinigame()!=mg) {
			return false;
		}
		if(Minigames.inGame(p)) {
			if(message) {
				p.sendMessage(Minigames.getMessage("You are already in an arena!", mg.getPrefix(), true));
			}
			return false;
		}
		else {
			if(arena.isStarted()) {
				if(message) {
					p.sendMessage(Minigames.getMessage("This arena has started!", mg.getPrefix(), true));
				}
				return false;
			}
			else {
				if(arena.playersCount()>=arena.getMax()) {
					if(!p.hasPermission("wolvsk.vip.kick")) {
						if(message) {
							p.sendMessage(Minigames.getMessage("This arena is full!", mg.getPrefix(), true));
						}
						return false;
					}
					Player[] list = arena.getAllPlayers();
					ArrayList<Player> list2 = new ArrayList<Player>();
					for(Player pl : list) {
						if(pl.hasPermission("wolvsk.vip.kick")) {
							list2.add(pl);
						}
					}
					if(list2.size()>0) {
						Collections.shuffle(list2);
						Player pl = list2.get(0);
						leave(pl, true, false, null);
					}
					else {
						if(message) {
							p.sendMessage(Minigames.getMessage("You can't join this arena because there is no player that can be kicked!", mg.getPrefix(), true));
						}
						return false;
					}
				}
				mg.setArena(p, arena);
				arena.addPlayer(p);
				games.put(p.getName(), mg);
				lvl.put(p.getName(), p.getLevel());
				exp.put(p.getName(), p.getExp());
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
				arena.updateSigns();
				return true;
			}
		}
	}
	
	public static boolean leave(Player p, boolean force, boolean msg, String prefix) {
		if(!force) {
			if(!Minigames.inGame(p)) {
				if(msg) {
					p.sendMessage(Minigames.getMessage("You are not in a game!", prefix, true));
				}
				return false;
			}
			else {
				if(Minigames.getMinigame(p).getArena(p).isStarted()) {
					if(msg) {
						p.sendMessage(Minigames.getMessage("The game is started!", prefix, true));
					}
					return false;
				}
				else {
					Arena arena = Minigames.getMinigame(p).getArena(p);
					WolvSK.getInstance().getServer().getPluginManager().callEvent(new PlayerLeaveArenaEvent(Minigames.getMinigame(p), Minigames.getMinigame(p).getArena(p), p));
					Minigames.getMinigame(p).getArena(p).removePlayer(p);
					Minigames.getMinigame(p).setArena(p, null);
					games.remove(p.getName());
					if(lvl.containsKey(p.getName())) {
						p.setLevel(lvl.get(p.getName()));
					}
					if(exp.containsKey(p.getName())) {
						p.setExp(exp.get(p.getName()));
					}
					if(inv.containsKey(p.getName())) {
						p.getInventory().setContents(inv.get(p.getName()));
						p.updateInventory();
					}
					if(ancLoc.containsKey(p.getName())) {
						p.teleport(ancLoc.get(p.getName()));
					}
					arena.updateSigns();
					return true;
				}
			}
		}
		else {
			if(Minigames.inGame(p)) {
				Arena arena = Minigames.getMinigame(p).getArena(p);
				WolvSK.getInstance().getServer().getPluginManager().callEvent(new PlayerLeaveArenaEvent(Minigames.getMinigame(p), Minigames.getMinigame(p).getArena(p), p));
				Minigames.getMinigame(p).getArena(p).removePlayer(p);
				Minigames.getMinigame(p).setArena(p, null);
				if(msg) {
					String m = playerLeft.replaceAll("%player%", p.getName()).replaceAll("%min%", arena.getMin().toString()).replaceAll("%max%", arena.getMax().toString()).replaceAll("%players count%", String.valueOf(arena.getAllPlayers().length));
					arena.broadcast(Minigames.getMessage(m, arena.getMinigame().getPrefix(), false));
				}
				games.remove(p.getName());
				if(lvl.containsKey(p.getName())) {
					p.setLevel(lvl.get(p.getName()));
				}
				if(exp.containsKey(p.getName())) {
					p.setExp(exp.get(p.getName()));
				}
				if(inv.containsKey(p.getName())) {
					p.getInventory().setContents(inv.get(p.getName()));
					p.updateInventory();
				}
				if(ancLoc.containsKey(p.getName())) {
					p.teleport(ancLoc.get(p.getName()));
				}
				arena.updateSigns();
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public static boolean inGame(Player p) {
		if(games.containsKey(p.getName())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Minigame getMinigame(Player p) {
		if(games.containsKey(p.getName())) {
			return games.get(p.getName());
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
							if(a.getAllPlayers().length!=0) {
								a.countdown();
							}
						}
						else {
							a.finish();
						}
					}
				}
			}
		}, 0L, 20L);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if(ancLoc.containsKey(e.getPlayer().getName())) {
			e.getPlayer().teleport(ancLoc.get(e.getPlayer().getName()));
		}
		Minigames.leave(e.getPlayer(), true, true, null);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(Minigames.inGame(p)) {
			if(Minigames.customChatFormat) {
				e.setCancelled(true);
				Minigames.getMinigame(p).getArena(p).chat(p, e.getMessage());
			}
			else {
				e.getRecipients().clear();
				for(Player pl : WolvSK.getInstance().getServer().getOnlinePlayers()){
					if(Minigames.getMinigame(pl)!=null) {
						if(Minigames.getMinigame(pl).getArena(pl).getName().equals(Minigames.getMinigame(p).getArena(p).getName()) && Minigames.getMinigame(pl).getCommand().equals(Minigames.getMinigame(p).getCommand())) {
							e.getRecipients().add(pl);
						}
					}
				}
			}
			System.out.println("[Arena: " + Minigames.getMinigame(p).getArena(p).getName() + ", Minigame: " + Minigames.getMinigame(p).getName() + "] " + p.getName() + " > " + e.getMessage());
		}
		else {
			for(Player pl : WolvSK.getInstance().getServer().getOnlinePlayers()){
				if(Minigames.inGame(pl)) {
					e.getRecipients().remove(pl);
				}
			}
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(Minigames.inGame(e.getPlayer())) {
			if(!Minigames.getMinigame(e.getPlayer()).getArena(e.getPlayer()).isStarted()) {
				e.setCancelled(true);
			}
		}
		if(!e.isCancelled() && (e.getBlock().getType()==Material.WALL_SIGN || e.getBlock().getType()==Material.SIGN_POST)) {
			Sign sign = (Sign) e.getBlock().getState();
			Player p = e.getPlayer();
			if(sign.getLine(0).startsWith(ChatColor.GREEN + "[") && sign.getLine(0).endsWith("]")) {
				Minigame mg = null;
				for(Minigame m : Minigames.getAllMinigames()) {
					if(sign.getLine(0).equals(ChatColor.GREEN + "[" + m.getPrefix() + "]")) {
						mg = m;
						break;
					}
				}
				if(mg==null) {
					return;
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".admin") || p.hasPermission("wolvsk." + mg.getName() + ".sign")) {
					Arena a = null;
					for(Arena a2 : mg.getArenas()) {
						if(sign.getLine(1).equals(a2.getName())) {
							a = a2;
							break;
						}
					}
					if(a==null) {
						return;
					}
					a.removeSign(e.getBlock(), true);
				}
				else {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(Minigames.inGame(e.getPlayer())) {
			if(!Minigames.getMinigame(e.getPlayer()).getArena(e.getPlayer()).isStarted()) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Minigames.inGame(p)) {
				if(!Minigames.getMinigame(p).getArena(p).isStarted()) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		for(Minigame mg : Minigames.getAllMinigames()) {
			if(e.getLine(0).equalsIgnoreCase("[" + mg.getPrefix() + "]")) {
				if(e.getPlayer().hasPermission("wolvsk." + mg.getName() + ".admin") || e.getPlayer().hasPermission("wolvsk." + mg.getName() + ".sign")) {
					for(Arena a : mg.getArenas()) {
						if(e.getLine(1).equalsIgnoreCase(a.getName())) {
							e.setLine(0, ChatColor.GREEN + "[" + mg.getPrefix() + "]");
							e.setLine(1, a.getName());
							a.addSign(e.getBlock(), true);
							return;
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		if(Minigames.inGame(e.getPlayer())) {
			if(!Minigames.getMinigame(e.getPlayer()).getArena(e.getPlayer()).isStarted()) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if(Minigames.inGame(p)) {
				if(!Minigames.getMinigame(p).getArena(p).isStarted()) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent e) {
		if(Minigames.inGame((Player) e.getEntity())) {
			if(!Minigames.getMinigame((Player) e.getEntity()).getArena((Player) e.getEntity()).isStarted()) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(Minigames.inGame(e.getPlayer())) {
			if(!Minigames.getMinigame(e.getPlayer()).getArena(e.getPlayer()).isStarted()) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				for(Minigame mg : Minigames.getAllMinigames()) {
					if(sign.getLine(0).equals(ChatColor.GREEN + "[" + mg.getPrefix() + "]")) {
						if(mg.getArena(sign.getLine(1), false)!=null && sign.getLine(3).equals(ChatColor.GREEN + "join")) {
							if(mg.getArena(sign.getLine(1), false).getLobby()==null) {
								e.getPlayer().sendMessage(Minigames.getMessage("The arena " + ChatColor.DARK_RED + sign.getLine(1) + ChatColor.RED + " is not ready to be used!", mg.getPrefix(), true));
								return;
							}
							boolean success = join(e.getPlayer(), mg, mg.getArena(sign.getLine(1), false), true);
							if(success) {
								String m = playerJoined.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%min%", mg.getArena(sign.getLine(1), false).getMin().toString()).replaceAll("%max%", mg.getArena(sign.getLine(1), false).getMax().toString()).replaceAll("%players count%", String.valueOf(mg.getArena(sign.getLine(1), false).getAllPlayers().length));
								mg.getArena(sign.getLine(1), false).broadcast(Minigames.getMessage(m, mg.getPrefix(), false));
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		String[] list = e.getMessage().split(" ");
		final Player p = e.getPlayer();
		if(commands.containsKey(list[0].replaceFirst("/", "").toLowerCase())) {
			e.setCancelled(true);
			String cmd = list[0].replaceFirst("/", "");
			String[] args = new String[list.length - 1];
			Integer i = 0;
			boolean first = true;
			for(String s : list) {
				if(!first) {
					args[i] = s;
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
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmax <arena> <max>");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".list") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " list");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".remove") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " remove <arena>");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".settimer") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " settimer <arena> <seconds>");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".start") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " start <arena>");
				}
				if(p.hasPermission("wolvsk." + mg.getName() + ".stop") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
					p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " stop <arena>");
				}
				p.sendMessage(ChatColor.AQUA + "---------------");
			}
			else if(args.length==1) {
				if(args[0].equalsIgnoreCase("leave")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".leave") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						Arena a = null;
						if(Minigames.getMinigame(p)!=null) {
							a = Minigames.getMinigame(p).getArena(p);
						}
						boolean s = Minigames.leave(p, false, true, mg.getPrefix());
						if(s) {
							p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] You left the game!");
							String m = playerLeft.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%min%", a.getMin().toString()).replaceAll("%max%", a.getMax().toString()).replaceAll("%players count%", String.valueOf(a.getAllPlayers().length));
							a.broadcast(Minigames.getMessage(m, mg.getPrefix(), false));
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("join")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " join <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("create")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " create <arena> <min> <max>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("setlobby")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setlobby") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setlobby <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("setmin")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmin") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmin <arena> <min>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("setmax")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmax") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmax <arena> <max>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("settimer")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".settimer") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " settimer <arena> <seconds>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("list")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".list") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						Arena[] l = mg.getArenas();
						ArrayList<String> f = new ArrayList<String>();
						for(Arena a : l) {
							f.add(a.getName());
						}
						p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] All arenas availables: " + ChatColor.GOLD + String.join(ChatColor.GREEN + ", " + ChatColor.GOLD, f));
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("remove")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".remove") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " remove <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("start")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".start") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " start <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("stop")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".start") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " stop <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("help")) {
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
					if(p.hasPermission("wolvsk." + mg.getName() + ".remove") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " remove <arena>");
					}
					if(p.hasPermission("wolvsk." + mg.getName() + ".settimer") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " settimer <arena> <seconds>");
					}
				}
				else {
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
					if(p.hasPermission("wolvsk." + mg.getName() + ".remove") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " remove <arena>");
					}
					if(p.hasPermission("wolvsk." + mg.getName() + ".settimer") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " settimer <arena> <seconds>");
					}
				}
			}
			else if(args.length==2) {
				if(args[0].equalsIgnoreCase("join")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						if(!Minigames.inGame(p)) {
							if(mg.getArena(args[1], true)==null) {
								p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The arena " + args[1] + " doesn't exists!");
								return;
							}
							if((mg.getArena(args[1], true).getLobby()==null)) {
								p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The arena " + ChatColor.DARK_RED + args[1] + ChatColor.RED + " is not ready to be used!");
								return;
							}
							boolean success = Minigames.join(p, mg, mg.getArena(args[1], true), true);
							if(success) {
								String m = playerJoined.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%min%", mg.getArena(args[1], true).getMin().toString()).replaceAll("%max%", mg.getArena(args[1], true).getMax().toString()).replaceAll("%players count%", String.valueOf(mg.getArena(args[1], true).getAllPlayers().length));
								mg.getArena(args[1], true).broadcast(Minigames.getMessage(m, mg.getPrefix(), false));
							}
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("create")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " create <arena> <min> <max>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("remove")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".remove") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1], true)!=null) {
							mg.removeArena(mg.getArena(args[1], true), true);
							p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] The arena " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " has been removed!");
						}
						else {
							p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The arena " + args[1] + " doesn't exists!");
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("setlobby")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setlobby") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1], true)!=null) {
							mg.getArena(args[1], true).setLobby(p.getLocation(), true);
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
				else if(args[0].equalsIgnoreCase("setmin")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmin") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmin <arena> <min>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("setmax")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmax") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setmax <arena> <min>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("settimer")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".settimer") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " settimer <arena> <seconds>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("start")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".start") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1], true)!=null) {
							Minigames.start(mg, mg.getArena(args[1], true), true);
							p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] You forced the starting of the arena " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + "!");
						}
						else {
							p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The arena " + args[1] + " doesn't exists!");
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("stop")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".stop") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1], true)!=null) {
							Minigames.stop(mg, mg.getArena(args[1], true));
							p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] You stopped the arena " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + "!");
						}
						else {
							p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The arena " + args[1] + " doesn't exists!");
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
			}
			else if(args.length==3) {
				if(args[0].equalsIgnoreCase("join")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " join <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("create")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " create <arena> <min> <max>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("setlobby")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setlobby") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setlobby <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("setmin")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmin") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1], true)!=null) {
							if(isInteger(args[2])) {
								Integer min = Integer.parseInt(args[2]);
								if(mg.getArena(args[1], true).getMax()>=min) {
									mg.getArena(args[1], true).setMin(min, true);
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
				else if(args[0].equalsIgnoreCase("setmax")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setmax") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1], true)!=null) {
							if(isInteger(args[2])) {
								Integer max = Integer.parseInt(args[2]);
								if(max>=mg.getArena(args[1], true).getMin()) {
									mg.getArena(args[1], true).setMax(max, true);
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
				else if(args[0].equalsIgnoreCase("settimer")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".settimer") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1], true)!=null) {
							if(isInteger(args[2])) {
								Integer timer = Integer.parseInt(args[2]);
								if(timer>0) {
									mg.getArena(args[1], true).setDefaultTimer(timer, true);
									p.sendMessage(ChatColor.GREEN + "[" + mg.getPrefix() + "] The timer of the arena " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " is now " +  ChatColor.DARK_GREEN + timer + ChatColor.GREEN + "!");
								}
								else {
									p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] Invalid number!");
								}
							}
							else {
								p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The number of seconds of the timer must be an integer!");
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
			}
			else if(args.length==4) {
				if(args[0].equalsIgnoreCase("join")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " join <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("setlobby")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".setlobby") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " setlobby <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("create")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						if(mg.getArena(args[1], true)==null) {
							if(args[1].equalsIgnoreCase("command") || args[1].equalsIgnoreCase("prefix") || args[1].equalsIgnoreCase("name")) {
								p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] An arena cannot be called with that name!");
							}
							if(isInteger(args[2])) {
								if(isInteger(args[3])) {
									Integer min = Integer.parseInt(args[2]);
									Integer max = Integer.parseInt(args[3]);
									if(min>max) {
										p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The minimum of player cannot be greater than the maximum of player");
										return;
									}
									if(min<=1) {
										p.sendMessage(ChatColor.RED + "[" + mg.getPrefix() + "] The minimum of player must be greater than 0!");
										return;
									}
									mg.addArena(new Arena(mg, args[1], min, max), true);
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
			}
			else {
				if(args[0].equalsIgnoreCase("join")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".join") || p.hasPermission("wolvsk." + mg.getName() + ".player")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " join <arena>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
				else if(args[0].equalsIgnoreCase("create")) {
					if(p.hasPermission("wolvsk." + mg.getName() + ".create") || p.hasPermission("wolvsk." + mg.getName() + ".admin")) {
						p.sendMessage(ChatColor.GOLD + "/" + cmd + ChatColor.GREEN + " create <arena> <min> <max>");
					}
					else {
						p.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
					}
				}
			}
			return;
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
			section2.set("timer", a.getDefaultTimer());
			if(a.getLobby()!=null) {
				section2.set("lobby.x", a.getLobby().getBlockX());
				section2.set("lobby.y", a.getLobby().getBlockY());
				section2.set("lobby.z", a.getLobby().getBlockZ());
				section2.set("lobby.world", a.getLobby().getWorld().getName());
			}
			Integer block = 0;
			for(Block b : a.getAllSigns()) {
				section2.set("signs." + block + ".x", b.getX());
				section2.set("signs." + block + ".y", b.getY());
				section2.set("signs." + block + ".z", b.getZ());
				section2.set("signs." + block + ".world", b.getWorld().getName());
				++block;
			}
		}
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void load() {
		File file = new File(WolvSK.getInstance().getDataFolder() + "/mg-settings.yml");
		if(file.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			if(config.isSet("custom-chat-format")) {
				Minigames.customChatFormat = config.getBoolean("custom-chat-format");
			}
			if(config.isSet("chat-format")) {
				Minigames.chatFormat = config.getString("chat-format");
			}
			if(config.isSet("message-format")) {
				Minigames.messageFormat = config.getString("message-format");
			}
			if(config.isSet("messages")) {
				ConfigurationSection sect = config.getConfigurationSection("messages");
				if(sect.isSet("start-in-x-seconds")) {
					Minigames.xSecsLeft = sect.getString("start-in-x-seconds");
				}
				if(sect.isSet("start-in-1-second")) {
					Minigames.OneSecLeft = sect.getString("start-in-1-second");
				}
				if(sect.isSet("player-joined")) {
					Minigames.playerJoined = sect.getString("player-joined");
				}
				if(sect.isSet("player-left")) {
					Minigames.playerLeft = sect.getString("player-left");
				}
			}
		}
		else {
			saveFormats();
		}
		file = new File(WolvSK.getInstance().getDataFolder() + "/minigames.yml");
		if(file.exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			Set<String> mgs = config.getKeys(false);
			for(String mg : mgs) {
				ConfigurationSection section = config.getConfigurationSection(mg);
				if(section.isSet("name") && section.isSet("prefix") && section.isSet("command")) {
					Minigame minigame = Minigames.createMinigame(section.getString("name"), section.getString("command"), section.getString("prefix"), false);
					Set<String> arenas = section.getKeys(false);
					for(String a : arenas) {
						if(!a.equals("name") && !a.equals("prefix") && !a.equals("command")) {
							ConfigurationSection section2 = section.getConfigurationSection(a);
							if(section2.isSet("name") && section2.isSet("max") && section2.isSet("min")) {
								Arena arena = new Arena(minigame, section2.getString("name"), section2.getInt("min"), section2.getInt("max"));
								minigame.addArena(arena, false);
								if(section2.isSet("lobby.x") && section2.isSet("lobby.y") && section2.isSet("lobby.z") && section2.isSet("lobby.world")) {
									World w = WolvSK.getInstance().getServer().getWorld(section2.getString("lobby.world"));
									if(w!=null) {
										arena.setLobby(new Location(w, section2.getInt("lobby.x"), section2.getInt("lobby.y"), section2.getInt("lobby.z")), false);
									}
								}
								if(section2.isSet("timer")) {
									arena.setDefaultTimer(section2.getInt("timer"), false);
								}
								Integer block = 0;
								while(section2.isSet("signs." + block)) {
									if(section2.isSet("signs." + block + ".x") && section2.isSet("signs." + block + ".y") && section2.isSet("signs." + block + ".z") && section2.isSet("signs." + block + ".world")) {
										World w = WolvSK.getInstance().getServer().getWorld(section2.getString("signs." + block + ".world"));
										if(w!=null) {
											arena.addSign(w.getBlockAt(section2.getInt("signs." + block + ".x"), section2.getInt("signs." + block + ".y"), section2.getInt("signs." + block + ".z")), false);
										}
									}
									++block;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void registerAll() {
		   Classes.registerClass(new ClassInfo<Arena>(Arena.class, "arena").user("arena").name("arena").parser(new Parser<Arena>() {

			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@Override
			@Nullable
			public Arena parse(String arg0, ParseContext arg1) {
				return null;
			}

			@Override
			public String toString(Arena arg0, int arg1) {
				return "Minigame:" + arg0.getMinigame().getName() + "; Arena:" + arg0.getName();
			}

			@Override
			public String toVariableNameString(Arena arg0) {
				return "Minigame:" + arg0.getMinigame().getName() + "; Arena:" + arg0.getName();
			}
		   
		   }));
		   Classes.registerClass(new ClassInfo<Minigame>(Minigame.class, "minigame").user("minigame").name("minigame").parser(new Parser<Minigame>() {

			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@Override
			@Nullable
			public Minigame parse(String arg0, ParseContext arg1) {
				return Minigames.getByName(arg0);
			}

			@Override
			public String toString(Minigame arg0, int arg1) {
				return arg0.getName();
			}

			@Override
			public String toVariableNameString(Minigame arg0) {
				return arg0.getName();
			}
		   
		   }));
		   Skript.registerEffect(EffCreateMinigame.class, "create[ a] (minigame|mg)[ named] %string% with (command|cmd) %string% and prefix %string%");
		   Skript.registerEffect(EffStartArena.class, "start %arena%");
		   Skript.registerEffect(EffStopArena.class, "stop %arena%");
		   Skript.registerEffect(EffMakeJoinArena.class, "make %player% join %arena%");
		   Skript.registerEffect(EffMakeLeaveArena.class, "make %player% leave[ current] arena");
		   Skript.registerEffect(EffCreateArena.class, "create[ a[n]] arena[ named] %string% with min[imum][ player[s]] %integer%(,| and) max[imum][ player[s]] %integer% (for|in) %minigame%");
		   Skript.registerEffect(EffArenaBroadcast.class, "broadcast [message ]%string% in %arena%");
		   Skript.registerCondition(CondInGame.class, "%player% is in (a[n] arena|[a ]game)");
		   Skript.registerCondition(CondInArena.class, "%player% is in %arena%");
		   Skript.registerCondition(CondIsStarted.class, "%arena% is started");
		   Skript.registerExpression(ExprMinigameByName.class, Minigame.class, ExpressionType.PROPERTY, "minigame %string%");
		   Skript.registerExpression(ExprAllMinigames.class, Minigame.class, ExpressionType.PROPERTY, "[all ]minigames");
		   Skript.registerExpression(ExprMinigamePlayer.class, Minigame.class, ExpressionType.PROPERTY, "[current ]minigame of %player%", "%player%['s] [current ]minigame");
		   Skript.registerExpression(ExprNameOfMinigame.class, String.class, ExpressionType.PROPERTY, "name of (minigame|mg) %minigame%", "(minigame|mg) %minigame%['s] name");
		   Skript.registerExpression(ExprAllArenas.class, Arena.class, ExpressionType.PROPERTY, "[all ]arenas of %minigame%", "%minigame%['s] arenas");
		   Skript.registerExpression(ExprArenaByName.class, Arena.class, ExpressionType.PROPERTY, "arena %string% in %minigame%", "%minigame%['s] arena %string%");
		   Skript.registerExpression(ExprArenaOfPlayer.class, Arena.class, ExpressionType.PROPERTY, "[current ]arena of %player%", "%player%['s] [current ]arena");
		   Skript.registerExpression(ExprMinigameCommand.class, String.class, ExpressionType.PROPERTY, "command of %minigame%", "%minigame%['s] command");
		   Skript.registerExpression(ExprMinigamePrefix.class, String.class, ExpressionType.PROPERTY, "prefix of %minigame%", "%minigame%['s] prefix");
		   Skript.registerExpression(ExprArenaLobby.class, Location.class, ExpressionType.PROPERTY, "lobby of %arena%", "%arena%['s] lobby");
		   Skript.registerExpression(ExprMinigameOfArena.class, Minigame.class, ExpressionType.PROPERTY, "minigame of %arena%", "%arena%['s] minigame");
		   Skript.registerExpression(ExprArenaName.class, String.class, ExpressionType.PROPERTY, "name of arena %arena%", "arena %arena%['s] name");
		   Skript.registerExpression(ExprArenaMax.class, Integer.class, ExpressionType.PROPERTY, "max[imum][ of player[s]] of %arena%", "%arena%['s] max[imum][ of player[s]]");
		   Skript.registerExpression(ExprArenaMin.class, Integer.class, ExpressionType.PROPERTY, "min[imum][ of player[s]] of %arena%", "%arena%['s] min[imum][ of player[s]]");
		   Skript.registerExpression(ExprArenaCount.class, Integer.class, ExpressionType.PROPERTY, "([player ]count|number of player[s]) of %arena%", "%arena%['s] ([player ]count|number of player[s])");
		   Skript.registerExpression(ExprArenaTimer.class, Integer.class, ExpressionType.PROPERTY, "default (timer|countdown) of %arena%", "%arena%['s] default (timer|countdown)");
		   Skript.registerExpression(ExprArenaPlayers.class, Player.class, ExpressionType.PROPERTY, "[all ]players (in|of) %arena%");
		   Skript.registerExpression(ExprMinigameChatFormat.class, String.class, ExpressionType.PROPERTY, "chat format of minigames");
		   Skript.registerExpression(ExprMinigameMessageFormat.class, String.class, ExpressionType.PROPERTY, "message format of minigames");
		   Skript.registerExpression(ExprArenaCurrentCountdown.class, Integer.class, ExpressionType.PROPERTY, "countdown of %arena%", "%arena%['s] countdown");
		   Skript.registerEvent("Arena Start Event", SimpleEvent.class, ArenaStartEvent.class, "arena start");
		   EventValues.registerEventValue(ArenaStartEvent.class, Arena.class, new Getter<Arena, ArenaStartEvent>() {
			   public Arena get(ArenaStartEvent e) {
				   return e.getArena();
			   }
		   }, 0);
		   EventValues.registerEventValue(ArenaStartEvent.class, Minigame.class, new Getter<Minigame, ArenaStartEvent>() {
			   public Minigame get(ArenaStartEvent e) {
				   return e.getMinigame();
			   }
		   }, 0);
		   Skript.registerEvent("Arena Stop Event", SimpleEvent.class, ArenaStopEvent.class, "arena stop");
		   EventValues.registerEventValue(ArenaStopEvent.class, Arena.class, new Getter<Arena, ArenaStopEvent>() {
			   public Arena get(ArenaStopEvent e) {
				   return e.getArena();
			   }
		   }, 0);
		   EventValues.registerEventValue(ArenaStopEvent.class, Minigame.class, new Getter<Minigame, ArenaStopEvent>() {
			   public Minigame get(ArenaStopEvent e) {
				   return e.getMinigame();
			   }
		   }, 0);
		   Skript.registerEvent("Player Join Arena Event", SimpleEvent.class, PlayerJoinArenaEvent.class, "[player ]arena join");
		   EventValues.registerEventValue(PlayerJoinArenaEvent.class, Arena.class, new Getter<Arena, PlayerJoinArenaEvent>() {
			   public Arena get(PlayerJoinArenaEvent e) {
				   return e.getArena();
			   }
		   }, 0);
		   EventValues.registerEventValue(PlayerJoinArenaEvent.class, Minigame.class, new Getter<Minigame, PlayerJoinArenaEvent>() {
			   public Minigame get(PlayerJoinArenaEvent e) {
				   return e.getMinigame();
			   }
		   }, 0);
		   EventValues.registerEventValue(PlayerJoinArenaEvent.class, Player.class, new Getter<Player, PlayerJoinArenaEvent>() {
			   public Player get(PlayerJoinArenaEvent e) {
				   return e.getPlayer();
			   }
		   }, 0);
		   Skript.registerEvent("Player Leave Arena Event", SimpleEvent.class, PlayerLeaveArenaEvent.class, "[player ]arena leave");
		   EventValues.registerEventValue(PlayerLeaveArenaEvent.class, Arena.class, new Getter<Arena, PlayerLeaveArenaEvent>() {
			   public Arena get(PlayerLeaveArenaEvent e) {
				   return e.getArena();
			   }
		   }, 0);
		   EventValues.registerEventValue(PlayerLeaveArenaEvent.class, Minigame.class, new Getter<Minigame, PlayerLeaveArenaEvent>() {
			   public Minigame get(PlayerLeaveArenaEvent e) {
				   return e.getMinigame();
			   }
		   }, 0);
		   EventValues.registerEventValue(PlayerLeaveArenaEvent.class, Player.class, new Getter<Player, PlayerLeaveArenaEvent>() {
			   public Player get(PlayerLeaveArenaEvent e) {
				   return e.getPlayer();
			   }
		   }, 0);
		   Skript.registerEvent("Arena Countdown Event", SimpleEvent.class, ArenaCountdownEvent.class, "arena (countdown|timer)[ change]");
		   EventValues.registerEventValue(ArenaCountdownEvent.class, Arena.class, new Getter<Arena, ArenaCountdownEvent>() {
			   public Arena get(ArenaCountdownEvent e) {
				   return e.getArena();
			   }
		   }, 0);
		   EventValues.registerEventValue(ArenaCountdownEvent.class, Minigame.class, new Getter<Minigame, ArenaCountdownEvent>() {
			   public Minigame get(ArenaCountdownEvent e) {
				   return e.getMinigame();
			   }
		   }, 0);
		   EventValues.registerEventValue(ArenaCountdownEvent.class, Integer.class, new Getter<Integer, ArenaCountdownEvent>() {
			   public Integer get(ArenaCountdownEvent e) {
				   return e.getCountdown();
			   }
		   }, 0);
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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("minigames.admin")) {
			if(args.length==0) {
				sender.sendMessage(ChatColor.GREEN + "------- Minigames ------");
				sender.sendMessage(ChatColor.GOLD + "/minigames" + ChatColor.GREEN + " create <minigame> <command> <prefix (underscore for spaces)>");
				sender.sendMessage(ChatColor.GOLD + "/minigames" + ChatColor.GREEN + " remove <minigame>");
				sender.sendMessage(ChatColor.GOLD + "/minigames" + ChatColor.GREEN + " list");
				sender.sendMessage(ChatColor.GOLD + "/minigames" + ChatColor.GREEN + " info <minigame>");
				sender.sendMessage(ChatColor.GREEN + "------------------------");
			}
			else {
				if(args[0].equalsIgnoreCase("create")) {
					if(args.length==4) {
						if(Minigames.getByName(args[1])==null) {
							if(!commands.containsKey(args[2].toLowerCase())) {
								Minigames.createMinigame(args[1], args[2].toLowerCase(), args[3].replaceAll("_", " "), true);
								sender.sendMessage(ChatColor.GREEN + "[Minigames] The Minigame " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " has been succefully created!");
							}
							else {
								sender.sendMessage(ChatColor.RED + "[Minigames] The command " + ChatColor.DARK_RED + args[2] + ChatColor.RED + " already exists!");
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + "[Minigames] The Minigame " + ChatColor.DARK_RED + args[1] + ChatColor.RED + " already exists!");
						}
					}
					else {
						sender.sendMessage(ChatColor.GOLD + "/minigames" + ChatColor.GREEN + " create <minigame> <command> <prefix (underscore for spaces)>");
					}
				}
				else if(args[0].equalsIgnoreCase("list")) {
					if(args.length==1) {
						ArrayList<String> list = new ArrayList<String>();
						for (Entry<String, Minigame> entry : map.entrySet())
						{
							list.add(entry.getKey());
						}
						sender.sendMessage(ChatColor.GREEN + "[Minigames] List: " + ChatColor.GOLD + String.join(ChatColor.GREEN + ", " + ChatColor.GOLD, list));
					}
					else {
						sender.sendMessage(ChatColor.GOLD + "/minigames" + ChatColor.GREEN + " list");
					}
				}
				else if(args[0].equalsIgnoreCase("info")) {
					if(args.length==2) {
						if(map.containsKey(args[1])) {
							Minigame mg = Minigames.getByName(args[1]);
							sender.sendMessage(ChatColor.GREEN + "[Minigames] Name: " + ChatColor.DARK_GREEN +  mg.getName() + ChatColor.GREEN + ", Command: " + ChatColor.DARK_GREEN +  mg.getCommand() + ChatColor.GREEN + ", Prefix: " + ChatColor.DARK_GREEN + mg.getPrefix());
						}
						else {
							sender.sendMessage(ChatColor.RED + "[Minigames] The Minigame " + ChatColor.DARK_RED + args[1] + ChatColor.RED + " doesn't exist!");
						}
					}
					else {
						sender.sendMessage(ChatColor.GOLD + "/minigames" + ChatColor.GREEN + " info <minigame>");
					}
				}
				else if(args[0].equalsIgnoreCase("remove")) {
					if(args.length==2) {
						if(map.containsKey(args[1])) {
							Minigames.removeMinigame(args[1], true);
							sender.sendMessage(ChatColor.GREEN + "[Minigames] The Minigame " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " has been removed!");
						}
						else {
							sender.sendMessage(ChatColor.RED + "[Minigames] The Minigame " + ChatColor.DARK_RED + args[1] + ChatColor.RED + " doesn't exist!");
						}
					}
					else {
						sender.sendMessage(ChatColor.GOLD + "/minigames" + ChatColor.GREEN + " remove <minigame>");
					}
				}
				else {
					WolvSK.getInstance().getServer().dispatchCommand(sender, "minigames");
				}
			}
		}
		else {
			sender.sendMessage(ChatColor.DARK_RED + "You don't have the permission to execute this command!");
		}
		return true;
	}
	
	public static String getMessage(String msg, String prefix, boolean error) {
		if(error) {
			return ChatColor.RED + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', Minigames.messageFormat.replaceAll("%minigame%", prefix).replaceAll("%message%", msg)));
		}
		else {
			return ChatColor.translateAlternateColorCodes('&', Minigames.messageFormat.replaceAll("%minigame%", prefix).replaceAll("%message%", msg));
		}
	}
	
	public static void saveFormats() {
		File file = new File(WolvSK.getInstance().getDataFolder() + "/mg-settings.yml");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("custom-chat-format", Minigames.customChatFormat);
		config.set("chat-format", Minigames.chatFormat);
		config.set("message-format", Minigames.messageFormat);
		ConfigurationSection sect = config.createSection("messages");
		sect.set("start-in-x-seconds", Minigames.xSecsLeft);
		sect.set("start-in-1-second", Minigames.OneSecLeft);
		sect.set("player-joined", Minigames.playerJoined);
		sect.set("player-left", Minigames.playerLeft);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
