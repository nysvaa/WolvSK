package fr.nashoba24.wolvsk.misc.spectate;

import javax.annotation.Nullable;

import net.minecraft.server.v1_10_R1.PacketPlayOutCamera;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSpectate1_10_R1 extends Effect {
	
	private Expression<Player> player;
	private Expression<Entity> entity;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		entity = (Expression<Entity>) expr[1];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "spectate entity";
	}
	
	@Override
	protected void execute(Event e) {
		PacketPlayOutCamera camera = new PacketPlayOutCamera(((CraftEntity) entity.getSingle(e)).getHandle());
		((CraftPlayer) player.getSingle(e)).getHandle().playerConnection.sendPacket(camera);
	}
}
