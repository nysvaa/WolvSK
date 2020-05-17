package fr.nashoba24.wolvsk;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import fr.nashoba24.wolvsk.misc.anvilgui.*;
import org.bukkit.Bukkit;

public class WolvSKNMS {

    public static void registerNMSClasses() {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].substring(1);
        String anvilNMSClass;
        if(Bukkit.getVersion().contains("1.12.2")) {
            anvilNMSClass = "1_12_2_R1";
        }
        else {
            anvilNMSClass = version;
        }
        try {
            AnvilGUI.anvilNMS = ((Class<IAnvilNMS>) Class.forName("fr.nashoba24.wolvsk.misc.anvilgui.AnvilNMS" + anvilNMSClass)).newInstance();
        } catch (ClassNotFoundException e) {
            Bukkit.getLogger().warning("The version " + version + " of Minecraft is not supported by WolvSK. Anvil GUI will not work.");
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("An error occured while loading Anvil GUI. MC Version: " + version);
        }
        try {
            Skript.registerEffect((Class<Effect>) Class.forName("fr.nashoba24.wolvsk.misc.spectate.EffSpectate" + version), "wolvsk make %player% spectate %entity%");
            Skript.registerEffect((Class<Effect>) Class.forName("fr.nashoba24.wolvsk.misc.spectate.EffUnspectate" + version), "wolvsk make %player% stop spectating");
        } catch (ClassNotFoundException e) {
            Bukkit.getLogger().warning("The version " + version + " of Minecraft is not supported by WolvSK. Spectate will not work.");
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("An error occured while loading Spectating. MC Version: " + version);
        }
    }
}
