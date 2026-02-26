public class Interfaz {

    public static void titulo(String text) {
        String line = "═".repeat(text.length() + 2);
        System.out.println("╔" + line + "╗");
        System.out.println("║ " + text + " ║");
        System.out.println("╚" + line + "╝");
    }

    public static void caja(String text) {
        int width = text.length() + 2;
        System.out.println("┌" + "─".repeat(width) + "┐");
        System.out.println("│ " + text + " │");
        System.out.println("└" + "─".repeat(width) + "┘");
    }

    public static String barraHP(int hp, int maxHp, int ancho) {
        if (maxHp <= 0) return "[??????????] 0%";
        hp = Math.max(0, Math.min(hp, maxHp));

        double ratio = (double) hp / (double) maxHp;
        int filled = (int) Math.round(ratio * ancho);
        filled = Math.max(0, Math.min(filled, ancho));

        int empty = ancho - filled;

        int pct = (int) Math.round(ratio * 100);

        return "[" + "█".repeat(filled) + "░".repeat(empty) + "] " + pct + "%";
    }

    public static void separador() {
        System.out.println("────────────────────────────────────────");
    }
}