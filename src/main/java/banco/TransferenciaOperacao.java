package banco;

public class TransferenciaOperacao implements Operacao {

    private Conta origem;
    private Conta destino;
    private double valor;

    public TransferenciaOperacao(Conta origem, Conta destino, double valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    public void executar() {
        this.origem.sacar(this.valor);
        this.destino.depositar(this.valor);
    }

    // desfaz na ordem contraria: tira de quem recebeu e devolve pra quem mandou
    public void cancelar() {
        this.destino.sacar(this.valor);
        this.origem.depositar(this.valor);
    }
}
