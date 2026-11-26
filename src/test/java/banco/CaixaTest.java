package banco;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaixaTest {

    Caixa caixa;
    Conta conta;

    @BeforeEach
    void setUp() {
        caixa = new Caixa();
        conta = new Conta("Pedro", 100.0);
    }

    @Test
    void deveDepositar() {
        caixa.executarOperacao(new DepositoOperacao(conta, 50.0));

        assertEquals(150.0, conta.getSaldo());
    }

    @Test
    void deveSacar() {
        caixa.executarOperacao(new SaqueOperacao(conta, 30.0));

        assertEquals(70.0, conta.getSaldo());
    }

    @Test
    void deveTransferir() {
        Conta destino = new Conta("Lucia", 20.0);
        caixa.executarOperacao(new TransferenciaOperacao(conta, destino, 40.0));

        assertEquals(60.0, conta.getSaldo());
        assertEquals(60.0, destino.getSaldo());
    }

    @Test
    void deveCancelarDeposito() {
        caixa.executarOperacao(new DepositoOperacao(conta, 50.0));
        caixa.cancelarUltimaOperacao();

        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void deveCancelarSaque() {
        caixa.executarOperacao(new SaqueOperacao(conta, 30.0));
        caixa.cancelarUltimaOperacao();

        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void deveCancelarTransferencia() {
        Conta destino = new Conta("Lucia", 20.0);
        caixa.executarOperacao(new TransferenciaOperacao(conta, destino, 40.0));
        caixa.cancelarUltimaOperacao();

        assertEquals(100.0, conta.getSaldo());
        assertEquals(20.0, destino.getSaldo());
    }

    @Test
    void deveCancelarSomenteAUltimaOperacao() {
        caixa.executarOperacao(new DepositoOperacao(conta, 50.0));
        caixa.executarOperacao(new SaqueOperacao(conta, 30.0));
        caixa.cancelarUltimaOperacao();

        assertEquals(150.0, conta.getSaldo());
    }

    @Test
    void deveCancelarVariasOperacoesNaOrdemInversa() {
        caixa.executarOperacao(new DepositoOperacao(conta, 50.0));
        caixa.executarOperacao(new SaqueOperacao(conta, 30.0));
        caixa.cancelarUltimaOperacao();
        caixa.cancelarUltimaOperacao();

        assertEquals(100.0, conta.getSaldo());
        assertEquals(0, caixa.getQuantidadeOperacoes());
    }

    @Test
    void naoDeveFazerNadaAoCancelarSemOperacoes() {
        caixa.cancelarUltimaOperacao();

        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void naoDeveRegistrarSaqueSemSaldo() {
        try {
            caixa.executarOperacao(new SaqueOperacao(conta, 500.0));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Saldo insuficiente", e.getMessage());
        }
        assertEquals(0, caixa.getQuantidadeOperacoes());
        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void cancelarDepoisDeSaqueRecusadoDeveDesfazerAOperacaoAnterior() {
        caixa.executarOperacao(new DepositoOperacao(conta, 50.0));
        assertThrows(IllegalArgumentException.class,
                () -> caixa.executarOperacao(new SaqueOperacao(conta, 1000.0)));
        caixa.cancelarUltimaOperacao();

        assertEquals(100.0, conta.getSaldo());
    }

    @Test
    void naoDeveCancelarDepositoSeODinheiroJaFoiSacado() {
        Conta destino = new Conta("Lucia", 0.0);
        caixa.executarOperacao(new DepositoOperacao(destino, 50.0));
        destino.sacar(50.0); // saque feito direto, fora do caixa

        assertThrows(IllegalArgumentException.class, () -> caixa.cancelarUltimaOperacao());
    }

    @Test
    void naoDeveAceitarDepositoNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> caixa.executarOperacao(new DepositoOperacao(conta, -10.0)));
        assertEquals(100.0, conta.getSaldo());
    }
}
