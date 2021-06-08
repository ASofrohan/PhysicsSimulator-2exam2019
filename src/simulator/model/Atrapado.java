package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Atrapado extends Body {
	protected double dist;
	
	public Atrapado(String id, Vector2D p, Vector2D v, double m, double dist) {
		super(id, p, v, m);
		this.dist = dist;
	}
	
	@Override
	void move(double t) {
		Vector2D a = new Vector2D();		//acceleration
		Vector2D newPosition = new Vector2D();
		if(mass != 0) a = new Vector2D(force.scale(1/mass));
		newPosition = position.plus(velocity.scale(t));
		newPosition = newPosition.plus(a.scale(Math.pow(t, 2)/2));
		if(newPosition.distanceTo(position) <= dist) {
			velocity = velocity.plus(a.scale(t));
			position = newPosition;
		}
	}
	
	@Override
	public JSONObject getState() {
		JSONObject joBody = new JSONObject();
		joBody.put("id", this.id);
		joBody.put("m", this.mass);
		joBody.put("p", this.position.asJSONArray());
		joBody.put("v", this.velocity.asJSONArray());
		joBody.put("f", this.force.asJSONArray());
		joBody.put("dist", this.dist);
		return joBody;
	}

	public double getDist() {
		return dist;
	}
	public void setDist(double dist) {
		this.dist = dist;
	}
}
