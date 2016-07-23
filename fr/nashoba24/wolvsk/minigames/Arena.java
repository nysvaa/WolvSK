package fr.nashoba24.wolvsk.minigames;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class Arena {
	
	private String arenaname;
	private Minigame game;
	private Location lobby;
	private Integer minp;
	private Integer maxp;
	private ArrayList<Player> pl = new ArrayList<Player>();
	private boolean started = false;
	private Integer defaultTimer = 120;
	private Integer timer = 120;
	private ArrayList<Block> signs = new ArrayList<Block>();

	public Arena(Minigame mg, String name, Integer min, Integer max) {
		name = name.replaceAll(" ", "-");
		game = mg;
		arenaname = name;
		minp = min;
		maxp = max;
	}
	
	public void setLobby(Location loc, boolean save) {
		lobby = loc;
		if(save) {
			Minigames.save(this.getMinigame());
		}
	}
	
	public Location getLobby() {
		return lobby;
	}
	
	public Minigame getMinigame() {
		return game;
	}
	
	public String getName() {
		return arenaname;
	}
	
	public void setMax(Integer max, boolean save) {
		maxp = max;
		if(save) {
			Minigames.save(this.getMinigame());
		}
	}
	
	public void setMin(Integer min, boolean save) {
		minp = min;
		if(save) {
			Minigames.save(this.getMinigame());
		}
	}
	
	public Integer getMin() {
		return minp;
	}
	
	public Integer getMax() {
		return maxp;
	}
	
	public void addPlayer(Player p) {
		pl.add(p);
	}
	
	public void removePlayer(Player p) {
		pl.remove(p);
	}
	
	public Integer playersCount() {
		return pl.size();
	}
	
	public boolean isInArena(Player p) {
		if(pl.contains(p)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public void setDefaultTimer(Integer i, boolean save) {
		if(timer==defaultTimer) {
			timer = i;
			defaultTimer = i;
		}
		else {
			defaultTimer = i;
		}
		if(save) {
			Minigames.save(this.getMinigame());
		}
	}
	
	public Integer getDefaultTimer() {
		return defaultTimer;
	}
	
	public void timer() {
		Block[] listb = this.getAllSigns();
		for(Block b : listb) {
			if(b.getType()==Material.WALL_SIGN || b.getType()==Material.SIGN_POST) {
				Sign sign = (Sign) b.getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[" + this.getMinigame().getPrefix() + "]")) {
					String line2 = sign.getLine(1);
					if(this.getMinigame().getArena(line2)==this) {
						if(this.isStarted()) {
							sign.setLine(2, this.playersCount() + "/" + this.getMax());
							sign.setLine(3, ChatColor.GREEN + "join");
						}
						else {
							sign.setLine(2, this.playersCount() + "/" + this.getMax());
							sign.setLine(3, ChatColor.GOLD + "started");
						}
						sign.update(true);//TODO
					}
					else {
						this.removeSign(b);
					}
				}
				else {
					this.removeSign(b);
				}
			}
			else {
				this.removeSign(b);
			}
		}
		if(this.isStarted()) {
			if(this.playersCount()==0) {
				Minigames.stop(this.getMinigame(), this);
			}
			return;
		}
		if(this.playersCount()>=this.getMin()) {
			--timer;
			if(timer==0) {
				boolean s = Minigames.start(this.getMinigame(), this, false);
				if(!s) {
					timer = defaultTimer - 1;
				}
			}
			Player[] list = this.getAllPlayers();
			for(Player p : list) {
				Double f = (double) (timer/defaultTimer);
				p.setExp(f.floatValue());//TODO ne marche pas
				p.setLevel(timer);
			}
			if(timer!=defaultTimer) {
				if(timer>=30) {
					if(timer % 30 == 0) {
						this.broadcast(this.getMinigame().getFullPrefix() + " " + ChatColor.GOLD + timer + ChatColor.AQUA + " seconds left before starting!");
					}
				}
				else {
					if(timer==20) {
						this.broadcast(this.getMinigame().getFullPrefix() + " " + ChatColor.GOLD + timer + ChatColor.AQUA + " seconds left before starting!");
					}
					else if(timer==10) {
						this.broadcast(this.getMinigame().getFullPrefix() + " " + ChatColor.GOLD + timer + ChatColor.AQUA + " seconds left before starting!");
					}
					else if(timer==5) {
						this.broadcast(this.getMinigame().getFullPrefix() + " " + ChatColor.GOLD + timer + ChatColor.AQUA + " seconds left before starting!");
						Player[] list2 = this.getAllPlayers();
						for(Player p : list2) {
							TitleAPI.sendTitle(p, 5, 10, 5, ChatColor.GREEN + "5", "");
							p.playNote(p.getLocation(), Instrument.PIANO, Note.natural(1, Tone.A));
						}
					}
					else if(timer==4) {
						this.broadcast(this.getMinigame().getFullPrefix() + " " + ChatColor.GOLD + timer + ChatColor.AQUA + " seconds left before starting!");
						Player[] list2 = this.getAllPlayers();
						for(Player p : list2) {
							TitleAPI.sendTitle(p, 5, 10, 5, ChatColor.YELLOW + "4", "");
							p.playNote(p.getLocation(), Instrument.PIANO, Note.natural(1, Tone.D));
						}
					}
					else if(timer==3) {
						this.broadcast(this.getMinigame().getFullPrefix() + " " + ChatColor.GOLD + timer + ChatColor.AQUA + " seconds left before starting!");
						Player[] list2 = this.getAllPlayers();
						for(Player p : list2) {
							TitleAPI.sendTitle(p, 5, 10, 5, ChatColor.GOLD + "3", "");
							p.playNote(p.getLocation(), Instrument.PIANO, Note.natural(1, Tone.D));
						}
					}
					else if(timer==2) {
						this.broadcast(this.getMinigame().getFullPrefix() + " " + ChatColor.GOLD + timer + ChatColor.AQUA + " seconds left before starting!");
						Player[] list2 = this.getAllPlayers();
						for(Player p : list2) {
							TitleAPI.sendTitle(p, 5, 10, 5, ChatColor.RED + "2", "");
							p.playNote(p.getLocation(), Instrument.PIANO, Note.natural(1, Tone.G));
						}
					}
					else if(timer==1) {
						this.broadcast(this.getMinigame().getFullPrefix() + " " + ChatColor.GOLD + timer + ChatColor.AQUA + " second left before starting!");
						Player[] list2 = this.getAllPlayers();
						for(Player p : list2) {
							TitleAPI.sendTitle(p, 5, 10, 5, ChatColor.DARK_RED + "1", "");
							p.playNote(p.getLocation(), Instrument.PIANO, Note.natural(1, Tone.G));
						}
					}
				}
			}
		}
		else {
			timer = defaultTimer;
			Player[] list = this.getAllPlayers();
			for(Player p : list) {
				p.setExp(timer/defaultTimer - 0.01F);
				p.setLevel(timer);
			}
		}
	}
	
	public Player[] getAllPlayers() {
		if(pl.size()==0) {
			return new Player[]{};
		}
		Player[] list = new Player[pl.size()];
		list = pl.toArray(list);
		return list;
	}
	
	public void setStarted(boolean b) {
		started = b;
	}
	
	public void broadcast(String msg) {
		Player[] list = this.getAllPlayers();
		for(Player p : list) {
			p.sendMessage(msg);
		}
	}
	
	
	public Block[] getAllSigns() {
		if(signs.size()==0) {
			return new Block[]{};
		}
		Block[] list = new Block[signs.size()];
		list = signs.toArray(list);
		return list;
	}
	
	public void addSign(Block b) {
		signs.add(b);
	}
	
	public void removeSign(Block b) {
		signs.remove(b);
	}
}
