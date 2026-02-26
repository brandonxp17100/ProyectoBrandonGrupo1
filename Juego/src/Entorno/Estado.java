public enum Estado {
    NINGUNO(""),
    VENENO("VN"),
    QUEMADURA("QM");

    private final String icono;
    Estado(String icono) { this.icono = icono; }
    public String icono() { return icono; }

    @Override
    public String toString() {
        return icono.isEmpty() ? "NINGUNO" : name() + " " + icono;
    }
}