package fr.nashoba24.wolvsk.askyblock;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprASkyBlockTeamLeader extends SimpleExpression<OfflinePlayer>{
	private Expression<Player> player;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends OfflinePlayer> getReturnType() {
		return OfflinePlayer.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "asb team leader";
	}
	
	@Override
	@Nullable
	protected OfflinePlayer[] get(Event e) {
		if(player.getSingle(e)==null) {
			return null;
		}
		UUID uuid = ASkyBlockAPI.getInstance().getTeamLeader(player.getSingle(e).getUniqueId());
		if(uuid == null) {
			return null;
		}
		else {
			return new OfflinePlayer[]{ fr.nashoba24.wolvsk.WolvSK.getInstance().getServer().getOfflinePlayer(uuid) };
		}
	}
}

