import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jugador {
    private String nombre;
    private List<Carta> mano;
    private List<Carta> tesoro;

    private int puntos;

    public Jugador(String nombre) {
        this.nombre = nombre;
        mano = new ArrayList<>();
        tesoro = new ArrayList<>();
        puntos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Carta> getMano() {
        return mano;
    }

    public void setMano(List<Carta> mano) {
        this.mano = mano;
    }

    public List<Carta> getTesoro() {
        return tesoro;
    }

    public void setTesoro(List<Carta> tesoro) {
        this.tesoro = tesoro;
    }

    public void levantarCarta(Carta cartaMesa, Mesa mesa) {
        if (mesa.getCartasEnMesa().contains(cartaMesa)) {
            tesoro.add(cartaMesa);
            mesa.usarCarta(cartaMesa);
        }
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void agregarCartaAlTesoro(Carta carta) {
        tesoro.add(carta);
    }

    public void agregarCarta(Carta carta) {
        mano.add(carta);
    }


    public void recibirCarta(Carta carta) {
        mano.add(carta); // Agregar la carta a la mano del jugador
    }
    Scanner scanner = new Scanner(System.in);
    public void jugarCarta(Carta carta, Mesa mesa, Jugador oponente) {
        // Si la carta es válida, se agrega a la mesa y se elimina de la mano del jugador
        mesa.agregarCartaAMesa(carta);
        mano.remove(carta);

        // Si la carta no es válida, se le da la opción al oponente de soplar
        System.out.println("Quiere soplar la carta del jugador?. Presione '1' para soplar o '2' para no soplar:");

        int opcionSoplar = scanner.nextInt();
        scanner.nextLine();

        if (opcionSoplar == 1) {
            List<Carta> cartasCoincidentes = cartasCoincidentesConUltima(mesa);
            if (cartasCoincidentes.isEmpty() || cartasCoincidentes.size() ==1 ) {
                // Si el oponente decide soplar y la carta es incorrecta, se resta un punto al jugador
                oponente.setPuntos(-1);
                System.out.println("El oponente ha soplado incorrectamente. Se le resta un punto al jugador que soplo.");
            } else if (cartasCoincidentes.size() >= 2) {
                // Hay al menos 2 cartas coincidentes, hacer algo con ellas
                oponente.agregarCartasAlTesoro(cartasCoincidentes);
                mesa.eliminarCartasDeMesa(cartasCoincidentes);
                System.out.println("El jugador soplo con exito y levanta las cartas que podria haber levantado");

            }
        }
            else if (opcionSoplar == 2){
            // Si el oponente decide no soplar, el jugador original gana la mano
            System.out.println("El jugador ha decidido no soplar.");
        }

    }



    public void robarTesoro(Jugador oponente,Carta cartaMano) {
        List<Carta> pozoOponente = oponente.getTesoro();
        if (!pozoOponente.isEmpty()) {
            // Robar todo el tesoro del oponente
            tesoro.addAll(pozoOponente);
            tesoro.add(cartaMano);
            mano.remove(cartaMano);
            pozoOponente.clear(); // Limpiar el tesoro del oponente después de robarlo
        }
    }



    public void agregarCartasAlTesoro(List<Carta> cartas) {
        for (Carta carta : cartas) {
            agregarCartaAlTesoro(carta);
        }
    }



    public List<Carta> soplarUltimaCarta(Mesa mesa) {
        Carta ultimaCartaEnMesa = mesa.getUltimaCartaEnMesa(); // Obtener la última carta en la mesa
        List<Carta> cartasLevantadas = new ArrayList<>(); // Lista para almacenar las cartas que se levantan

        // Verificar si el número de la última carta en la mesa coincide con el número de otras cartas en la mesa
        for (Carta carta : mesa.getCartasEnMesa()) {
            if (carta.getNumero() == ultimaCartaEnMesa.getNumero()) {
                // Si hay coincidencia en el número, agregar la carta a la lista de cartas levantadas
                cartasLevantadas.add(carta);
            }
        }

        // Agregar la última carta jugada a las cartas levantadas
        cartasLevantadas.add(ultimaCartaEnMesa);

        return cartasLevantadas;
    }




    public void levantarCartaDeMesaConCartaMano(Carta cartaMesa, Carta cartaMano, Mesa mesa) {
        if (mano.contains(cartaMano) && mesa.getCartasEnMesa().contains(cartaMesa)) {
            System.out.println("Carta seleccionada de la mano: " + cartaMano);
            System.out.println("Carta seleccionada de la mesa: " + cartaMesa);

            tesoro.add(cartaMano); // Agregar carta de la mano al tesoro del jugador
            tesoro.add(cartaMesa); // Agregar carta de la mesa al tesoro del jugador
            mano.remove(cartaMano); // Eliminar carta de la mano del jugador
            mesa.usarCarta(cartaMesa); // Eliminar carta de la mesa
        } else {
            System.out.println("La carta seleccionada no es válida para levantar de la mesa.");
        }
    }


    public int contarPuntosAlFinal() {
        int totalPuntos = puntos; // Incluir los puntos actuales del jugador

        // Sumar puntos por las cartas en el tesoro/casita
        totalPuntos += tesoro.size();

        return totalPuntos;
    }


    public Carta mostrarCarta(int indice) {
        if (indice >= 0 && indice < mano.size()) {
            return mano.get(indice);
        } else {
            System.out.println("Índice de carta no válido.");
            return null;
        }
    }


    public List<Carta> cartasCoincidentesConUltima(Mesa mesa) {
        List<Carta> cartasEnMesa = mesa.getCartasEnMesa();
        int tamaño = cartasEnMesa.size();

        List<Carta> cartasCoincidentes = new ArrayList<>();

        if (tamaño < 2) {
            return cartasCoincidentes; // Si hay una o ninguna carta en la mesa, no hay coincidencia
        }

        Carta ultimaCarta = cartasEnMesa.get(tamaño - 1);
        int numeroCarta = ultimaCarta.getNumero();

        for (int i = 0; i < tamaño ; i++) {
            Carta cartaMesa = cartasEnMesa.get(i);
            if (cartaMesa.getNumero() == numeroCarta) {
                cartasCoincidentes.add(cartaMesa); // Agregar la carta coincidente a la lista
            }
        }

        return cartasCoincidentes; // Devolver la lista de cartas coincidentes
    }



}
