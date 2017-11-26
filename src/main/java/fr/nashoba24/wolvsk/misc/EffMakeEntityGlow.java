package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_11_R1.MobEffect;
import net.minecraft.server.v1_11_R1.MobEffects;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityEffect;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffMakeEntityGlow extends Effect {
	
	private Expression<Entity> entity;
	private Expression<Player> player;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		entity = (Expression<Entity>) expr[0];
		player = (Expression<Player>) expr[1];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "make entity glow for players";
	}
	
	@Override
	protected void execute(Event e) {
		Player[] pl = player.getAll(e);
		Entity ent = entity.getSingle(e);
		PacketPlayOutEntityEffect packet = new PacketPlayOutEntityEffect(ent.getEntityId(), new MobEffect(MobEffects.GLOWING, 2147483647, 1, true, false));
		for(Player p : pl) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
