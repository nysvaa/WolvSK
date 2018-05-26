package fr.nashoba24.wolvsk.askyblock;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.wasteofplastic.askyblock.ASkyBlock;
import com.wasteofplastic.askyblock.LevelCalcByChunk;
import com.wasteofplastic.askyblock.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class EffASkyBlockCalculateLevel extends Effect {
	
	private Expression<Player> player;
	public static HashMap<UUID, Long> levelWaitTime = new HashMap<UUID, Long>();
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "asb calculate level";
	}
	
	@Override
	protected void execute(Event e) {
		//ASkyBlockAPI.getInstance().calculateIslandLevel(player.getSingle(e).getUniqueId());
        /*if (Settings.fastLevelCalc) {
            new LevelCalcByChunk(ASkyBlock.getPlugin(), player.getSingle(e).getUniqueId(), Bukkit.getServer().getConsoleSender(), false);
        } else {
            if (ASkyBlock.getPlugin().isCalculatingLevel()) {
            	Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + ASkyBlock.getPlugin().myLocale().islanderrorLevelNotReady);
                return;
            }
            ASkyBlock.getPlugin().setCalculatingLevel(true);
            LevelCalc levelCalc = new LevelCalc(ASkyBlock.getPlugin(), player.getSingle(e).getUniqueId(), Bukkit.getServer().getConsoleSender(), false);
           levelCalc.runTaskTimer(ASkyBlock.getPlugin(), 0L, 5L);
        }*/ 
		Player asker = player.getSingle(e);
        if (!onLevelWaitTime(asker) || Settings.levelWait <= 0) {
            setLevelWaitTime(asker);
            new LevelCalcByChunk(ASkyBlock.getPlugin(), ASkyBlock.getPlugin().getGrid().getIsland(asker.getUniqueId()), asker.getUniqueId(), Bukkit.getServer().getConsoleSender(), false);
        }
	}
	
    public boolean onLevelWaitTime(final Player player) {
        if (levelWaitTime.containsKey(player.getUniqueId())) {
            if (levelWaitTime.get(player.getUniqueId()).longValue() > Calendar.getInstance().getTimeInMillis()) {
                return true;
            }

            return false;
        }

        return false;
    }
    
    public void setLevelWaitTime(final Player player) {
        levelWaitTime.put(player.getUniqueId(), Long.valueOf(Calendar.getInstance().getTimeInMillis() + Settings.levelWait * 1000));
    }
}
