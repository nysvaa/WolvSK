package fr.nashoba24.wolvsk.askyblock;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import com.wasteofplastic.askyblock.ASkyBlock;
import com.wasteofplastic.askyblock.LevelCalc;
import com.wasteofplastic.askyblock.LevelCalcByChunk;
import com.wasteofplastic.askyblock.Settings;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffASkyBlockCalculateLevel extends Effect {
	
	private Expression<Player> player;
	
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
        if (Settings.fastLevelCalc) {
            new LevelCalcByChunk(ASkyBlock.getPlugin(), player.getSingle(e).getUniqueId(), Bukkit.getServer().getConsoleSender(), false);
        } else {
            if (ASkyBlock.getPlugin().isCalculatingLevel()) {
            	Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + ASkyBlock.getPlugin().myLocale().islanderrorLevelNotReady);
                return;
            }
            ASkyBlock.getPlugin().setCalculatingLevel(true);
            LevelCalc levelCalc = new LevelCalc(ASkyBlock.getPlugin(), player.getSingle(e).getUniqueId(), Bukkit.getServer().getConsoleSender(), false);
            levelCalc.runTaskTimer(ASkyBlock.getPlugin(), 0L, 5L);
        }
	}
}
