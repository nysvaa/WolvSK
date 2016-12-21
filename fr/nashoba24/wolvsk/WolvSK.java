package fr.nashoba24.wolvsk;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Query;

import ch.njol.skript.Skript;
import fr.nashoba24.wolvsk.askyblock.WolvSKASkyBlock;
import fr.nashoba24.wolvsk.essentials.WolvSKEssentials;
import fr.nashoba24.wolvsk.guardianbeamapi.WolvSKGuardianBeamAPI;
import fr.nashoba24.wolvsk.maths.WolvSKMaths;
import fr.nashoba24.wolvsk.minigames.Minigames;
import fr.nashoba24.wolvsk.misc.ExprNameOfBlock;
import fr.nashoba24.wolvsk.misc.WolvSKMisc;
import fr.nashoba24.wolvsk.playerpoints.WolvSKPlayerPoints;
import fr.nashoba24.wolvsk.pvparena.WolvSKPvpArena;
import fr.nashoba24.wolvsk.serverquery.WolvSKPing;
import fr.nashoba24.wolvsk.supertrails.WolvSKSuperTrails;
import fr.nashoba24.wolvsk.teamspeak.WolvSKTS3;
import fr.nashoba24.wolvsk.twitter.WolvSKTwitter;
import fr.nashoba24.wolvsk.wolvmc.WolvSKWolvMC;

public class WolvSK extends JavaPlugin implements Listener {
	
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
		   if(Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
			   Bukkit.getPluginManager().registerEvents(new Minigames(), this);
			   getCommand("minigames").setExecutor(new Minigames());
		   }
		   Skript.registerAddon(this);
		   if(Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
			   Minigames.registerAll();
			   Minigames.load();
		   }
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
		   WolvSKTwitter.registerAll();
		   WolvSKMaths.registerAll();
		   Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aWolvSK Enabled!"));
	  }

	  
	  public static WolvSK getInstance() {
		    return WolvSK.instance;
	  }
}
