package fantasticBits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

class Entidad {
	int id;
	int x;
	int y;
	int vx;
	int vy;

	public Entidad(int idE, int xE, int yE, int vxE, int vyE) {

		id = idE;
		x = xE;
		y = yE;
		vx = vxE;
		vy = vyE;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

}

class Jugador extends Entidad {
	int hasSnaffle;

	public Jugador(int idE, int xE, int yE, int vxE, int vyE, int hasSnaffleE) {
		super(idE, xE, yE, vxE, vyE);
		hasSnaffle = hasSnaffleE;
	}

	public int getHasSnaffle() {
		return hasSnaffle;
	}

	public void setHasSnaffle(int hasSnaffle) {
		this.hasSnaffle = hasSnaffle;
	}
}

class Snaffle extends Entidad {
	public Snaffle(int idE, int xE, int yE, int vxE, int vyE) {
		super(idE, xE, yE, vxE, vyE);
	}
}

class Enemigo extends Entidad {
	int hasSnaffle;

	public Enemigo(int idE, int xE, int yE, int vxE, int vyE, int hasSnaffleE) {
		super(idE, xE, yE, vxE, vyE);
		hasSnaffle = hasSnaffleE;
	}

	public int getHasSnaffle() {
		return hasSnaffle;
	}

	public void setHasSnaffle(int hasSnaffle) {
		this.hasSnaffle = hasSnaffle;
	}
}

/**
 * Grab Snaffles and try to throw them through the opponent's goal! Move towards
 * a Snaffle and use your team id to determine where you need to throw it.
 **/
class Player {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int myTeamId = in.nextInt(); // if 0 you need to score on the right of
										// the map, if 1 you need to score on
										// the left
		List<Jugador> jugadores = new ArrayList<>();
		List<Snaffle> snaffles = new ArrayList<>();
		List<Enemigo> enemigos = new ArrayList<>();
		Integer centroPorteriaX;
		Integer centroPorteriaY;
		Integer potencia = 500;
		if (myTeamId == 0) {
			centroPorteriaX = 16000;
			centroPorteriaY = 3750;
		} else {
			centroPorteriaX = 0;
			centroPorteriaY = 3750;
		}

		// game loop
		while (true) {
			int entities = in.nextInt(); // number of entities still in game
			for (int i = 0; i < entities; i++) {
				int entityId = in.nextInt(); // entity identifier
				String entityType = in.next(); // "WIZARD", "OPPONENT_WIZARD" or
												// "SNAFFLE" (or "BLUDGER" after
												// first league)
				int x = in.nextInt(); // position
				int y = in.nextInt(); // position
				int vx = in.nextInt(); // velocity
				int vy = in.nextInt(); // velocity
				int state = in.nextInt(); // 1 if the wizard is holding a
											// Snaffle, 0 otherwise

				if (entityType.equals("WIZARD")) {

					Jugador jugador = new Jugador(jugadores.size() + 1, x, y, vx, vy, state);
					jugadores.add(jugador);

				} else if (entityType.equals("OPPONENT_WIZARD")) {

					Enemigo enemigo = new Enemigo(enemigos.size() + 1, x, y, vx, vy, state);
					enemigos.add(enemigo);
				} else if (entityType.equals("SNAFFLE")) {

					Snaffle snaffle = new Snaffle(snaffles.size() + 1, x, y, vx, vy);
					snaffles.add(snaffle);
				}
			}

			System.err.println("Hay " + jugadores.size() + " jugadores");
			System.err.println("Hay " + enemigos.size() + " enemigos");
			System.err.println("Hay " + snaffles.size() + " snaffles");

			for (int i = 0; i < 2; i++) {

				Jugador jugador = jugadores.get(i);
				Map<Integer, Double> distanciasConIdSnaffle = new HashMap<>();
				Map<Integer, Double> distanciasConIdSnaffleORDER = new TreeMap<>(Collections.reverseOrder());

				for (Snaffle snaffle : snaffles) {
					distanciasConIdSnaffle.put(snaffle.getId(), calculateDistanceOfTwoEntities(jugador, snaffle));
					distanciasConIdSnaffleORDER.put(snaffle.getId(), calculateDistanceOfTwoEntities(jugador, snaffle));
				}

				Map<Integer, Double> distanciasConIdSnaffleOrdenadas = sortByValue(distanciasConIdSnaffle);
				int idSnaffleMasCercana = 0;
				for (Entry<Integer, Double> distanciasConIdSnaffleOrdenadasEntry : distanciasConIdSnaffleOrdenadas
						.entrySet()) {
					idSnaffleMasCercana = distanciasConIdSnaffleOrdenadasEntry.getKey();
					break;
				}
				Snaffle snaffleMasCercana = snaffles.get(idSnaffleMasCercana);

				String movimiento = "";

				if (jugador.getHasSnaffle() == 1) {
					movimiento = "THROW " + centroPorteriaX + " " + centroPorteriaY + " " + potencia;
				} else {
					movimiento = "MOVE " + snaffleMasCercana.getX() + " " + snaffleMasCercana.getY() + " 150";
				}

				// Write an action using System.out.println()
				// To debug: System.err.println("Debug messages...");

				// Edit this line to indicate the action for each wizard (0 <=
				// thrust <= 150, 0 <= power <= 500)
				// i.e.: "MOVE x y thrust" or "THROW x y power"
				System.out.println(movimiento);
			}
			jugadores = new ArrayList<>();
			snaffles = new ArrayList<>();
			enemigos = new ArrayList<>();
		}
	}

	static double calculateDistanceOfTwoEntities(Entidad entidad1, Entidad entidad2) {
		double distance = 0;
		int restaX = (entidad2.getX() - entidad1.getX());
		int restaY = (entidad2.getY() - entidad1.getY());

		distance = Math.sqrt(Math.pow(restaX - restaY, 2));
		return distance;
	}

	// JAVA 7
	// public static <K, V extends Comparable<? super V>> Map<K, V>
	// sortByValue(Map<K, V> map) {
	// List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
	// Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
	// @Override
	// public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
	// return (o1.getValue()).compareTo(o2.getValue());
	// }
	// });
	//
	// Map<K, V> result = new LinkedHashMap<>();
	// for (Map.Entry<K, V> entry : list) {
	// result.put(entry.getKey(), entry.getValue());
	// }
	// return result;
	// }

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return map.entrySet().stream()
				.sorted(Map.Entry
						.comparingByValue(/* Collections.reverseOrder() */))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
}
