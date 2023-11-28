import java.util.List;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        Mesa mesa = new Mesa();
        Jugador jugador1 = new Jugador("luis");
        Jugador jugador2 = new Jugador("pablo");
        Mazo baraja = new Mazo();
        baraja.barajar();

        // Repartir cartas a los jugadores
        while (jugador1.getMano().size() < 3) {
            Carta cartaRepartida = baraja.repartir();
            if (cartaRepartida != null) {
                jugador1.agregarCarta(cartaRepartida);
            } else {
                break; // Si el mazo está vacío, se sale del bucle
            }
        }

        while (jugador2.getMano().size() < 3) {
            Carta cartaRepartida = baraja.repartir();
            if (cartaRepartida != null) {
                jugador2.agregarCarta(cartaRepartida);
            } else {
                break; // Si el mazo está vacío, se sale del bucle
            }
        }



        Scanner scanner = new Scanner(System.in);
        boolean juegoEnCurso = true;
        boolean turnoJugador1 = true; // Variable para alternar los turnos entre los jugadores
        boolean primeraronda =true;
        System.out.println("Comienza el juego");
        while (juegoEnCurso) {
            if (baraja.isEmpty() && (jugador1.getMano().isEmpty() || jugador1.getMano().size() <=2 ) && (jugador2.getMano().isEmpty() || jugador2.getMano().size() <=2))  {
                int puntosJugador1 = jugador1.contarPuntosAlFinal();
                int puntosJugador2 = jugador2.contarPuntosAlFinal();
                juegoEnCurso = false;
                System.out.println("Puntos del Jugador 1: " + puntosJugador1);
                System.out.println("Puntos del Jugador 2: " + puntosJugador2);

                if (puntosJugador1 > puntosJugador2) {
                    int puntosDiferencia = puntosJugador1 - puntosJugador2;
                    System.out.println("¡El jugador 1 gana por " + puntosDiferencia + " puntos!");
                } else if (puntosJugador2 > puntosJugador1) {
                    int puntosDiferencia = puntosJugador2 - puntosJugador1;
                    System.out.println("¡El jugador 2 gana por " + puntosDiferencia + " puntos!");
                } else {
                    System.out.println("¡Hubo un empate!");
                }
                System.out.println("¡Termino el juego!");
                break;
            }
            if (turnoJugador1) {
                System.out.println("Turno de Jugador 1");

                if (jugador1.getMano().isEmpty() && jugador2.getMano().isEmpty())  {
                    while (jugador1.getMano().size() < 3) {
                        Carta cartaRepartida = baraja.repartir();
                        if (cartaRepartida != null) {
                            jugador1.agregarCarta(cartaRepartida);
                        } else {
                            break; // Si el mazo está vacío, se sale del bucle
                        }
                    }

                    while (jugador2.getMano().size() < 3) {
                        Carta cartaRepartida = baraja.repartir();
                        if (cartaRepartida != null) {
                            jugador2.agregarCarta(cartaRepartida);
                        } else {
                            break; // Si el mazo está vacío, se sale del bucle
                        }
                    }
                }
                if (primeraronda) {
                    while (mesa.getCartasEnMesa().size() < 4) {
                        Carta cartaRepartida = baraja.repartir();
                        primeraronda=false;
                        if (cartaRepartida != null) {
                            mesa.agregarCarta(cartaRepartida);
                        } else {
                            break;
                        }
                    }
                }

                List<Carta> casitaJugador1 = jugador1.getTesoro();
                List<Carta> casitaJugador2 = jugador2.getTesoro();


                // Verificar si hay al menos una carta en el tesoro antes de intentar acceder a la última carta
                if (!casitaJugador1.isEmpty()) {
                    Carta ultimaCarta = casitaJugador1.get(casitaJugador1.size() - 1);
                    System.out.println("Última carta en el tesoro del Jugador 1: " + ultimaCarta);
                } else {
                    System.out.println("La casita del Jugador 1 está vacío.");
                }

                // Verificar si hay al menos una carta en el tesoro antes de intentar acceder a la última carta
                if (!casitaJugador2.isEmpty()) {
                    Carta ultimaCarta = casitaJugador2.get(casitaJugador2.size() - 1);
                    System.out.println("Última carta en el tesoro del Jugador 2: " + ultimaCarta);
                } else {
                    System.out.println("La casita del Jugador 2 está vacío.");
                }

                System.out.println("Mano de Jugador 1: " + jugador1.getMano());
                System.out.println("Cartas en la mesa: " + mesa.getCartasEnMesa());
                //System.out.println("Casita de Jugador 1: " + jugador1.getTesoro());
                //System.out.println("Casita de Jugador 2: " + jugador2.getTesoro());

                System.out.println("Elige qué carta dejar en la mesa:");

                // Mostrar las cartas disponibles en la mano del jugador para elegir
                for (int i = 0; i < jugador1.getMano().size(); i++) {
                    System.out.println((i + 1) + " -> " + jugador1.getMano().get(i));
                }

                System.out.println("'4' para levantar una carta, '5' para robar la casita del otro jugador o '0' para pasar el turno para el otro jugador:");
                int error=0;
                int opcion = -1;
                while (opcion < 0 || opcion > 5) {
                    opcion = scanner.nextInt();
                    if (opcion == 0) {
                        System.out.println("Cambio de turno...");
                        turnoJugador1 = !turnoJugador1; // Cambio de turno al otro jugador


                    } else if (opcion >= 1 && opcion <= jugador1.getMano().size()) {
                        Carta cartaSeleccionada = jugador1.getMano().get(opcion - 1);
                        jugador1.jugarCarta(cartaSeleccionada, mesa, jugador2);
                    } else if (opcion == 4) {
                        System.out.println("Cartas en la mesa: " + mesa.getCartasEnMesa());
                        System.out.println("Elige una carta de la mesa para levantar:");
                        int cartaMesa = scanner.nextInt();
                        System.out.println("Elige una carta de tu mano para usar:");
                        int cartaMano = scanner.nextInt();
                        if (cartaMano >= 1 && cartaMano <= jugador1.getMano().size() && cartaMesa >= 1 && cartaMesa <= mesa.getCartasEnMesa().size()) {
                            Carta cartaSeleccionadaMesa = mesa.getCartasEnMesa().get(cartaMesa - 1);
                            Carta cartaSeleccionadaMano = jugador1.getMano().get(cartaMano - 1);
                            // Validar si las cartas son del mismo número
                            if (cartaSeleccionadaMesa.getNumero() == cartaSeleccionadaMano.getNumero()) {
                                jugador1.levantarCartaDeMesaConCartaMano(cartaSeleccionadaMesa, cartaSeleccionadaMano, mesa);
                                System.out.println("Has levantado la carta de la mesa: " + cartaSeleccionadaMesa + " con tu carta de mano: " + cartaSeleccionadaMano);
                            } else {
                                System.out.println("Las cartas seleccionadas no tienen el mismo número.");
                                error=1;
                            }
                        } else {
                            System.out.println("Número de carta inválido.");
                        }
                    } else if (opcion == 5) {
                        if (jugador1.getMano().size() > 0) {
                            // Obtener la carta superior de la casita del otro jugador si existe
                            Carta ultimaCarta = casitaJugador2.get(casitaJugador2.size() - 1);

                            // Verificar si hay carta en la casita del otro jugador
                            if (ultimaCarta != null) {
                                System.out.println("Carta superior en la casita del otro jugador: " + ultimaCarta);
                                System.out.println("Elige una carta de tu mano para robar el tesoro:");

                                int cartaRobo = scanner.nextInt();
                                if (cartaRobo >= 1 && cartaRobo <= jugador1.getMano().size()) {
                                    Carta cartaSeleccionadaMano = jugador1.getMano().get(cartaRobo - 1);

                                    // Validar si las cartas son del mismo número
                                    if (cartaSeleccionadaMano.getNumero() == ultimaCarta.getNumero()) {
                                        jugador1.robarTesoro(jugador2, cartaSeleccionadaMano);
                                        System.out.println("Has robado todo el tesoro del otro jugador.");
                                    } else {
                                        System.out.println("La carta seleccionada no tiene el mismo número que la carta superior de la casita del otro jugador.");
                                    }
                                } else {
                                    System.out.println("Número de carta inválido.");
                                    error =1;
                                }
                            } else {
                                System.out.println("No hay carta en la casita del otro jugador para robar.");
                                error = 1;
                            }
                        } else {
                            System.out.println("No hay cartas en tu mano para robar el tesoro del otro jugador.");
                        }
                    } else {
                        System.out.println("Opción no válida. Elige una opción válida.");
                        error = 1;
                    }

                    turnoJugador1 = false; // Cambiar al turno del Jugador 2
                    if (error == 1){
                        System.out.println("Se repite turno");
                        turnoJugador1 = true;
                    }
                }
            } else {
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
                System.out.println("Turno de Jugador 2");
                if (mesa.getCartasEnMesa().isEmpty() && jugador1.getMano().isEmpty() && jugador2.getMano().isEmpty()) {
                    while (mesa.getCartasEnMesa().size() < 4) {
                        Carta cartaRepartida = baraja.repartir();
                        if (cartaRepartida != null) {
                            mesa.agregarCarta(cartaRepartida);
                        } else {
                            break;
                        }
                    }
                }

                List<Carta> casitaJugador1 = jugador1.getTesoro();
                List<Carta> casitaJugador2 = jugador2.getTesoro();


                // Verificar si hay al menos una carta en el tesoro antes de intentar acceder a la última carta
                if (!casitaJugador1.isEmpty()) {
                    Carta ultimaCarta = casitaJugador1.get(casitaJugador1.size() - 1);
                    System.out.println("Última carta en el tesoro del Jugador 1: " + ultimaCarta);
                } else {
                    System.out.println("La casita del Jugador 1 está vacío.");
                }

                // Verificar si hay al menos una carta en el tesoro antes de intentar acceder a la última carta
                if (!casitaJugador2.isEmpty()) {
                    Carta ultimaCarta = casitaJugador2.get(casitaJugador2.size() - 1);
                    System.out.println("Última carta en el tesoro del Jugador 2: " + ultimaCarta);
                } else {
                    System.out.println("La casita del Jugador 2 está vacío.");
                }

                System.out.println("Mano de Jugador 2: " + jugador2.getMano());
                System.out.println("Cartas en la mesa: " + mesa.getCartasEnMesa());
                //System.out.println("Casita de Jugador 1: " + jugador1.getTesoro());
                //System.out.println("Casita de Jugador 2: " + jugador2.getTesoro());

                System.out.println("Elige qué carta dejar en la mesa:");

                // Mostrar las cartas disponibles en la mano del jugador para elegir
                for (int i = 0; i < jugador2.getMano().size(); i++) {
                    System.out.println((i + 1) + " -> " + jugador2.getMano().get(i));
                }

                System.out.println("'4' para levantar una carta, '5' para robar la casita del otro jugador o '0' para pasar el turno para el otro jugador:");


                int error=0;
                int opcion2=-1;


                while (opcion2 < 0 || opcion2 > 5) {
                    opcion2 = scanner.nextInt();
                    if (opcion2 == 0) {
                        System.out.println("Cambio de turno...");
                        turnoJugador1 = !turnoJugador1; // Cambio de turno al otro jugador


                    } else if (opcion2 >= 1 && opcion2 <= jugador2.getMano().size()) {
                        Carta cartaSeleccionada = jugador2.getMano().get(opcion2 - 1);
                        jugador2.jugarCarta(cartaSeleccionada, mesa, jugador1);
                    } else if (opcion2 == 4) {
                        System.out.println("Cartas en la mesa: " + mesa.getCartasEnMesa());
                        System.out.println("Elige una carta de la mesa para levantar:");
                        int cartaMesa = scanner.nextInt();
                        System.out.println("Elige una carta de tu mano para usar:");
                        int cartaMano = scanner.nextInt();
                        if (cartaMano >= 1 && cartaMano <= jugador2.getMano().size() && cartaMesa >= 1 && cartaMesa <= mesa.getCartasEnMesa().size()) {
                            Carta cartaSeleccionadaMesa = mesa.getCartasEnMesa().get(cartaMesa - 1);
                            Carta cartaSeleccionadaMano = jugador2.getMano().get(cartaMano - 1);
                            // Validar si las cartas son del mismo número
                            if (cartaSeleccionadaMesa.getNumero() == cartaSeleccionadaMano.getNumero()) {
                                jugador2.levantarCartaDeMesaConCartaMano(cartaSeleccionadaMesa, cartaSeleccionadaMano, mesa);
                                System.out.println("Has levantado la carta de la mesa: " + cartaSeleccionadaMesa + " con tu carta de mano: " + cartaSeleccionadaMano);
                            } else {
                                System.out.println("Las cartas seleccionadas no tienen el mismo número.");
                                error=1;
                            }
                        } else {
                            System.out.println("Número de carta inválido.");
                        }
                    } else if (opcion2 == 5) {
                        if (jugador2.getMano().size() > 0) {
                            // Obtener la carta superior de la casita del otro jugador si existe
                            Carta ultimaCarta = casitaJugador1.get(casitaJugador1.size() - 1);

                            // Verificar si hay carta en la casita del otro jugador
                            if (ultimaCarta != null) {
                                System.out.println("Carta superior en la casita del otro jugador: " + ultimaCarta);
                                System.out.println("Elige una carta de tu mano para robar el tesoro:");

                                int cartaRobo = scanner.nextInt();
                                if (cartaRobo >= 1 && cartaRobo <= jugador2.getMano().size()) {
                                    Carta cartaSeleccionadaMano = jugador2.getMano().get(cartaRobo - 1);

                                    // Validar si las cartas son del mismo número
                                    if (cartaSeleccionadaMano.getNumero() == ultimaCarta.getNumero()) {
                                        jugador2.robarTesoro(jugador1, cartaSeleccionadaMano);
                                        System.out.println("Has robado todo el tesoro del otro jugador.");
                                    } else {
                                        System.out.println("La carta seleccionada no tiene el mismo número que la carta superior de la casita del otro jugador.");
                                    }
                                } else {
                                    System.out.println("Número de carta inválido.");
                                    error =1;
                                }
                            } else {
                                System.out.println("No hay carta en la casita del otro jugador para robar.");
                                error = 1;
                            }
                        } else {
                            System.out.println("No hay cartas en tu mano para robar el tesoro del otro jugador.");
                        }
                    } else {
                        System.out.println("Opción no válida. Elige una opción válida.");
                        error = 1;
                    }
                    turnoJugador1 = true;
                    for (int i = 0; i < 50; i++) {
                        System.out.println();
                    }
                     // Cambiar al turno del Jugador 1
                    if (error == 1){
                        System.out.println("Se repite turno");
                        turnoJugador1 = false;
                    }
                }
            }


           /*if (baraja.isEmpty() && jugador1.getMano().isEmpty() && jugador2.getMano().isEmpty()) {
                int puntosJugador1 = jugador1.contarPuntosAlFinal();
                int puntosJugador2 = jugador2.contarPuntosAlFinal();
                juegoEnCurso = false;
                System.out.println("Puntos del Jugador 1: " + puntosJugador1);
                System.out.println("Puntos del Jugador 2: " + puntosJugador2);

                if (puntosJugador1 > puntosJugador2) {
                    int puntosDiferencia = puntosJugador1 - puntosJugador2;
                    System.out.println("¡El jugador 1 gana por " + puntosDiferencia + " puntos!");
                } else if (puntosJugador2 > puntosJugador1) {
                    int puntosDiferencia = puntosJugador2 - puntosJugador1;
                    System.out.println("¡El jugador 2 gana por " + puntosDiferencia + " puntos!");
                } else {
                    System.out.println("¡Hubo un empate!");
                }
            }*/

        }

        scanner.close();

    }

}
