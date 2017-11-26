package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class CondEven extends Condition {


    private Expression<Number> number;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        number = (Expression<Number>) expr[0];
        setNegated(i == 1);
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "number is even";
    }

    @Override
    public boolean check(Event e) {
    	return isNegated() ? !((number.getSingle(e).intValue() & 1) == 0) : ((number.getSingle(e).intValue() & 1) == 0);
    }

}