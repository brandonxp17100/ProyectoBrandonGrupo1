public class Antidoto extends Item {
    public Antidoto() {
        super("ANT", "Antídoto", "Cura problemas de estado");
    }

    @Override
    public void usar(Personaje objetivo) {
        objetivo.aplicarEstado(Estado.NINGUNO, 0);
    }
}