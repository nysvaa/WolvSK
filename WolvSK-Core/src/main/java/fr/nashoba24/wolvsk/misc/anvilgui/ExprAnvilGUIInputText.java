package fr.nashoba24.wolvsk.misc.anvilgui;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprAnvilGUIInputText extends SimpleExpression<String>  {
    @Override
    public boolean isSingle() {
        return true;
    }
 
    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
 
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
    	if (!ScriptLoader.isCurrentEvent(CloseAnvilGUIEvent.class)) {
			Skript.error("Cannot use 'input text of anvil gui' outside of a anvil gui event", ErrorQuality.SEMANTIC_ERROR);
			return false;
    	}
    	return true;
    }
 
    @Override
    public String toString(@Nullable Event e, boolean paramBoolean) {
        return "input text of anvil gui";
    }
 
    @Override
    @Nullable
    protected String[] get(Event event) {
    	if (event == null)
			return null;
		if (event instanceof CloseAnvilGUIEvent) {
			final String text = ((CloseAnvilGUIEvent) event).getInputText();
			return new String[]{ text };
		}
		return null;
    }
}