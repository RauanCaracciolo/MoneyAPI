package controller.chain;

import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class HandlerFactory {
	public static Handler createChain() throws Exception{
		InputStreamReader reader = new InputStreamReader(HandlerFactory.class.getClassLoader().getResourceAsStream("chain-config.json"));
		JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
		JsonArray array = json.getAsJsonArray();
		List<Handler> handlers = new ArrayList<>();
		for(JsonElement el : array) {
			String className =  el.getAsString();
			Class<?> class_ = Class.forName(className);
			Constructor<?> constructor = class_.getDeclaredConstructor();
			Handler handler = (Handler) constructor.newInstance();
			handlers.add(handler);
		}
		for(int i = 0; i < handlers.size() -1; i++) {
			handlers.get(i).setNext(handlers.get(i+1));
		}
		return handlers.get(0);
		
	}
}
