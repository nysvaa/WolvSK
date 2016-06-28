package fr.nashoba24.wolvsk.expressions;

import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprASkyBlockTopTen extends SimpleExpression<OfflinePlayer>{
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends OfflinePlayer> getReturnType() {
		return OfflinePlayer.class;
	}

	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "asb top ten";
	}
	
	@Override
	@Nullable
	protected OfflinePlayer[] get(Event e) {
		Map<UUID, Integer> list = ASkyBlockAPI.getInstance().getTopTen();
		OfflinePlayer[] pl = new OfflinePlayer[list.size()];
		Integer i = 0;
		for (Entry<UUID, Integer> entry : list.entrySet())
		{
			pl[i] = fr.nashoba24.wolvsk.WolvSK.getInstance().getServer().getOfflinePlayer(entry.getKey());
			++i;
		}
		return pl;
	}
}

