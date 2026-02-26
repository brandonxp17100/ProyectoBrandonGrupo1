import java.util.Random;

public class FabricaEnemigos {
    private static final Random rng = new Random();

    public static Enemigo crearEnemigo(int nivelJugador) {
        String[] nombres = {"Thanos", "Madara Uchiha", "Sukuna", "Freezer", "Voldemort"};
        String nombre = nombres[rng.nextInt(nombres.length)];

        int vida = 18 + nivelJugador * 3 + rng.nextInt(6);
        int atk  = 5 + nivelJugador + rng.nextInt(3);
        int def  = 1 + (nivelJugador / 2);

        return new Enemigo(nombre, vida, atk, def);
    }
}