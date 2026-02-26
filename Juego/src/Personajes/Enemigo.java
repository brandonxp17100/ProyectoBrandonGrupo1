import java.util.Random;

public class Enemigo extends Personaje {
    private static final Random rng = new Random();

    public Enemigo(String nombre, int vidaMax, int ataque, int defensa) {
        super(nombre, vidaMax, ataque, defensa, null);
        this.icono = "lml";
    }

    @Override
    public Habilidad getHabilidadEspecial() {
        return new Habilidad() {
            @Override public String nombre() { return "Triturar"; }
            @Override public void usar(Personaje usuario, Personaje objetivo) {
                int danio = usuario.ataque + 1;
                objetivo.recibirDanio(danio);
            }
        };
    }

    public String jugarTurno(Personaje jugador) {
        if (!estaVivo()) return "";

        int roll = rng.nextInt(100);
        if (roll < 70) {
            int base = this.ataque + rng.nextInt(3);
            jugador.recibirDanio(base);
            return nombre + " atacó e hizo " + Math.max(0, base - jugador.defensa) + " daño real.";
        } else {
            if (jugador.getEstado() == Estado.NINGUNO) {
                if (rng.nextBoolean()) {
                    jugador.aplicarEstado(Estado.VENENO, 3);
                    return nombre + " aplicó VENENO.";
                } else {
                    jugador.aplicarEstado(Estado.QUEMADURA, 2);
                    return nombre + " aplicó QUEMADURA.";
                }
            } else {
                int base = this.ataque + rng.nextInt(2);
                jugador.recibirDanio(base);
                return nombre + " atacó e hizo " + Math.max(0, base - jugador.defensa) + " daño real.";
            }
        }
    }
}