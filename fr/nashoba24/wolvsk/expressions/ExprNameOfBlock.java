package fr.nashoba24.wolvsk.expressions;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprNameOfBlock extends SimpleExpression<String> implements Listener {
	private Expression<Block> block;
	
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
		block = (Expression<Block>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "name of block";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		Object s = Variables.getVariable("wolvsk.nameblock." + block.getSingle(e).getX() + "." + block.getSingle(e).getY() + "." + block.getSingle(e).getZ() + "." + block.getSingle(e).getWorld().getName(), null, false);
		if(s==null) {
			return null;
		}
		else {
			return new String[]{ (String) s };
		}
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			Variables.setVariable("wolvsk.nameblock." + block.getSingle(e).getX() + "." + block.getSingle(e).getY() + "." + block.getSingle(e).getZ() + "." + block.getSingle(e).getWorld().getName(), (String) delta[0], null, false);
		}
		else if (mode == ChangeMode.RESET) {
			Variables.setVariable("wolvsk.nameblock." + block.getSingle(e).getX() + "." + block.getSingle(e).getY() + "." + block.getSingle(e).getZ() + "." + block.getSingle(e).getWorld().getName(), null, null, false);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET)
			return CollectionUtils.array(String.class);
		return null;
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Object s = Variables.getVariable("wolvsk.nameblock." + e.getBlock().getX() + "." + e.getBlock().getY() + "." + e.getBlock().getZ() + "." + e.getBlock().getWorld().getName(), null, false);
		if(s!=null) {
			Variables.setVariable("wolvsk.nameblock." + e.getBlock().getX() + "." + e.getBlock().getY() + "." + e.getBlock().getZ() + "." + e.getBlock().getWorld().getName(), null, null, false);
		}
	}
}

