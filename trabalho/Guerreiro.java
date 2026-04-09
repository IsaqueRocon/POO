public class Guerreiro {
    private final String nome;
    private int vidas;
    private int vidasPerdidas;

    public Guerreiro(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getVidas() {
        return vidas;
    }

    public int getVidasPerdidas() {
        return vidasPerdidas;
    }

    public void definirVidas(int vidas) {
        this.vidas = vidas;
        this.vidasPerdidas = 0;
    }

    public void perderVida() {
        if (vidas > 0) {
            vidas--;
            vidasPerdidas++;
        }
    }

    public void ganharVida() {
        vidas++;
    }

    public boolean estaVivo() {
        return vidas > 0;
    }
}
