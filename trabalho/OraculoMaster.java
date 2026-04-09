import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OraculoMaster {
    private final Random random = new Random();
    private final List<Charada> bancoDeCharadas = Arrays.asList(
        new Charada("O que e, o que e? Quanto mais se tira, maior fica.", "buraco"),
        new Charada("O que e, o que e? Tem dentes, mas nao morde.", "pente"),
        new Charada("O que e, o que e? Cai em pe e corre deitado.", "chuva"),
        new Charada("O que e, o que e? Tem asas, mas nao voa. Tem bico, mas nao bica.", "bule"),
        new Charada("O que e, o que e? Sobe quando a chuva desce.", "guarda-chuva")
    );

    private String nome;
    private Guerreiro guerreiro;
    private final List<Integer> palpitesLevel1 = new ArrayList<>();
    private final List<String> respostasLevel2 = new ArrayList<>();
    private final List<String> respostasLevel3 = new ArrayList<>();
    private int segredoLevel1;
    private final List<String> respostasCorretasLevel2 = new ArrayList<>();
    private int senhaLevel3;
    private boolean ganhouVidaExtra;

    public void definirNome(String nome) {
        this.nome = nome;
    }

    public int sortearVidas() {
        return random.nextInt(4) + 9;
    }

    public void iniciarPartida(String nomeGuerreiro) {
        this.guerreiro = new Guerreiro(nomeGuerreiro);
        this.guerreiro.definirVidas(sortearVidas());
        this.palpitesLevel1.clear();
        this.respostasLevel2.clear();
        this.respostasLevel3.clear();
        this.respostasCorretasLevel2.clear();
        this.ganhouVidaExtra = false;
        this.segredoLevel1 = 0;
        this.senhaLevel3 = 0;
    }

    public String prologoIntroducao() {
        return "Oraculo " + nome + ": Bem-vindo, " + guerreiro.getNome() + "!\n"
            + "Sua jornada comeca com " + guerreiro.getVidas() + " vidas.\n"
            + "Sobreviva aos tres niveis para se tornar um campeao.";
    }

    public boolean loadLevel1() {
        segredoLevel1 = random.nextInt(100) + 1;

        while (guerreiro.estaVivo()) {
            int palpite = InOut.lerInteiro("Level 1", "Adivinhe o numero secreto entre 1 e 100:");
            palpitesLevel1.add(palpite);

            if (palpite == segredoLevel1) {
                InOut.exibirSucesso("Level 1", "Acertou! O numero secreto era " + segredoLevel1 + ".");
                return true;
            }

            guerreiro.perderVida();
            String dica = palpite > segredoLevel1 ? "MENOR" : "MAIOR";
            InOut.exibirAviso("Level 1",
                "Errou! O segredo e " + dica + " que o seu palpite.\nVidas restantes: " + guerreiro.getVidas());
        }

        return false;
    }

    public boolean loadLevel2() {
        List<Charada> charadas = new ArrayList<>(bancoDeCharadas);
        Collections.shuffle(charadas, random);

        for (int i = 0; i < 3; i++) {
            Charada charada = charadas.get(i);
            respostasCorretasLevel2.add(charada.getResposta());
            String resposta = InOut.lerTexto("Level 2", "Charada " + (i + 1) + ":\n" + charada.getPergunta());
            respostasLevel2.add(resposta == null ? "(cancelado)" : resposta);

            if (!charada.confereResposta(resposta)) {
                guerreiro.perderVida();
                InOut.exibirAviso("Level 2",
                    "Resposta incorreta.\nCorreto: " + charada.getResposta() + "\nVidas restantes: " + guerreiro.getVidas());
                if (!guerreiro.estaVivo()) {
                    return false;
                }
            } else {
                InOut.exibirSucesso("Level 2", "Boa! Voce acertou a charada.");
            }
        }

        return true;
    }

    public boolean loadLevel3() {
        senhaLevel3 = random.nextInt(40) + 10;

        for (int rodada = 1; rodada <= 3 && guerreiro.estaVivo(); rodada++) {
            int tentativa = InOut.lerInteiro(
                "Level 3",
                "Minigame da senha magica.\nRodada " + rodada + " de 3.\nTente acertar a senha entre 10 e 49:"
            );
            respostasLevel3.add(String.valueOf(tentativa));

            if (tentativa == senhaLevel3) {
                InOut.exibirSucesso("Level 3", "Senha descoberta! O portal foi aberto.");
                return true;
            }

            guerreiro.perderVida();
            String dica = tentativa > senhaLevel3 ? "mais baixa" : "mais alta";
            InOut.exibirAviso("Level 3",
                "Senha incorreta. A senha e " + dica + ".\nVidas restantes: " + guerreiro.getVidas());
        }

        return false;
    }

    public String vidaExtra() {
        return InOut.lerTexto(
            "Pedido de Misericordia",
            "Escreva seu pedido de misericordia ao Oraculo para tentar ganhar mais uma vida:"
        );
    }

    public boolean decidirVidaExtra(String misericordia) {
        if (misericordia == null) {
            return false;
        }

        String[] palavras = misericordia.trim().split("\\s+");
        boolean concedida = palavras.length > 5;
        if (concedida) {
            guerreiro.ganharVida();
            ganhouVidaExtra = true;
        }
        return concedida;
    }

    public String prologoVencedor() {
        return "Oraculo " + nome + ": Grandioso " + guerreiro.getNome()
            + ", voce venceu os desafios e concluiu sua jornada com honra!";
    }

    public String prologoPerdedor() {
        return "Oraculo " + nome + ": " + guerreiro.getNome()
            + ", hoje a escuridao venceu. Volte mais forte para uma nova batalha.";
    }

    public String relatorioFimGame() {
        return "Relatorio final da partida\n"
            + "Oraculo: " + nome + "\n"
            + "Guerreiro: " + guerreiro.getNome() + "\n"
            + "Vidas restantes: " + guerreiro.getVidas() + "\n"
            + "Vidas perdidas: " + guerreiro.getVidasPerdidas() + "\n"
            + "Vida extra concedida: " + (ganhouVidaExtra ? "sim" : "nao") + "\n"
            + "Palpites do Level 1: " + palpitesLevel1 + "\n"
            + "Numero correto do Level 1: " + segredoLevel1 + "\n"
            + "Respostas do Level 2: " + respostasLevel2 + "\n"
            + "Respostas corretas do Level 2: " + respostasCorretasLevel2 + "\n"
            + "Tentativas do Level 3: " + respostasLevel3 + "\n"
            + "Senha do Level 3: " + senhaLevel3;
    }
}
