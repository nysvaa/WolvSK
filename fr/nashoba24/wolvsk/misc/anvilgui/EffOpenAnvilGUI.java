package fr.nashoba24.wolvsk.misc.anvilgui;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class EffOpenAnvilGUI extends Effect {
	
	private Expression<String> name;
	private Expression<Player> player;
	private Expression<String> text;
	private boolean t = false;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern==1) {
			name = (Expression<String>) expr[0];
			player = (Expression<Player>) expr[1];
			text = (Expression<String>) expr[2];
			t = true;
		}
		else if(matchedPattern==0) {
			name = (Expression<String>) expr[0];
			player = (Expression<Player>) expr[1];
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "open anvil gui";
	}
	
	@Override
	protected void execute(Event e) {
		if(!t) {
			final String n = name.getSingle(e);
			final Player p = player.getSingle(e);
	        new AnvilGUI(WolvSK.getInstance(), player.getSingle(e), new AnvilGUI.AnvilClickHandler() {
	            @Override
	            public boolean onClick(AnvilGUI menu, String msg){
	            	WolvSK.getInstance().getServer().getPluginManager().callEvent(new CloseAnvilGUIEvent(p, msg, n, menu.getInventory(), menu.getItems()));
	                return true;
	            }
	        }).open();
		}
		else {
			final String n = name.getSingle(e);
			final Player p = player.getSingle(e);
	        new AnvilGUI(WolvSK.getInstance(), player.getSingle(e), new AnvilGUI.AnvilClickHandler() {
	            @Override
	            public boolean onClick(AnvilGUI menu, String msg){
	            	WolvSK.getInstance().getServer().getPluginManager().callEvent(new CloseAnvilGUIEvent(p, msg, n, menu.getInventory(), menu.getItems()));
	                return true;
	            }
	        }).setInputName(text.getSingle(e)).open();
		}
	}
}
