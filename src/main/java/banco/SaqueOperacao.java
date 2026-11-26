package banco;

public class SaqueOperacao implements Operacao {

    private Conta conta;
    private double valor;

    public SaqueOperacao(Conta conta, double valor) {
        this.conta = conta;
        this.valor = valor;
    }

    public void executar() {
        this.conta.sacar(this.valor);
    }

    public void cancelar() {
        this.conta.depositar(this.valor);
    }
}
