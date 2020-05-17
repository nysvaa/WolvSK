package fr.nashoba24.wolvsk.essentials;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.List;

public class ExprEssentialsHome extends SimpleExpression<Location>{
	
	private Expression<Player> player;
	private Expression<String> home;
	private boolean set = false;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(expr.length==1) {
			player = (Expression<Player>) expr[0];
			set = true;
			return true;
		}
		if(matchedPattern==0) {
			player = (Expression<Player>) expr[1];
			home = (Expression<String>) expr[0];
		}
		else {
			player = (Expression<Player>) expr[0];
			home = (Expression<String>) expr[1];
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "home of player";
	}
	
	@Override
	@Nullable
	protected Location[] get(Event e) {
    	Essentials ess = ((Essentials) WolvSK.getInstance().getServer().getPluginManager().getPlugin("Essentials"));
    	User user = ess.getUser(player.getSingle(e));
    	if(user == null) { return null; }
    	Location h;
    	if(set) {
    		try {
				h = user.getHome("home");
				if(h==null) {
					List<String> list = user.getHomes();
					if(list==null) {
						return null;
					}
					if(list.size()>=1) {
						try {
							return new Location[]{ user.getHome(list.get(0)) };
						} catch (Exception e2) {
							return null;
						}
					}
					else {
						return null;
					}
				}
				else {
					return new Location[]{ h };
				}
			} catch (Exception e1) {
				List<String> list = user.getHomes();
				if(list==null) {
					return null;
				}
				if(list.size()>=1) {
					try {
						return new Location[]{ user.getHome(list.get(0)) };
					} catch (Exception e2) {
						return null;
					}
				}
				else {
					return null;
				}
			}
    	}
		try {
			h = user.getHome(home.getSingle(e));
		} catch (Exception e1) {
			return null;
		}
		return new Location[]{ h };
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
    	Essentials ess = ((Essentials) WolvSK.getInstance().getServer().getPluginManager().getPlugin("Essentials"));
    	User user = ess.getUser(player.getSingle(e));
    	if(user == null) { return; }
		if (mode == ChangeMode.SET) {
			if(set) {
				user.setHome("home", (Location) delta[0]);
			}
			else {
				user.setHome(home.getSingle(e), (Location) delta[0]);
			}
		}
		else if(mode == ChangeMode.REMOVE) {
			try {
				if(set) {
					user.delHome("home");
				}
				else {
					user.delHome(home.getSingle(e));
				}
			} catch (Exception e1) {
				return;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Location.class);
		return null;
	}
}

