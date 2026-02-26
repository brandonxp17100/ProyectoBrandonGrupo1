import java.util.HashMap;
import java.util.Random;

public class Mago extends Personaje {
    private final Habilidad especial;

    public Mago(String nombre, HashMap<String, Integer> stats) {
        super(nombre, 32, 7, 2, stats);
        this.icono = "MG";
        this.especial = new Habilidad() {
            private final Random rng = new Random();

            @Override public String nombre() { return "Niebla Veneno"; }

            @Override
            public void usar(Personaje usuario, Personaje objetivo) {
                int danio = usuario.ataque + 3 + rng.nextInt(3); // +3..5
                objetivo.recibirDanio(danio);
                objetivo.aplicarEstado(Estado.VENENO, 3);
                if (usuario.stats != null) {
                    usuario.stats.put("danoTotal",
                            usuario.stats.getOrDefault("danoTotal", 0) + Math.max(0, danio - objetivo.defensa));
                }
            }
        };
    }

    @Override
    public Habilidad getHabilidadEspecial() {
        return especial;
    }
}