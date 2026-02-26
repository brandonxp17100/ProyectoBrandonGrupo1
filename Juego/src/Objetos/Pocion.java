public class Pocion extends Item {
    private final int curacion;

    public Pocion(int curacion) {
        super("PC", "Poción", "Cura " + curacion + " HP.");
        this.curacion = curacion;
    }

    @Override
    public void usar(Personaje objetivo) {
        objetivo.curar(curacion);
    }
}