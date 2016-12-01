package fantasticBits;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Jugador {
	int id;
	int x;
	int y;
	int vx;
	int vy;
	int hasSnaffle;

	public Jugador(int idE, int xE, int yE, int vxE, int vyE, int hasSnaffleE) {

		id = idE;
		x = xE;
		y = yE;
		vx = vxE;
		vy = vyE;
		hasSnaffle = hasSnaffleE;

	}

	int getHasSnaffle() {
		return hasSnaffle;
	}
}

class Snaffle {
	int id;
	int x;
	int y;
	int vx;
	int vy;
	int state;

	public Snaffle(int idE, int xE, int yE, int vxE, int vyE, int stateE) {

		id = idE;
		x = xE;
		y = yE;
		vx = vxE;
		vy = vyE;
		state = stateE;
	}
}

class Enemigo {
	int id;
	int x;
	int y;
	int vx;
	int vy;
	int state;

	public Enemigo(int idE, int xE, int yE, int vxE, int vyE, int stateE) {

		id = idE;
		x = xE;
		y = yE;
		vx = vxE;
		vy = vyE;
		state = stateE;
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

					Snaffle snaffle = new Snaffle(snaffles.size() + 1, x, y, vx, vy, state);
					snaffles.add(snaffle);
				}
			}

			for (int i = 0; i < 2; i++) {
				System.err.println("tama�o del jugadores" + jugadores.size());
				Jugador jugador = jugadores.get(i);
				String movimiento = "MOVE 8000 3750 100";

				if (jugador.getHasSnaffle() == 1) {
					movimiento = "THROW " + centroPorteriaX + " " + centroPorteriaY + " " + potencia;
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
}