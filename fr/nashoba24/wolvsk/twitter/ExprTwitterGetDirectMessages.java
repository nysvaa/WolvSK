package fr.nashoba24.wolvsk.twitter;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterGetDirectMessages extends SimpleExpression<String>{
	
	private Expression<Integer> page;
	private boolean paging = false;
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern == 1) {
			page = (Expression<Integer>) expr[0];
			paging = true;
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "direct messages";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if(WolvSKTwitter.tf==null) { return null; }
		try {
			if(paging) {
				ResponseList<DirectMessage> list = WolvSKTwitter.tf.getInstance().getDirectMessages(new Paging(page.getSingle(e)));
				ArrayList<String> msg = new ArrayList<String>();
				for(DirectMessage dm : list) {
					msg.add(dm.getSenderScreenName() + "-" + dm.getText());
				}
				String[] l = new String[msg.size()];
				l = msg.toArray(l);
				return l;
			}
			else {
				ResponseList<DirectMessage> list = WolvSKTwitter.tf.getInstance().getDirectMessages();
				ArrayList<String> msg = new ArrayList<String>();
				for(DirectMessage dm : list) {
					msg.add("@" + dm.getSenderScreenName() + " - " + dm.getText());
				}
				String[] l = new String[msg.size()];
				l = msg.toArray(l);
				return l;
			}
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}

