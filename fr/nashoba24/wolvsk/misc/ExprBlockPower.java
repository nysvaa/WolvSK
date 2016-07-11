package fr.nashoba24.wolvsk.misc;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprBlockPower extends SimpleExpression<Integer> {
	
	private Expression<Block> block;
	
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
		block = (Expression<Block>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "block power";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
        return new Integer[]{ block.getSingle(e).getBlockPower() };
	}
}

