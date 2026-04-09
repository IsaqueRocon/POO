public class Charada {
    private final String pergunta;
    private final String resposta;

    public Charada(String pergunta, String resposta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public boolean confereResposta(String tentativa) {
        if (tentativa == null) {
            return false;
        }
        return resposta.equalsIgnoreCase(tentativa.trim());
    }
}
