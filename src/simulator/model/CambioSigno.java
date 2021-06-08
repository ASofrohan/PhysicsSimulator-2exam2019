package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulator.control.Controller;
import simulator.misc.Vector2D;

public class CambioSigno implements SimulatorObserver{
	
	private Map<String, Integer> mapaCuerpos;
	private int _axe;
	private List<Vector2D> posicionesAntiguas;
	
	public CambioSigno(Controller ctrl, int axe) {
		_axe = axe;
		mapaCuerpos = new HashMap<>();
		posicionesAntiguas = new ArrayList<Vector2D>();
		ctrl.addObserver(this);
	}
	
	public void printHistory() {
		for(Map.Entry<String, Integer> entrada : mapaCuerpos.entrySet()) {
			String nombre = entrada.getKey();
			int n = entrada.getValue();
			System.out.println("Body: " + nombre + "  numero de cambios: " + n);
		}
	}
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		for(int i = 0; i < bodies.size(); i++) {
			posicionesAntiguas.add(bodies.get(i).getPosition());
		}
		
		for(int i = 0; i < bodies.size(); i++) {
			mapaCuerpos.put(bodies.get(i).getId(), 0);
		}
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		mapaCuerpos.clear();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		if(_axe == 0) {
			for(int i = 0; i < bodies.size(); i++) {
				if((posicionesAntiguas.get(i).getX() >= 0 && bodies.get(i).getPosition().getX() < 0) || (posicionesAntiguas.get(i).getX() < 0 && bodies.get(i).getPosition().getX() >= 0)) {
					mapaCuerpos.put(bodies.get(i).getId(), mapaCuerpos.get(bodies.get(i).getId())+1);
				}
				posicionesAntiguas.set(i, bodies.get(i).getPosition());
			}
		}
		if(_axe == 1) {
			for(int i = 0; i < bodies.size(); i++) {
				if(posicionesAntiguas.get(i).getY() >= 0 && bodies.get(i).getPosition().getY() < 0 || posicionesAntiguas.get(i).getY() < 0 && bodies.get(i).getPosition().getY() >= 0) {
					mapaCuerpos.put(bodies.get(i).getId(), mapaCuerpos.get(bodies.get(i).getId())+1);
				}
				posicionesAntiguas.set(i, bodies.get(i).getPosition());
			}
		}
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}
