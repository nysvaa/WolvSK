package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ExprCountry extends SimpleExpression<String> {
	
	HashMap<String, String> country = new HashMap<String, String>();
	HashMap<String, String> countrycode = new HashMap<String, String>();
	
	private Expression<String> ip;
	private Expression<Player> player;
	private boolean pl = false;
	private boolean code = false;
	
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
		if(matchedPattern==0 || matchedPattern==2 || matchedPattern==4 || matchedPattern==6) {
			ip = (Expression<String>) expr[0];
			if(matchedPattern==2 || matchedPattern==6) {
				code = true;
			}
		}
		else if(matchedPattern==1 || matchedPattern==3 || matchedPattern==5 || matchedPattern==7) {
			player = (Expression<Player>) expr[0];
			pl = true;
			if(matchedPattern==3 || matchedPattern==7) {
				code = true;
			}
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "country/country code of player/ip";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		String ip2 = "";
		if(pl) {
			ip2 = player.getSingle(e).getAddress().getAddress().getHostAddress();
		}
		else {
			ip2 = ip.getSingle(e);
		}
		if(ip2 == null) {
			return null;
		}
		else {
			if(ip2.equals("")) {
				return null;
			}
		}
		if(code) {
			if(this.countrycode.containsKey(ip2)) {
				return new String[]{ this.countrycode.get(ip2) };
			}
		}
		else {
			if(this.country.containsKey(ip2)) {
				return new String[]{ this.country.get(ip2) };
			}
		}
		URL site;
		String page = "";
		try {
			site = new URL("http://ip-api.com/json/" + ip2);
		} catch (MalformedURLException e1) {
			return null;
		}
        try {
			BufferedReader in = new BufferedReader(new InputStreamReader(site.openStream()));
			page = page + (in.readLine());
		} catch (IOException e1) {
			return null;
		}
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
			json = (JSONObject) parser.parse(page);
		} catch (ParseException e1) {
			return null;
		}
        if(json == null) {
        	return null;
        }
        if(!json.get("status").equals("fail")) {
        	countrycode.put(ip2, (String) json.get("countryCode"));
        	country.put(ip2, (String) json.get("country"));
        	if(code) {
        		return new String[]{ (String) json.get("countryCode") };
        	}
        	else {
        		return new String[]{ (String) json.get("country") };
        	}
        }
        else {
        	return null;
        }
	}
}

