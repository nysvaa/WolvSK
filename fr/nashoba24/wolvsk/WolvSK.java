package fr.nashoba24.wolvsk;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import ch.njol.skript.Skript;
import fr.nashoba24.wolvsk.askyblock.WolvSKASkyBlock;
import fr.nashoba24.wolvsk.essentials.WolvSKEssentials;
import fr.nashoba24.wolvsk.guardianbeamapi.WolvSKGuardianBeamAPI;
import fr.nashoba24.wolvsk.minigames.Minigames;
import fr.nashoba24.wolvsk.misc.ExprNameOfBlock;
import fr.nashoba24.wolvsk.misc.WolvSKMisc;
import fr.nashoba24.wolvsk.playerpoints.WolvSKPlayerPoints;
import fr.nashoba24.wolvsk.pvparena.WolvSKPvpArena;
import fr.nashoba24.wolvsk.serverquery.WolvSKPing;
import fr.nashoba24.wolvsk.supertrails.WolvSKSuperTrails;
import fr.nashoba24.wolvsk.teamspeak.WolvSKTS3;
import fr.nashoba24.wolvsk.wolvmc.WolvSKWolvMC;

public class WolvSK extends JavaPlugin implements Listener, PluginMessageListener {
	
	private static WolvSK instance;
	public static TS3Query ts3query;
	public static TS3Api ts3api;
	public static HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	  
	  @Override
	  public void onDisable()
	  {
		  Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&bWolvSK Disabled!"));
	  }
	  
	  @Override
	  public void onEnable()
	  {
		   instance = this;
		   Bukkit.getPluginManager().registerEvents(new ExprNameOfBlock(), this);
		   Bukkit.getPluginManager().registerEvents(new Minigames(), this);
		   Bukkit.getPluginManager().registerEvents(this, this);
		   getCommand("minigames").setExecutor(new Minigames());
		   Skript.registerAddon(this);
		   Minigames.registerAll();
		   Minigames.load();
		   WolvSKASkyBlock.registerAll();
		   WolvSKEssentials.registerAll();
		   WolvSKGuardianBeamAPI.registerAll();
		   WolvSKMisc.registerAll();
		   WolvSKPvpArena.registerAll();
		   WolvSKPing.registerAll();
		   WolvSKSuperTrails.registerAll();
		   WolvSKTS3.registerAll();
		   WolvSKWolvMC.registerAll();
		   WolvSKPlayerPoints.registerAll();
		   Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aWolvSK Enabled!"));
	  }

	  
	  public static WolvSK getInstance() {
		    return WolvSK.instance;
	  }
	  
	  @Override
	  public void onPluginMessageReceived(String channel, Player player, byte[] message) {
	    if (!channel.equals("BungeeCord")) {
	      return;
	    }
	    ByteArrayDataInput in = ByteStreams.newDataInput(message);
	    String sub = in.readUTF();
    	String server = in.readUTF();
	    int playercount = in.readInt();
    	System.out.println("Sub: " + sub);
	    if(sub.equalsIgnoreCase("PlayerCount")) {
	    	System.out.println(playercount + " joueurs sur " + server);
	    }
	  }
}
