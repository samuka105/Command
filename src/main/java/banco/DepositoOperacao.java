package banco;

public class DepositoOperacao implements Operacao {

    private Conta conta;
    private double valor;

    public DepositoOperacao(Conta conta, double valor) {
        this.conta = conta;
        this.valor = valor;
    }

    public void executar() {
        this.conta.depositar(this.valor);
    }

    public void cancelar() {
        this.conta.sacar(this.valor);
    }
}
