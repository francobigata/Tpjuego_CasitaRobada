public class Carta {
    private int numero;
    private String palo;

    public Carta(int numero, String palo){
        this.numero = numero;
        this.palo = palo;

    }


    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public boolean esIgual(Carta otraCarta){
        return this.numero == otraCarta.numero && this.palo.equals(otraCarta.palo);
    }

    @Override
    public String toString(){
        return numero + " de " + palo;
    }
}
