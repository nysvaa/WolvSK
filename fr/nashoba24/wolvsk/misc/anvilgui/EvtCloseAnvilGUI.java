package fr.nashoba24.wolvsk.misc.anvilgui;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;

public class EvtCloseAnvilGUI extends SkriptEvent {
	
	private Literal<String> name;

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
    	name = (Literal<String>) literals[0];
        return true;
    }

    @Override
    public boolean check(Event e) {
        return ((CloseAnvilGUIEvent) e).getGuiName().equals(name.getSingle(e));
    }

    @Override
    public String toString(Event e, boolean b) {
        return "close anvil GUI";
    }

}
