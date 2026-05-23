package br.com.fiapbank.model;

import br.com.fiapbank.model.exceptions.AcessoBloqueadoException;
import br.com.fiapbank.model.exceptions.ValorInvalidoException;
import br.com.fiapbank.model.interfaces.Autorizavel;

public class ContaAcesso implements Autorizavel {

    private static final Integer MAX_TENTATIVAS = 3;

    private final String senha;
    private Integer tentativasRestantes;
    private Boolean bloqueado;

    public ContaAcesso(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new ValorInvalidoException("Senha não pode ser vazia.");
        }
        this.senha = senha;
        this.tentativasRestantes = MAX_TENTATIVAS;
        this.bloqueado = false;
    }

    @Override
    public Boolean autorizar(String senha) {
        if (isBloqueado()) {
            throw new AcessoBloqueadoException("Conta bloqueada após 3 tentativas inválidas. Procure uma agência.");
        }
        if (this.senha.equals(senha)) {
            this.tentativasRestantes = MAX_TENTATIVAS;
            return true;
        }
        this.tentativasRestantes--;
        if (this.tentativasRestantes <= 0) {
            this.bloqueado = true;
            throw new AcessoBloqueadoException("Conta bloqueada após 3 tentativas inválidas. Procure uma agência.");
        }
        return false;
    }

    @Override
    public Boolean isBloqueado() {
        return bloqueado;
    }

    public Integer getTentativasRestantes() {
        return tentativasRestantes;
    }
}
