package banco;

import java.util.ArrayList;
import java.util.List;

public class Caixa {

    private List<Operacao> operacoes = new ArrayList<Operacao>();

    public void executarOperacao(Operacao operacao) {
        operacao.executar();
        // so entra na lista se executou sem erro, senao o cancelar desfaria algo que nao aconteceu
        this.operacoes.add(operacao);
    }

    public void cancelarUltimaOperacao() {
        if (operacoes.size() != 0) {
            Operacao operacao = this.operacoes.get(this.operacoes.size() - 1);
            operacao.cancelar();
            this.operacoes.remove(this.operacoes.size() - 1);
        }
    }

    public int getQuantidadeOperacoes() {
        return operacoes.size();
    }
}
