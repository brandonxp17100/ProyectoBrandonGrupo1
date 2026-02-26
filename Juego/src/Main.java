import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        HashMap<String, Integer> stats = new HashMap<>();
        stats.put("danoTotal", 0);
        stats.put("curacionTotal", 0);
        stats.put("turnosJugados", 0);
        stats.put("enemigosDerrotados", 0);

        Interfaz.titulo("RPG de Consola: Arena por Turnos");
        System.out.print("Nombre del jugador: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) nombre = "Jugador";

        Personaje jugador = elegirClase(sc, nombre, stats);

        ArrayList<Item> inventario = new ArrayList<>();
        inventario.add(new Pocion(10));
        inventario.add(new Antidoto());

        int[] oroRef = new int[]{20};

        int ronda = 1;
        while (jugador.estaVivo()) {

        Interfaz.titulo("RONDA " + ronda);
        System.out.println(jugador);
        System.out.println("Oro: " + oroRef[0] + " | Items: " + inventario.size());
        Interfaz.separador();

            Enemigo enemigo = FabricaEnemigos.crearEnemigo(jugador.getNivel());

            Combate combate = new Combate(sc, jugador, enemigo, inventario, stats);
            boolean victoria = combate.iniciar();

            if (!victoria) {
                System.out.println("\nVictoria!. Estadísticas:");
                imprimirStats(stats);
                break;
            }

            int recompensa = 8 + jugador.getNivel() * 2;
            oroRef[0] += recompensa;
            System.out.println("Ganas " + recompensa + " oro.");

            jugador.subirNivel();
            System.out.println("¡Subiste a nivel " + jugador.getNivel() + "! Nuevos stats: " + jugador);

            System.out.println("\n¿Ir a la tienda? (1=Sí, 0=No)");
            int ir = leerIntSeguro(sc, "Elige: ");
            if (ir == 1) {
                Tienda.abrir(sc, inventario, oroRef);
            }

            ronda++;
        }

        sc.close();
    }

    private static Personaje elegirClase(Scanner sc, String nombre, HashMap<String,Integer> stats) {
        while (true) {
            System.out.println("\nElige clase:");
            System.out.println("1) Guerrero (Mega Golpe)");
            System.out.println("2) Mago (Niebla Veneno)");
            System.out.println("3) Arquero (Flecha Incendiaria)");
            int op = leerIntSeguro(sc, "Opción: ");
            return switch (op) {
                case 1 -> new Guerrero(nombre, stats);
                case 2 -> new Mago(nombre, stats);
                case 3 -> new Arquero(nombre, stats);
                default -> {
                    System.out.println("Opción inválida.");
                    yield null;
                }
            };
        }
    }

    private static void imprimirStats(HashMap<String,Integer> stats) {
        System.out.println("Daño total: " + stats.getOrDefault("danoTotal", 0));
        System.out.println("Curación total: " + stats.getOrDefault("curacionTotal", 0));
        System.out.println("Turnos jugados: " + stats.getOrDefault("turnosJugados", 0));
        System.out.println("Enemigos derrotados: " + stats.getOrDefault("enemigosDerrotados", 0));
    }

    private static int leerIntSeguro(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = sc.nextLine();
                return Integer.parseInt(line.trim());
            } catch (Exception e) {
                System.out.println("Entrada inválida. Intenta de nuevo.");
            }
        }
    }
}