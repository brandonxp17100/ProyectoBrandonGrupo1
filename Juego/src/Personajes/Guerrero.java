import java.util.HashMap;
import java.util.Random;

public class Guerrero extends Personaje {
    private final Habilidad especial;

    public Guerrero(String nombre, HashMap<String, Integer> stats) {
        super(nombre, 40, 8, 3, stats);
        this.icono = "GR";
        this.especial = new Habilidad() {
            private final Random rng = new Random();

            @Override public String nombre() { return "Mega Golpe"; }

            @Override
            public void usar(Personaje usuario, Personaje objetivo) {
                int bonus = 4 + rng.nextInt(4); // 4-7
                int danio = usuario.ataque + bonus;
                objetivo.recibirDanio(danio);
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