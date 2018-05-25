package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExprReturnOfMethodWithParams extends SimpleExpression<Object> {
	
	private Expression<String> className;
	private Expression<String> functionName;
	private Expression<Object> params;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		functionName = (Expression<String>) expr[0];
		params = (Expression<Object>) expr[1];
		className = (Expression<String>) expr[2];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "return of method";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Nullable
	protected Object[] get(Event e) {
		try {
			Class thisClass = Class.forName(className.getSingle(e));
			Object iClass = thisClass.newInstance();
			Class[] paramList = new Class[params.getAll(e).length];
			for(int i=0; i<params.getAll(e).length; i++){
				paramList[i] = params.getAll(e)[i].getClass();
			}
			Method thisMethod = thisClass.getDeclaredMethod(functionName.getSingle(e), paramList);
			return new Object[] { thisMethod.invoke(iClass, params.getAll(e)) };
		} catch (ClassNotFoundException e1) {
			WolvSK.getInstance().getLogger().warning("Error in a Skript: Class " + className.getSingle(e) + " not found!");
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			WolvSK.getInstance().getLogger().warning("Error in a Skript with Class " + className.getSingle(e));
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			WolvSK.getInstance().getLogger().warning("Error in a Skript with Class " + className.getSingle(e));
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			WolvSK.getInstance().getLogger().warning("Error in a Skript: The function " + functionName + " doesn't exist!");
			e1.printStackTrace();
		} catch (SecurityException e1) {
			WolvSK.getInstance().getLogger().warning("Error in a Skript: The function " + functionName + " is protected!");
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			WolvSK.getInstance().getLogger().warning("Error in a Skript: Arguments of function " + functionName + " are incorrect!");
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			WolvSK.getInstance().getLogger().warning("Error in a Skript with Class " + className.getSingle(e));
			e1.printStackTrace();
		}
		return null;
	}
}

