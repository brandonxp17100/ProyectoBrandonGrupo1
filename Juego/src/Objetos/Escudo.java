public class Escudo extends Item {
    private final int bonusDef;
    private final int turnos;

    public Escudo(int bonusDef, int turnos) {
        super("ES", "Escudo", "Aumenta DEF +" + bonusDef + " por " + turnos + " turnos.");
        this.bonusDef = bonusDef;
        this.turnos = turnos;
    }

    @Override
    public void usar(Personaje objetivo) { /* se maneja en Combate */ }

    public int getBonusDef() { return bonusDef; }
    public int getTurnos() { return turnos; }
}