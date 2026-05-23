package br.com.fiapbank.model;

import br.com.fiapbank.model.exceptions.SaldoInsuficienteException;
import br.com.fiapbank.model.exceptions.ValorInvalidoException;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(String numero, Double saldoInicial) {
        super(numero, saldoInicial);
    }

    @Override
    public void sacar(Double valor) {
        if (valor == null || valor <= 0) {
            throw new ValorInvalidoException("Valor de saque deve ser maior que zero.");
        }
        if (valor > getSaldo()) {
            throw new SaldoInsuficienteException(
                String.format("Saldo insuficiente. Saldo disponível: R$ %,.2f", getSaldo())
            );
        }
        debitar(valor);
    }
}
