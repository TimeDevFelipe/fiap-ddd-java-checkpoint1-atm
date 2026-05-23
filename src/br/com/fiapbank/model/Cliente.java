package br.com.fiapbank.model;

import br.com.fiapbank.model.exceptions.ValorInvalidoException;

public class Cliente {

    private final String nome;
    private final String cpf;
    private final Conta conta;

    public Cliente(String nome, String cpf, Conta conta) {
        if (nome == null || nome.isBlank()) {
            throw new ValorInvalidoException("Nome do cliente inválido.");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new ValorInvalidoException("CPF do cliente inválido.");
        }
        if (conta == null) {
            throw new ValorInvalidoException("Conta não pode ser nula.");
        }
        this.nome = nome;
        this.cpf = cpf;
        this.conta = conta;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Conta getConta() {
        return conta;
    }
}
