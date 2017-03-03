package fr.nashoba24.wolvsk.misc.anvilgui;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;

public class EvtCloseAnvilGUI extends SkriptEvent {
	
	private Literal<String> name;
	private boolean n = false;

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
    	if(i==0) {
    		name = (Literal<String>) literals[0];
    		n = true;
    	}
        return true;
    }

    @Override
    public boolean check(Event e) {
    	if(n) {
    		return ((CloseAnvilGUIEvent) e).getGuiName().equals(name.getSingle(e));
    	}
    	return true;
    }

    @Override
    public String toString(Event e, boolean b) {
        return "close anvil GUI";
    }

}
