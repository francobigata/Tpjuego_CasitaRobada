import java.util.ArrayList;
import java.util.List;

class Mesa {
    private List<Carta> cartasEnMesa;

    public Mesa() {
        this.cartasEnMesa = new ArrayList<>();
    }

    public void agregarCarta(Carta carta) {
        cartasEnMesa.add(carta);
    }

    public List<Carta> getCartasEnMesa() {
        return cartasEnMesa;
    }

    public void limpiarMesa() {
        cartasEnMesa.clear();
    }

    // Método para agregar una carta a la mesa
    public void agregarCartaAMesa(Carta carta) {
        cartasEnMesa.add(carta);
    }


    public Carta usarCarta(Carta cartaJugada) {
        Carta cartaMesa = null;
        for (Carta carta : cartasEnMesa) {
            if (carta.getNumero() == cartaJugada.getNumero() || carta.getPalo().equals(cartaJugada.getPalo())) {
                cartaMesa = carta;
                break;
            }
        }

        if (cartaMesa != null) {
            cartasEnMesa.remove(cartaMesa);
        }

        return cartaMesa;
    }

    public Carta getUltimaCartaEnMesa() {
        if (!cartasEnMesa.isEmpty()) {
            int indiceUltimaCarta = cartasEnMesa.size() - 1;
            return cartasEnMesa.get(indiceUltimaCarta);
        }
        return null;
    }

    public void eliminarCartasDeMesa(List<Carta> cartas) {
        cartasEnMesa.removeAll(cartas);
    }

    public List<Carta> getCartasJugadas() {
        return cartasEnMesa;
    }

    public boolean cartaPuedeSerLigada(Carta carta) {
        int numeroCarta = carta.getNumero();
        for (Carta cartaMesa : cartasEnMesa) {
            if (cartaMesa.getNumero() == numeroCarta) {
                return true; // La carta se puede ligar con una de la mesa
            }
        }
        return false; // La carta no se puede ligar con ninguna de la mesa
    }
}






