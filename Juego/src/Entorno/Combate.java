import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Combate {
    private final Scanner sc;
    private final Personaje jugador;
    private final Enemigo enemigo;
    private final ArrayList<Item> inventario;
    private final HashMap<String, Integer> stats;

    private int defBonus = 0;
    private int turnosDefBonus = 0;

    public Combate(Scanner sc, Personaje jugador, Enemigo enemigo, ArrayList<Item> inventario, HashMap<String,Integer> stats) {
        this.sc = sc;
        this.jugador = jugador;
        this.enemigo = enemigo;
        this.inventario = inventario;
        this.stats = stats;
    }

    public boolean iniciar() {
        System.out.println("\n=== ¡COMBATE! Apareció " + enemigo.getNombre() + " ===");

        while (jugador.estaVivo() && enemigo.estaVivo()) {
            stats.put("turnosJugados", stats.getOrDefault("turnosJugados", 0) + 1);

            System.out.println(jugador.procesarEfectosPorTurno());
            System.out.println(enemigo.procesarEfectosPorTurno());

            if (!jugador.estaVivo() || !enemigo.estaVivo()) break;

            if (turnosDefBonus > 0) {
                turnosDefBonus--;
                if (turnosDefBonus == 0) defBonus = 0;
            }

            imprimirEstado();

            Interfaz.caja("1) Atacar   2) Habilidad   3) Usar Item   4) Rendirse");
            int op = leerIntSeguro("Elige: ");
            switch (op) {
                case 1 -> accionAtacar();
                case 2 -> accionHabilidad();
                case 3 -> accionItem();
                case 4 -> {
                    System.out.println("Game Over.");
                    return false;
                }
                default -> System.out.println("Opción inválida.");
            }

            if (!enemigo.estaVivo()) break;

            int defOriginal = jugador.defensa;
            jugador.defensa = defOriginal + defBonus;

            System.out.println(">> " + enemigo.jugarTurno(jugador));

            jugador.defensa = defOriginal;

            if (!jugador.estaVivo()) break;
        }

        if (jugador.estaVivo()) {
            System.out.println("¡Victoria! Derrotaste a " + enemigo.getNombre());
            stats.put("enemigosDerrotados", stats.getOrDefault("enemigosDerrotados", 0) + 1);
            return true;
        } else {
            System.out.println("Fuiste derrotado...");
            return false;
        }
    }

    private void imprimirEstado() {
    Interfaz.titulo("TURNO DE COMBATE");

    String hpJugador = Interfaz.barraHP(jugador.getVida(), jugador.getVidaMax(), 18);
    String hpEnemigo = Interfaz.barraHP(enemigo.getVida(), enemigo.getVidaMax(), 18);

    System.out.println("Jugador: " + jugador.getIcono() + " " + jugador.getNombre() +
            "  HP " + jugador.getVida() + "/" + jugador.getVidaMax() + " " + hpJugador +
            "  Estado: " + jugador.getEstado());

    System.out.println("Enemigo: " + enemigo.getIcono() + " " + enemigo.getNombre() +
            "  HP " + enemigo.getVida() + "/" + enemigo.getVidaMax() + " " + hpEnemigo +
            "  Estado: " + enemigo.getEstado());

    if (defBonus > 0) {
        System.out.println("🛡️ Buff DEF: +" + defBonus + " (" + turnosDefBonus + " turnos)");
    }

    Interfaz.separador();
}

    private void accionAtacar() {
        int antes = enemigo.getVida();
        jugador.atacar(enemigo);
        int hecho = Math.max(0, antes - enemigo.getVida());
        System.out.println("Atacaste e hiciste " + hecho + " daño real.");
    }

    private void accionHabilidad() {
        Habilidad h = jugador.getHabilidadEspecial();
        int antes = enemigo.getVida();
        h.usar(jugador, enemigo);
        int hecho = Math.max(0, antes - enemigo.getVida());
        System.out.println("Usaste " + h.nombre() + " e hiciste " + hecho + " daño real. Estado enemigo: " + enemigo.getEstado());
    }

    private void accionItem() {
        if (inventario.isEmpty()) {
            System.out.println("No tienes items.");
            return;
        }
        System.out.println("\n--- INVENTARIO ---");
        for (int i = 0; i < inventario.size(); i++) {
            System.out.println((i+1) + ") " + inventario.get(i));
        }
        System.out.println("0) Cancelar");

        int idx = leerIntSeguro("Elegir item: ") - 1;
        if (idx == -1) return;
        if (idx < 0 || idx >= inventario.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Item it = inventario.remove(idx);

        if (it instanceof Escudo esc) {
            // Activar buff temporal
            defBonus = esc.getBonusDef();
            turnosDefBonus = esc.getTurnos();
            System.out.println("Usaste Escudo: DEF +" + defBonus + " por " + turnosDefBonus + " turnos.");
            return;
        }

        it.usar(jugador);
        System.out.println("Usaste " + it.getNombre() + ". Ahora HP: " + jugador.getVida() + "/" + jugador.getVidaMax()
                + " Estado: " + jugador.getEstado());
    }

    private int leerIntSeguro(String prompt) {
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