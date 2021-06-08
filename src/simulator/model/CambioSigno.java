package simulator.model;


import simulator.control.Controller;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;


import simulator.misc.Vector2D;


public class CambioSigno implements SimulatorObserver {

	

	private Map<String, Integer> _miMapa;

	private List<Vector2D> posicionesAntiguas;

	private Integer _axe;


	public CambioSigno(Controller ctrl, Integer axe) {

		_miMapa = new HashMap<>();

		_axe = axe;

		posicionesAntiguas = new ArrayList<>();

		ctrl.addObserver(this);


	}


	@Override

	public void onAdvance(List<Body> bodies, double time) {

		// aquí recibo las posiciones nuevas

		

		Double coord_axe_nueva;

		Double coord_axe_antigua;

		

		for (int j = 0; j < bodies.size(); j++) {

			// para cada cuerpo cojo la coord axe

			if (_axe == 0) {

				coord_axe_nueva = bodies.get(j).getPosition().getX();

				// cojo la coord axe de la posición anterior para comparar

			  coord_axe_antigua = posicionesAntiguas.get(j).getX();

			}

			else {

				coord_axe_nueva = bodies.get(j).getPosition().getY();

			  // cojo la coord axe de la posición anterior para comparar

		    coord_axe_antigua = posicionesAntiguas.get(j).getY();

			}


			if ((coord_axe_nueva > 0 && coord_axe_antigua < 0) || (coord_axe_nueva < 0 && coord_axe_antigua > 0)) {

				int num_veces = _miMapa.get(bodies.get(j).getId());

				num_veces++;

				_miMapa.put(bodies.get(j).getId(), num_veces);

			}

		}


		// Guardo las posiciones nuevas como antiguas para comparar en el siguiente paso de simulación

		for (int i = 0; i < bodies.size(); i++) {

			posicionesAntiguas.set(i, bodies.get(i).getPosition());

		}		

	}


	@Override

	public void onReset(List<Body> bodies, double time, double dt, String forceLawsInfo) {

		// TODO Auto-generated method stub

		

	}


	@Override

	public void onRegister(List<Body> bodies, double time, double dt, String forceLawsInfo) {

		for (int j = 0; j < bodies.size(); j++) {

			posicionesAntiguas.add(bodies.get(j).getPosition());

			// inicializo miMapa con los ids de los cuerpos y 0 cambios de signo

			_miMapa.put(bodies.get(j).getId(), 0);

		}		

	}


	@Override

	public void onBodyAdded(List<Body> bodies, Body b) {

		_miMapa.clear();		

	}


	@Override

	public void onDeltaTimeChanged(double dt) {

		// TODO Auto-generated method stub

		

	}


	@Override

	public void onForceLawsChanged(String forceLawsInfo) {

		// TODO Auto-generated method stub

		

	}

	

	public void printHistory() {

		// recorro el mapa y saco la información

		for (Map.Entry<String, Integer> entrada : _miMapa.entrySet()) {

			String clave = entrada.getKey();

			Integer valor = entrada.getValue();

			System.out.println("clave= " + clave + ", valor=" + valor);

		}

	}


}