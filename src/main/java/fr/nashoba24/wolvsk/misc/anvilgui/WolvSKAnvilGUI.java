package fr.nashoba24.wolvsk.misc.anvilgui;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WolvSKAnvilGUI {

	public static void registerAll() {
		   Skript.registerEffect(EffOpenAnvilGUI.class, "open[ a[n]] anvil gui name[d] %string% to %player%", "open[ a[n]] anvil gui name[d] %string% to %player% with[ default] text %string%", "open[ a[n]] anvil gui name[d] %string% to %player% with item %itemstack%", "open[ a[n]] anvil gui name[d] %string% to %player% with[ default] text %string% and [with ]item %itemstack%");
		   Skript.registerExpression(ExprAnvilGUIName.class, String.class, ExpressionType.PROPERTY, "name of anvil( |-)gui");
		   Skript.registerExpression(ExprAnvilGUIInputText.class, String.class, ExpressionType.PROPERTY, "[input ]text of anvil( |-)gui");
		   Skript.registerEvent("Anvil GUI", EvtCloseAnvilGUI.class, CloseAnvilGUIEvent.class, "(close|confirm|done)[ a[n]] anvil gui name[d] %string%", "(close|confirm|done)[ a[n]] anvil gui");
		   EventValues.registerEventValue(CloseAnvilGUIEvent.class, Player.class, new Getter<Player, CloseAnvilGUIEvent>() {
			   public Player get(CloseAnvilGUIEvent e) {
				   return e.getPlayer();
			   }
		   }, 0);
		   EventValues.registerEventValue(CloseAnvilGUIEvent.class, String.class, new Getter<String, CloseAnvilGUIEvent>() {
			   public String get(CloseAnvilGUIEvent e) {
				   return e.getInputText();
			   }
		   }, 0);
		   EventValues.registerEventValue(CloseAnvilGUIEvent.class, Inventory.class, new Getter<Inventory, CloseAnvilGUIEvent>() {
			   public Inventory get(CloseAnvilGUIEvent e) {
				   return e.getInventory();
			   }
		   }, 0);
	}
	
}
