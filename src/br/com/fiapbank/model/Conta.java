package br.com.fiapbank.model;

import br.com.fiapbank.model.exceptions.ValorInvalidoException;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

    private final String numero;
    private Double saldo;
    private final List<String> historico;

    public Conta(String numero, Double saldoInicial) {
        if (numero == null || numero.isBlank()) {
            throw new ValorInvalidoException("Número da conta inválido.");
        }
        if (saldoInicial == null || saldoInicial < 0) {
            throw new ValorInvalidoException("Saldo inicial não pode ser negativo.");
        }
        this.numero = numero;
        this.saldo = saldoInicial;
        this.historico = new ArrayList<>();
    }

    public abstract void sacar(Double valor);

    public void depositar(Double valor) {
        if (valor == null || valor <= 0) {
            throw new ValorInvalidoException("Valor de depósito deve ser maior que zero.");
        }
        this.saldo += valor;
        this.historico.add(String.format("Depósito    : R$ %,.2f", valor));
    }

    protected void debitar(Double valor) {
        this.saldo -= valor;
        this.historico.add(String.format("Saque       : R$ %,.2f", valor));
    }

    public Double getSaldo() {
        return saldo;
    }

    public String getNumero() {
        return numero;
    }

    public List<String> getHistorico() {
        return new ArrayList<>(historico);
    }
}
