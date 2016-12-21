package fr.nashoba24.wolvsk.teamspeak;

import javax.annotation.Nullable;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import fr.nashoba24.wolvsk.WolvSK;

public class WolvSKTS3 {

	public static void registerAll() {
		   Classes.registerClass(new ClassInfo<Client>(Client.class, "client").user("client").name("client").parser(new Parser<Client>() {

			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@Override
			@Nullable
			public Client parse(String arg0, ParseContext arg1) {
				if(WolvSK.ts3api==null) { return null; }
				if(arg0.startsWith("client ")) {
					return WolvSK.ts3api.getClientByNameExact(arg0.replaceFirst("client ", ""), true);
				}
				else {
					return WolvSK.ts3api.getClientByNameExact(arg0, true);
				}
			}

			@Override
			public String toString(Client arg0, int arg1) {
				return arg0.getNickname();
			}

			@Override
			public String toVariableNameString(Client arg0) {
				return arg0.getNickname();
			}
		   
		   }));
		   Skript.registerEffect(EffTSConnect.class, "(teamspeak|ts[3])[ server] connect to %string% with user %string% and (login|credentials) %string%, %string%[ on query port %integer%]", "(teamspeak|ts[3])[ server] debug connect to %string% with user %string% and (login|credentials) %string%, %string%[ on query port %integer%]");
		   Skript.registerEffect(EffTSDisconnect.class, "(teamspeak|ts[3])[ server] disconnect");
		   Skript.registerEffect(EffTSSendChannelMessage.class, "(teamspeak|ts[3])[ server][ send] channel (message|msg) %string%[ (in|to) channel id %integer%]");
		   Skript.registerEffect(EffTSBroadcastMessage.class, "(teamspeak|ts[3])[ server][ send] broadcast[ message] %string%");
		   Skript.registerEffect(EffTSKickClient.class, "(teamspeak|ts[3])[ server] kick %client% (due to|because) %string%[ from server]");
		   Skript.registerEffect(EffTSBanTemporary.class, "(teamspeak|ts[3])[ server] tempban %client% (due to|because) %string% for %integer% second[s]");
		   Skript.registerEffect(EffTSBan.class, "(teamspeak|ts[3])[ server] ban %client% (due to|because) %string%");
		   Skript.registerEffect(EffTSMoveClient.class, "(teamspeak|ts[3])[ server] move %client% to channel[ with id] %integer%");
		   Skript.registerEffect(EffTSAddToGroup.class, "(teamspeak|ts[3])[ server] add %client% to [server ]group[ with id] %integer%");
		   Skript.registerEffect(EffTSRemoveFromGroup.class, "(teamspeak|ts[3])[ server] remove %client% from [server ]group[ with id] %integer%");
		   Skript.registerEffect(EffTSPoke.class, "(teamspeak|ts[3])[ server] poke %client% with (message|msg) %string%");
		   Skript.registerEffect(EffTSSendPV.class, "(teamspeak|ts[3])[ server][ send] (private|pv) (message|msg) %string%[ to] %client%");
		   Skript.registerCondition(CondTSIsOnline.class, "(teamspeak|ts[3]) %client% is online");
		   Skript.registerCondition(CondTSIsQuery.class, "(teamspeak|ts[3])['s] addon is connected", "[the] addon is connect[ed] to (teamspeak|ts[3])");
		   Skript.registerExpression(ExprTSClient.class, Client.class, ExpressionType.PROPERTY, "client %string%");
		   Skript.registerExpression(ExprTSClientID.class, Integer.class, ExpressionType.PROPERTY, "(teamspeak|ts[3]) id of %client%");
		   Skript.registerExpression(ExprTSDescription.class, String.class, ExpressionType.PROPERTY, "(teamspeak|ts[3]) description of %client%"); //TODO On ne peux pas la get
		   Skript.registerExpression(ExprTSIP.class, String.class, ExpressionType.PROPERTY, "(teamspeak|ts[3]) ip of %client%"); 
		   Skript.registerExpression(ExprTSIPSList.class, Client.class, ExpressionType.PROPERTY, "(teamspeak|ts[3])[ client[s]] correspond[ing][s] to ip %string%", "(teamspeak|ts[3]) ip[s] correspond[ing][s] to %string%");
		   Skript.registerExpression(ExprTSNickname.class, String.class, ExpressionType.PROPERTY, "(teamspeak|ts[3]) (nickname|nick|name) of %client%");
		   Skript.registerExpression(ExprTSClients.class, Client.class, ExpressionType.PROPERTY, "(teamspeak|ts[3]) client[s] (online[s]|connect[ed])");
		   /*Skript.registerEvent("ts3 client leave", SimpleEvent.class, TsClientLeaveEvent.class, "(teamspeak|ts[3])[ client] (leave|quit)");
		   EventValues.registerEventValue(TsClientLeaveEvent.class, String.class, new Getter<String, TsClientLeaveEvent>() {
			   public String get(TsClientLeaveEvent e) {
				   return e.getInvoker();
			   }
		   }, 0);
		   Skript.registerEvent("ts3 client join", SimpleEvent.class, TsClientJoinEvent.class, "(teamspeak|ts[3])[ client] (join|connect)");
		   EventValues.registerEventValue(TsClientJoinEvent.class, String.class, new Getter<String, TsClientJoinEvent>() {
			   public String get(TsClientJoinEvent e) {
				   return e.getInvoker();
			   }
		   }, 0);*/
	}
	
}
