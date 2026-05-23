package br.com.fiapbank.infrastructure;

import br.com.fiapbank.model.Cliente;
import br.com.fiapbank.model.ContaAcesso;
import br.com.fiapbank.model.ContaCorrente;
import br.com.fiapbank.model.ContaPoupanca;

import java.util.HashMap;
import java.util.Map;

public class Repositorio {

    private final Map<String, Cliente> clientes = new HashMap<>();
    private final Map<String, ContaAcesso> acessos = new HashMap<>();

    public Repositorio() {
        ContaCorrente cc1 = new ContaCorrente("001-1", 1500.00, 500.00);
        clientes.put("001-1", new Cliente("João Silva", "111.111.111-11", cc1));
        acessos.put("001-1", new ContaAcesso("1234"));

        ContaPoupanca cp2 = new ContaPoupanca("002-1", 800.00);
        clientes.put("002-1", new Cliente("Maria Santos", "222.222.222-22", cp2));
        acessos.put("002-1", new ContaAcesso("4321"));
    }

    public Cliente buscarCliente(String numeroConta) {
        return clientes.get(numeroConta);
    }

    public ContaAcesso buscarAcesso(String numeroConta) {
        return acessos.get(numeroConta);
    }
}
