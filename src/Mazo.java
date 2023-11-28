import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Mazo {
    private List<Carta> cartas;

    public Mazo(){
        cartas = new ArrayList<>();
        String[] palos = { "Espadas", "Bastos", "Oros", "Copas"};
        for (String palo : palos){
            for (int numero = 1; numero <= 12; numero++){
                cartas.add (new Carta(numero,palo));
            }
        }
    }

    public void barajar(){
        Collections.shuffle(cartas);
    }

    public Carta repartir(){
        if (!cartas.isEmpty()){
            return cartas.remove(0);
        }else {
            return null;
        }
    }

    public boolean isEmpty() {
        return cartas.isEmpty(); // Verificar si la lista de cartas está vacía
    }
}
