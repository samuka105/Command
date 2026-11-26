# Padrão Command

Caixa de banco em que depósito, saque e transferência são operações que o `Caixa` executa e
guarda numa lista, o que permite cancelar a última operação feita. Cada operação sabe se
desfazer: o depósito cancelado vira saque, o saque vira depósito e a transferência devolve o
dinheiro para a conta de origem. Uma operação que falha (saque sem saldo, por exemplo) não entra
na lista, para que o cancelar não desfaça algo que nunca aconteceu.

## Conteúdo do repositório

- Código-fonte (`Operacao`, `DepositoOperacao`, `SaqueOperacao`, `TransferenciaOperacao`, `Conta`, `Caixa`)
- Casos de teste JUnit (`CaixaTest`)
- Diagrama UML (`diagrama_command.drawio`)
