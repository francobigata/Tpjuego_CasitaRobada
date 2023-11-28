import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistoriaDeJuego {
    private Mazo mazo;
    private List<Jugador> jugadores;
    private Mesa mesa;



    public HistoriaDeJuego() {
        mazo = new Mazo();
        mazo.barajar();

        jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Jugador 1"));
        jugadores.add(new Jugador("Jugador 2"));

        mesa = new Mesa();
        repartirCartas();
    }

    private void repartirCartas() {
        int cantidadCartasPorJugador = 3;
        for (int i = 0; i < cantidadCartasPorJugador; i++) {
            for (Jugador jugador : jugadores) {
                Carta carta = mazo.repartir();
                if (carta != null) {
                    jugador.recibirCarta(carta);
                } else {
                    System.out.println("No hay suficientes cartas en el mazo.");
                    break;
                }
            }
        }
    }




    private boolean verificarFinJuego() {
        if (mazo.isEmpty()) {
            return true;
        }

        for (Jugador jugador : jugadores) {
            if (jugador.getPuntos() >= 30) {
                return true;
            }
        }
        return false;
    }

    private void declararGanador() {
        Jugador ganador = jugadores.get(0);
        for (Jugador jugador : jugadores) {
            if (jugador.getPuntos() > ganador.getPuntos()) {
                ganador = jugador;
            }
        }
        System.out.println("El ganador es: " + ganador.getNombre());
    }
}