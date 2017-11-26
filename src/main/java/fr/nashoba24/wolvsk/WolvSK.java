package fr.nashoba24.wolvsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import fr.nashoba24.wolvsk.askyblock.WolvSKASkyBlock;
import fr.nashoba24.wolvsk.essentials.WolvSKEssentials;
import fr.nashoba24.wolvsk.guardianbeamapi.WolvSKGuardianBeamAPI;
import fr.nashoba24.wolvsk.maths.WolvSKMaths;
import fr.nashoba24.wolvsk.minigames.Minigames;
import fr.nashoba24.wolvsk.misc.WolvSKMisc;
import fr.nashoba24.wolvsk.playerpoints.WolvSKPlayerPoints;
import fr.nashoba24.wolvsk.pvparena.WolvSKPvpArena;
import fr.nashoba24.wolvsk.serverquery.WolvSKPing;
import fr.nashoba24.wolvsk.supertrails.WolvSKSuperTrails;
import fr.nashoba24.wolvsk.wolvmc.WolvSKWolvMC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class WolvSK extends JavaPlugin {
	private static WolvSK instance;
	private static SkriptAddon addonInstance;
	protected FileConfiguration config;
	public static HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	public WolvSK() {
		if (instance == null) {
			instance = this;
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public void onEnable()
	{
		instance = getInstance();
		addonInstance = getAddonInstance();

		setupConfig();
		register();
	}

	@Override
	public void onDisable() {
	}

	public static SkriptAddon getAddonInstance() {
		if (addonInstance == null) {
			addonInstance = Skript.registerAddon(getInstance());
		}
		return addonInstance;
	}

	public static WolvSK getInstance() {
		if (instance == null) {
			throw new IllegalStateException();
		}
		return instance;
	}

	private void setupConfig() {
		File file = new File(instance.getDataFolder() + "/");
		if(!file.exists()) {
			file.mkdir();
		}
		config = this.getConfig();
		config.addDefault("askyblock", true);
		config.addDefault("essentials", true);
		config.addDefault("guardianbeamapi", true);
		config.addDefault("misc", true);
		config.addDefault("pvparena", true);
		config.addDefault("ping", true);
		config.addDefault("supertrails", true);
		config.addDefault("wolvmc", true);
		config.addDefault("playerpoints", true);
		config.addDefault("maths", true);
		config.addDefault("minigames", true);
		config.options().copyDefaults(true);
		saveConfig();
	}

	private void register() {
		if (config.getBoolean("askyblock")) {
			WolvSKASkyBlock.register();
		}
		if (config.getBoolean("essentials")) {
			WolvSKEssentials.register();
		}
		if (config.getBoolean("guardianbeamapi")) {
			WolvSKGuardianBeamAPI.register();
		}
		if (config.getBoolean("misc")) {
			WolvSKMisc.register();
		}
		if (config.getBoolean("pvparena")) {
			WolvSKPvpArena.register();
		}
		if (config.getBoolean("ping")) {
			WolvSKPing.register();
		}
		if (config.getBoolean("supertrails")) {
			WolvSKSuperTrails.register();
		}
		if (config.getBoolean("wolvmc")) {
			WolvSKWolvMC.register();
		}
		if (config.getBoolean("playerpoints")) {
			WolvSKPlayerPoints.register();
		}
		if (config.getBoolean("maths")) {
			WolvSKMaths.register();
		}
		if (config.getBoolean("minigames")) {
			Bukkit.getPluginManager().registerEvents(new Minigames(), this);
			this.getCommand("minigames").setExecutor(new Minigames());
			Minigames.registerAll();
			Minigames.load();
		}
	}
}
