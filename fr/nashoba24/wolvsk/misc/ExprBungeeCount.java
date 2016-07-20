package fr.nashoba24.wolvsk.misc;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class ExprBungeeCount extends SimpleExpression<Integer> {
	
	private Expression<String> server;
	private boolean all = false;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern==0) {
			server = (Expression<String>) expr[0];
		}
		else {
			all = true;
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "bungeecord player count";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		String serv = "";
		if(all) {
			serv = "ALL";
		}
		else {
			serv = server.getSingle(e);
		}
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("PlayerCount");
		out.writeUTF(serv);
		Player player = Iterables.getFirst(WolvSK.getInstance().getServer().getOnlinePlayers(), null);
		player.sendPluginMessage(WolvSK.getInstance(), "BungeeCord", out.toByteArray());
        return new Integer[]{ 0 };
	}
}

