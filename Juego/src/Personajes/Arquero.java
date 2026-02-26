import java.util.HashMap;
import java.util.Random;

public class Arquero extends Personaje {
    private final Habilidad especial;

    public Arquero(String nombre, HashMap<String, Integer> stats) {
        super(nombre, 34, 7, 2, stats);
        this.icono = "AR";
        this.especial = new Habilidad() {
            private final Random rng = new Random();

            @Override public String nombre() { return "Flecha Incendiaria"; }

            @Override
            public void usar(Personaje usuario, Personaje objetivo) {
                int danio = usuario.ataque + 2 + rng.nextInt(4);
                objetivo.recibirDanio(danio);
                objetivo.aplicarEstado(Estado.QUEMADURA, 4);
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