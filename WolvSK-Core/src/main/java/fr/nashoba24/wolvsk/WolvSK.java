package fr.nashoba24.wolvsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import fr.nashoba24.wolvsk.askyblock.WolvSKASkyBlock;
import fr.nashoba24.wolvsk.essentials.WolvSKEssentials;
import fr.nashoba24.wolvsk.guardianbeamapi.WolvSKGuardianBeamAPI;
import fr.nashoba24.wolvsk.maths.WolvSKMaths;
import fr.nashoba24.wolvsk.misc.WolvSKMisc;
import fr.nashoba24.wolvsk.playerpoints.WolvSKPlayerPoints;
import fr.nashoba24.wolvsk.pvparena.WolvSKPvpArena;
import fr.nashoba24.wolvsk.supertrails.WolvSKSuperTrails;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class WolvSK extends JavaPlugin {
    private static WolvSK instance;
    private static SkriptAddon addonInstance;
    protected FileConfiguration config;
    public static HashMap<String, Long> cooldowns = new HashMap<>();

    public WolvSK() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void onEnable() {
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
        if (!file.exists()) {
            file.mkdir();
        }
        config = this.getConfig();
        config.addDefault("askyblock", true);
        config.addDefault("essentials", true);
        config.addDefault("guardianbeamapi", true);
        config.addDefault("misc", true);
        config.addDefault("pvparena", true);
        config.addDefault("supertrails", true);
        config.addDefault("playerpoints", true);
        config.addDefault("maths", true);
        config.addDefault("nms", true);
        config.options().copyDefaults(true);
        saveConfig();
    }

    private void register() {
        if (!config.contains("askyblock") || config.getBoolean("askyblock")) {
            WolvSKASkyBlock.register();
        }
        if (!config.contains("essentials") || config.getBoolean("essentials")) {
            WolvSKEssentials.register();
        }
        if (!config.contains("guardianbeamapi") || config.getBoolean("guardianbeamapi")) {
            WolvSKGuardianBeamAPI.register();
        }
        if (!config.contains("misc") || config.getBoolean("misc")) {
            WolvSKMisc.register();
            CleanupTimer();
        }
        if (!config.contains("pvparena") || config.getBoolean("pvparena")) {
            WolvSKPvpArena.register();
        }
        if (!config.contains("supertrails") || config.getBoolean("supertrails")) {
            WolvSKSuperTrails.register();
        }
        if (!config.contains("playerpoints") || config.getBoolean("playerpoints")) {
            WolvSKPlayerPoints.register();
        }
        if (!config.contains("maths") || config.getBoolean("maths")) {
            WolvSKMaths.register();
        }
        if (!config.contains("nms") || config.getBoolean("nms")) {
            WolvSKNMS.registerNMSClasses();
        }
    }

    //Hourly Timer to prevent memory leaks - Govindas
    private void CleanupTimer() {
        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run() {
                int i = 0;
                Iterator<Map.Entry<String, Long>> it = WolvSK.cooldowns.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Long> pair = it.next();
                    long value = pair.getValue();
                    if (value < System.currentTimeMillis()) {
                        i++;
                        it.remove(); // avoids a ConcurrentModificationException
                    }
                }
                if (i > 0) {
                    System.out.println("[WolvSK Hourly Memory Cleanup] " + i + " finished cooldowns cleared from memory.");
                }
            }

        };
        //hourly schedule
        timer.schedule(hourlyTask, 100, 1000 * 60 * 60);
    }
}
