public class Principal {
    public static void main(String[] args) {
        OraculoMaster oraculo = new OraculoMaster();
        String nomeOraculo = pedirNomeObrigatorio("Nome do Oraculo", "Informe o nome do Oraculo Master:");
        oraculo.definirNome(nomeOraculo);

        for (int partida = 1; partida <= 2; partida++) {
            String nomeGuerreiro = pedirNomeObrigatorio(
                "Nome do Guerreiro",
                "Informe o nome do Guerreiro da partida " + partida + ":"
            );

            oraculo.iniciarPartida(nomeGuerreiro);
            InOut.exibirMensagem("Prologo", oraculo.prologoIntroducao());

            boolean venceu = jogarPartida(oraculo);

            if (venceu) {
                InOut.exibirSucesso("Resultado Final", oraculo.prologoVencedor());
            } else {
                InOut.exibirErro("Resultado Final", oraculo.prologoPerdedor());
            }

            InOut.exibirMensagem("Relatorio", oraculo.relatorioFimGame());
        }

        InOut.exibirMensagem("Encerramento", "As duas partidas minimas foram concluidas com sucesso.");
    }

    private static boolean jogarPartida(OraculoMaster oraculo) {
        if (!oraculo.loadLevel1()) {
            return tentarVidaExtra(oraculo) && oraculo.loadLevel2() && oraculo.loadLevel3();
        }

        if (!oraculo.loadLevel2()) {
            return tentarVidaExtra(oraculo) && oraculo.loadLevel3();
        }

        if (!oraculo.loadLevel3()) {
            return tentarVidaExtra(oraculo);
        }

        return true;
    }

    private static boolean tentarVidaExtra(OraculoMaster oraculo) {
        if (!InOut.confirmar("Vida Extra", "Deseja pedir misericordia ao Oraculo por uma vida extra?")) {
            return false;
        }

        String pedido = oraculo.vidaExtra();
        boolean concedida = oraculo.decidirVidaExtra(pedido);

        if (concedida) {
            InOut.exibirSucesso("Vida Extra", "O Oraculo aceitou o pedido. Uma vida extra foi concedida!");
        } else {
            InOut.exibirAviso("Vida Extra", "O pedido nao foi aceito. Era preciso escrever mais de cinco palavras.");
        }

        return concedida;
    }

    private static String pedirNomeObrigatorio(String titulo, String mensagem) {
        while (true) {
            String valor = InOut.lerTexto(titulo, mensagem);
            if (valor != null && !valor.trim().isEmpty()) {
                return valor.trim();
            }
            InOut.exibirAviso("Campo obrigatorio", "Informe um nome valido para continuar.");
        }
    }
}
