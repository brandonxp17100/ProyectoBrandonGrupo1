import java.util.ArrayList;
import java.util.Scanner;

public class Tienda {

    public static void abrir(Scanner sc, ArrayList<Item> inventario, int[] oroRef) {
        while (true) {
            System.out.println("\n=== TIENDA ===");
            System.out.println("Oro: " + oroRef[0]);
            System.out.println("1) Poción (10 oro) [cura 12]");
            System.out.println("2) Antídoto (8 oro)");
            System.out.println("3) Escudo (12 oro) [+2 DEF por 2 turnos]");
            System.out.println("0) Salir");

            int op = leerIntSeguro(sc, "Elige: ");
            if (op == 0) return;

            switch (op) {
                case 1 -> comprar(inventario, oroRef, 10, new Pocion(12));
                case 2 -> comprar(inventario, oroRef, 8, new Antidoto());
                case 3 -> comprar(inventario, oroRef, 12, new Escudo(2, 2));
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void comprar(ArrayList<Item> inv, int[] oroRef, int costo, Item item) {
        if (oroRef[0] < costo) {
            System.out.println("No tienes suficiente oro.");
            return;
        }
        oroRef[0] -= costo;
        inv.add(item);
        System.out.println("Compraste: " + item.getNombre());
    }

    // try/catch obligatorio (guard rail inputs)
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