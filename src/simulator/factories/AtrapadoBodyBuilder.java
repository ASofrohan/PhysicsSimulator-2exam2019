package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Atrapado;
import simulator.model.Body;

public class AtrapadoBodyBuilder extends Builder<Body>{
	public AtrapadoBodyBuilder() {
		super("ab", "Atrapado Builder");
	}
	
	@Override
	protected Body createTheInstance(JSONObject jo) {
		
		if(!jo.has("id") || !jo.has("p") || !jo.has("v") || !jo.has("m") || !jo.has("dist")) {
			throw new IllegalArgumentException("Faltan valores para crear los cuerpos");
		}
		
		String id = jo.getString("id");
		JSONArray vector = jo.getJSONArray("p");
		Vector2D p = new Vector2D(vector.getDouble(0), vector.getDouble(1));
		vector = jo.getJSONArray("v");
		Vector2D v = new Vector2D(vector.getDouble(0), vector.getDouble(1));
		double m = jo.getDouble("m");
		if(m <= 0) {
			throw new IllegalArgumentException("Masa menor o igual que cero");
		}
		double dist = jo.getDouble("dist");
		if(dist <= 0) {
			throw new IllegalArgumentException("Distancia menor o igual que cero");
		}
		return new Atrapado(id, p, v, m, dist);
	}

	@Override
	protected JSONObject getBuilderData() {
	
		JSONObject JASON = new JSONObject();
		JASON.put("id", "string que identifica al objeto");
		JASON.put("p", "posicion del objeto");		
		JASON.put("v", "velocidad del objeto");
		JASON.put("m", "masa del objeto");
		JASON.put("dist", "distancia maxima del cuerpo");			
		
		return JASON;
	}
}
