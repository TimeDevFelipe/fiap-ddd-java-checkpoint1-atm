package br.com.fiapbank.model;

import br.com.fiapbank.model.exceptions.SaldoInsuficienteException;
import br.com.fiapbank.model.exceptions.ValorInvalidoException;

public class ContaCorrente extends Conta {

    private final Double limite;

    public ContaCorrente(String numero, Double saldoInicial, Double limite) {
        super(numero, saldoInicial);
        if (limite == null || limite < 0) {
            throw new ValorInvalidoException("Limite não pode ser negativo.");
        }
        this.limite = limite;
    }

    @Override
    public void sacar(Double valor) {
        if (valor == null || valor <= 0) {
            throw new ValorInvalidoException("Valor de saque deve ser maior que zero.");
        }
        Double disponivel = getSaldo() + limite;
        if (valor > disponivel) {
            throw new SaldoInsuficienteException(
                String.format("Saldo insuficiente. Disponível (saldo + limite): R$ %,.2f", disponivel)
            );
        }
        debitar(valor);
    }

    public Double getLimite() {
        return limite;
    }
}
