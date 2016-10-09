package fr.nashoba24.wolvsk.misc;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondOdd extends Condition {


    private Expression<Number> number;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        number = (Expression<Number>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "number is odd";
    }

    @Override
    public boolean check(Event e) {
    	if((number.getSingle(e).intValue() & 1) == 1) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

}