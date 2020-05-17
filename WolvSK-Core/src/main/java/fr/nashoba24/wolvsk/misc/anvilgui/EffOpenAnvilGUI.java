package fr.nashoba24.wolvsk.misc.anvilgui;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class EffOpenAnvilGUI extends Effect {

    private Expression<String> name;
    private Expression<Player> player;
    private Expression<String> text;
    private Expression<ItemStack> item;
    private boolean t = false;
    private boolean i = false;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
        name = (Expression<String>) expr[0];
        player = (Expression<Player>) expr[1];
        if (matchedPattern == 3) {
            text = (Expression<String>) expr[2];
            t = true;
            item = (Expression<ItemStack>) expr[3];
            i = true;
        } else if (matchedPattern == 2) {
            item = (Expression<ItemStack>) expr[2];
            i = true;
        } else if (matchedPattern == 1) {
            text = (Expression<String>) expr[2];
            t = true;
        }
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "open anvil gui";
    }

    @Override
    protected void execute(Event e) {
        if (!t) {
            if (!i) {
                final String n = name.getSingle(e);
                final Player p = player.getSingle(e);
                new AnvilGUI(WolvSK.getInstance(), player.getSingle(e), n, new AnvilGUI.AnvilClickHandler() {
                    @Override
                    public boolean onClick(AnvilGUI menu, String msg) {
                        WolvSK.getInstance().getServer().getPluginManager().callEvent(new CloseAnvilGUIEvent(p, msg, n, menu.getInventory(), menu.getItems()));
                        return true;
                    }
                }).open();
            } else {
                final String n = name.getSingle(e);
                final Player p = player.getSingle(e);
                AnvilGUI gui = new AnvilGUI(WolvSK.getInstance(), player.getSingle(e), n, new AnvilGUI.AnvilClickHandler() {
                    @Override
                    public boolean onClick(AnvilGUI menu, String msg) {
                        WolvSK.getInstance().getServer().getPluginManager().callEvent(new CloseAnvilGUIEvent(p, msg, n, menu.getInventory(), menu.getItems()));
                        return true;
                    }
                });
                gui.setItem(0, item.getSingle(e), text.getSingle(e));
                gui.open();
            }
        } else {
            if (!i) {
                final String n = name.getSingle(e);
                final Player p = player.getSingle(e);
                new AnvilGUI(WolvSK.getInstance(), player.getSingle(e), n, new AnvilGUI.AnvilClickHandler() {
                    @Override
                    public boolean onClick(AnvilGUI menu, String msg) {
                        WolvSK.getInstance().getServer().getPluginManager().callEvent(new CloseAnvilGUIEvent(p, msg, n, menu.getInventory(), menu.getItems()));
                        return true;
                    }
                }).setInputName(text.getSingle(e)).open();
            } else {
                final String n = name.getSingle(e);
                final Player p = player.getSingle(e);
                AnvilGUI gui = new AnvilGUI(WolvSK.getInstance(), player.getSingle(e), n, new AnvilGUI.AnvilClickHandler() {
                    @Override
                    public boolean onClick(AnvilGUI menu, String msg) {
                        WolvSK.getInstance().getServer().getPluginManager().callEvent(new CloseAnvilGUIEvent(p, msg, n, menu.getInventory(), menu.getItems()));
                        return true;
                    }
                }).setInputName(text.getSingle(e));
                gui.setItem(0, item.getSingle(e), text.getSingle(e));
                gui.open();
            }
        }
    }
}
