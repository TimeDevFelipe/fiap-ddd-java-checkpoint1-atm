package br.com.fiapbank.application;

import br.com.fiapbank.model.Conta;

public class ContaService {

    public void sacar(Conta conta, Double valor) {
        conta.sacar(valor);
    }

    public void depositar(Conta conta, Double valor) {
        conta.depositar(valor);
    }

    public Double consultarSaldo(Conta conta) {
        return conta.getSaldo();
    }
}
