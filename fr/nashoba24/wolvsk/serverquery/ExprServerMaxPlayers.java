package fr.nashoba24.wolvsk.serverquery;

import java.io.IOException;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprServerMaxPlayers extends SimpleExpression<Integer> {
	
	private Expression<String> ip;
	
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
		ip = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "max players on server";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		MinecraftPingReply data = null;
		String s = ip.getSingle(e);
		String[] list = s.split(":");
		if(list.length==2) {
			if(isInteger(list[1])) {
				Integer port = Integer.parseInt(list[1]);
				try {
					data = new MinecraftPing().getPing(new MinecraftPingOptions().setHostname(list[0]).setPort(port));
				} catch (IOException e1) {
					return null;
				}
			}
			else {
				return null;
			}
		}
		else if(list.length==1) {
			try {
				data = new MinecraftPing().getPing(new MinecraftPingOptions().setHostname(list[0]).setPort(25565));
			} catch (IOException e1) {
				return null;
			}
		}
		if(data==null) {
			return null;
		}
		if(data.getPlayers()==null) {
			return null;
		}
		return new Integer[]{ data.getPlayers().getMax() };
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}

