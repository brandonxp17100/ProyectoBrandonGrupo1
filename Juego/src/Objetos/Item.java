public abstract class Item {
    protected String icono;
    protected String nombre;
    protected String descripcion;

    public Item(String icono, String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getIcono() { return icono; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    public abstract void usar(Personaje objetivo);

    @Override
    public String toString() {
        return nombre + " - " + descripcion;
    }
}