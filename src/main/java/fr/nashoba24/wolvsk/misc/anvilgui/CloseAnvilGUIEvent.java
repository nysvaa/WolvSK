package fr.nashoba24.wolvsk.misc.anvilgui;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CloseAnvilGUIEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private String input;
	private String gui;
	private Inventory inventory;
	private ItemStack[] is;
 
    public CloseAnvilGUIEvent(Player p, String text, String name, Inventory inv, ItemStack[] items) {
    	this.player = p;
    	this.input = text;
    	this.gui = name;
    	this.inventory = inv;
    	this.is = items;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public String getInputText() {
    	return this.input;
    }
    
    public String getGuiName() {
    	return this.gui;
    }
    
    public Inventory getInventory() {
    	return this.inventory;
    }
    
    public ItemStack[] getItems() {
    	return this.is;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
 
}
