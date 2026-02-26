import java.util.HashMap;

public abstract class Personaje {
    protected String nombre;
    protected int nivel;
    protected int vidaMax;
    protected int vida;
    protected int ataque;
    protected int defensa;
    protected Estado estado = Estado.NINGUNO;
    protected int turnosEstado = 0;
    protected String icono;

public String getIcono() { return icono; }

    protected HashMap<String, Integer> stats;

    protected Personaje(String nombre, int vidaMax, int ataque, int defensa, HashMap<String, Integer> stats) {
        this.nombre = nombre;
        this.nivel = 1;
        this.vidaMax = vidaMax;
        this.vida = vidaMax;
        this.ataque = ataque;
        this.defensa = defensa;
        this.stats = stats;
    }

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getVidaMax() { return vidaMax; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getNivel() { return nivel; }
    public Estado getEstado() { return estado; }
    public boolean estaVivo() { return vida > 0; }

    // No permitir vida negativa
    public void recibirDanio(int danio) {
        int real = Math.max(0, danio - defensa);
        vida = Math.max(0, vida - real);
    }

    public void curar(int cantidad) {
        int antes = vida;
        vida = Math.min(vidaMax, vida + Math.max(0, cantidad));
        int curado = vida - antes;
        if (stats != null) {
            stats.put("curacionTotal", stats.getOrDefault("curacionTotal", 0) + curado);
        }
    }

    public int atacar(Personaje objetivo) {
        int danio = ataque;
        objetivo.recibirDanio(danio);
        if (stats != null) {
            stats.put("danoTotal", stats.getOrDefault("danoTotal", 0) + Math.max(0, danio - objetivo.defensa));
        }
        return danio;
    }

    public void aplicarEstado(Estado nuevo, int turnos) {
        this.estado = nuevo;
        this.turnosEstado = Math.max(0, turnos);
    }

    public String procesarEfectosPorTurno() {
        if (estado == Estado.NINGUNO || turnosEstado <= 0 || !estaVivo()) return "";

        int dot;
        String msg;

        if (estado == Estado.VENENO) {
            dot = Math.max(1, vidaMax / 12); // ~8%
            msg = nombre + " sufre ENVENENAMIENTO (" + dot + " daño).";
        } else {
            dot = Math.max(1, vidaMax / 15); // ~6-7%
            msg = nombre + " sufre QUEMADURA (" + dot + " daño).";
        }

        vida = Math.max(0, vida - dot);
        turnosEstado--;

        if (turnosEstado == 0) {
            Estado prev = estado;
            estado = Estado.NINGUNO;
            msg += " El efecto " + prev + " terminó.";
        }

        return msg;
    }

    public abstract Habilidad getHabilidadEspecial();

    //Niveles
    public void subirNivel() {
        nivel++;
        vidaMax += 5;
        ataque += 2;
        defensa += 1;
        vida = Math.min(vidaMax, vida + 4);
    }

    @Override
        public String toString() {
        return icono + " " + nombre + " (Nv " + nivel + ") HP: " + vida + "/" + vidaMax +
            " ATK: " + ataque + " DEF: " + defensa + " Estado: " + estado;
    }
}