package fr.nashoba24.wolvsk.teamspeak;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class CondTSIsOnline extends Condition {


    private Expression<Client> client;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        client = (Expression<Client>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "client is online";
    }

    @Override
    public boolean check(Event e) {
    	if(WolvSK.ts3api==null || client.getSingle(e)==null) { return false; }
    	if(!client.getSingle(e).isAway()) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

}