package fr.nashoba24.wolvsk.misc.anvilgui;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Created by chasechocolate.
 */
public class AnvilNMS1_15_R1 implements IAnvilNMS {

    public AnvilNMS1_15_R1() {
    }

    public static class AnvilContainer extends ContainerAnvil {

        public static class AnvilGUIContainerAccess implements ContainerAccess {

            private EntityPlayer p;

            public AnvilGUIContainerAccess(EntityPlayer p) {
                this.p = p;
            }

            @Override
            public World getWorld() {
                return p.world;
            }

            @Override
            public BlockPosition getPosition() {
                return new BlockPosition(0, 0, 0);
            }

            @Override
            public Location getLocation() {
                return new Location(getWorld().getWorld(), 0, 0, 0);
            }

            @Override
            public <T> Optional<T> a(BiFunction<World, BlockPosition, T> biFunction) {
                return Optional.empty();
            }

            @Override
            public <T> T a(BiFunction<World, BlockPosition, T> bifunction, T t0) {
                return this.a(bifunction).orElse(t0);
            }

            @Override
            public void a(BiConsumer<World, BlockPosition> biconsumer) {
                this.a((world, blockposition) -> {
                    biconsumer.accept(world, blockposition);
                    return Optional.empty();
                });
            }
        }

        static {
            try {
                Field fieldText = ContainerAnvil.class.getDeclaredField("renameText");
                fieldText.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        private AnvilGUI menu;

        public AnvilContainer(EntityPlayer human, AnvilGUI menu, int nextContainerCounter) {
            super(nextContainerCounter, human.inventory, new AnvilGUIContainerAccess(human));

            this.menu = menu;
        }

        @Override
        public boolean canUse(EntityHuman entityhuman) {
            return true;
        }

        @Override
        public void a(String text) {
            menu.itemName = text == null ? "" : text;
            super.a(text);
        }
    }

    public Inventory open(AnvilGUI menu) {
        EntityPlayer nmsPlayer = ((CraftPlayer) menu.getPlayer()).getHandle();
        int windowId = nmsPlayer.nextContainerCounter();
        AnvilContainer container = new AnvilContainer(nmsPlayer, menu, windowId);
        Inventory inv = container.getBukkitView().getTopInventory();

        for (int slot = 0; slot < menu.getItems().length; slot++) {
            org.bukkit.inventory.ItemStack item = menu.getItems()[slot];

            if (item != null) {
                inv.setItem(slot, item);
            }
        }

        nmsPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(windowId, Containers.ANVIL, new ChatMessage(menu.getName())));
        nmsPlayer.activeContainer = container;
        nmsPlayer.activeContainer.addSlotListener(nmsPlayer);

        return inv;
    }
}