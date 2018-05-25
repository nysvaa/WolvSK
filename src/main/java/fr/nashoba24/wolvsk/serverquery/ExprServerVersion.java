package fr.nashoba24.wolvsk.serverquery;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.io.IOException;

public class ExprServerVersion extends SimpleExpression<String> {
	
	private Expression<String> ip;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		ip = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "version of server";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
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
		if(data.getVersion()==null) {
			return null;
		}
		return new String[]{ data.getVersion().getName() };
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

