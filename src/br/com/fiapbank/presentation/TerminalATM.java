package br.com.fiapbank.presentation;

import br.com.fiapbank.application.ContaService;
import br.com.fiapbank.infrastructure.Repositorio;
import br.com.fiapbank.model.Cliente;
import br.com.fiapbank.model.ContaAcesso;
import br.com.fiapbank.model.ContaCorrente;
import br.com.fiapbank.model.exceptions.AcessoBloqueadoException;
import br.com.fiapbank.model.exceptions.SaldoInsuficienteException;
import br.com.fiapbank.model.exceptions.ValorInvalidoException;

import java.util.List;
import java.util.Scanner;

public class TerminalATM {

    private static final String TOPO  = "╔══════════════════════════════════════════╗";
    private static final String LINHA = "╠══════════════════════════════════════════╣";
    private static final String BASE  = "╚══════════════════════════════════════════╝";

    private final Repositorio repositorio = new Repositorio();
    private final ContaService contaService = new ContaService();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new TerminalATM().iniciar();
    }

    private void iniciar() {
        exibirBanner();
        while (true) {
            Cliente cliente = realizarLogin();
            if (cliente != null) {
                exibirMenuPrincipal(cliente);
            }
        }
    }

    private Cliente realizarLogin() {
        System.out.println(TOPO);
        System.out.println("║           IDENTIFICAÇÃO DA CONTA           ║");
        System.out.println(BASE);
        System.out.print("\n  Número da conta (ex: 001-1): ");
        String numeroConta = scanner.nextLine().trim();

        Cliente cliente = repositorio.buscarCliente(numeroConta);
        ContaAcesso acesso = repositorio.buscarAcesso(numeroConta);

        if (cliente == null || acesso == null) {
            exibirErro("Conta não encontrada.");
            return null;
        }

        while (true) {
            System.out.print("  Senha: ");
            String senha = scanner.nextLine().trim();

            try {
                Boolean autorizado = acesso.autorizar(senha);
                if (autorizado) {
                    exibirSucesso("Acesso autorizado. Bem-vindo(a), " + cliente.getNome() + "!");
                    return cliente;
                } else {
                    System.out.println("\n  [ ! ] Senha incorreta. Tentativas restantes: " + acesso.getTentativasRestantes());
                }
            } catch (AcessoBloqueadoException e) {
                exibirErro(e.getMessage());
                return null;
            }
        }
    }

    private void exibirMenuPrincipal(Cliente cliente) {
        while (true) {
            System.out.println("\n" + TOPO);
            System.out.println("║              MENU PRINCIPAL                ║");
            System.out.println(LINHA);
            System.out.println("║  [1] Consultar Saldo                       ║");
            System.out.println("║  [2] Realizar Saque                        ║");
            System.out.println("║  [3] Realizar Depósito                     ║");
            System.out.println("║  [4] Ver Extrato                           ║");
            System.out.println("║  [5] Encerrar Sessão                       ║");
            System.out.println(BASE);
            System.out.print("\n  Opção: ");

            Integer opcao = lerInteiro();
            if (opcao == null) {
                exibirErro("Opção inválida. Digite um número de 1 a 5.");
                continue;
            }

            switch (opcao) {
                case 1 -> consultarSaldo(cliente);
                case 2 -> realizarSaque(cliente);
                case 3 -> realizarDeposito(cliente);
                case 4 -> verExtrato(cliente);
                case 5 -> {
                    System.out.println("\n  Sessão encerrada. Obrigado, " + cliente.getNome() + "!\n");
                    return;
                }
                default -> exibirErro("Opção inválida. Selecione entre 1 e 5.");
            }
        }
    }

    private void consultarSaldo(Cliente cliente) {
        System.out.println("\n" + TOPO);
        System.out.println("║              CONSULTA DE SALDO             ║");
        System.out.println(LINHA);
        System.out.printf("║  Conta   : %-32s ║%n", cliente.getConta().getNumero());
        System.out.printf("║  Titular : %-32s ║%n", cliente.getNome());
        System.out.printf("║  Saldo   : R$ %,-29.2f ║%n", cliente.getConta().getSaldo());
        if (cliente.getConta() instanceof ContaCorrente cc) {
            System.out.printf("║  Limite  : R$ %,-29.2f ║%n", cc.getLimite());
        }
        System.out.println(BASE);
    }

    private void realizarSaque(Cliente cliente) {
        System.out.print("\n  Valor do saque: R$ ");
        Double valor = lerDouble();
        if (valor == null) {
            exibirErro("Valor inválido. Digite um número.");
            return;
        }
        try {
            contaService.sacar(cliente.getConta(), valor);
            exibirSucesso(String.format("Saque de R$ %,.2f realizado com sucesso!", valor));
        } catch (SaldoInsuficienteException | ValorInvalidoException e) {
            exibirErro(e.getMessage());
        }
    }

    private void realizarDeposito(Cliente cliente) {
        System.out.print("\n  Valor do depósito: R$ ");
        Double valor = lerDouble();
        if (valor == null) {
            exibirErro("Valor inválido. Digite um número.");
            return;
        }
        try {
            contaService.depositar(cliente.getConta(), valor);
            exibirSucesso(String.format("Depósito de R$ %,.2f realizado com sucesso!", valor));
        } catch (ValorInvalidoException e) {
            exibirErro(e.getMessage());
        }
    }

    private void verExtrato(Cliente cliente) {
        List<String> historico = cliente.getConta().getHistorico();
        System.out.println("\n" + TOPO);
        System.out.println("║                  EXTRATO                   ║");
        System.out.println(LINHA);
        if (historico.isEmpty()) {
            System.out.println("║  Nenhuma movimentação registrada.          ║");
        } else {
            for (String mov : historico) {
                System.out.printf("║  %-42s ║%n", mov);
            }
        }
        System.out.println(LINHA);
        System.out.printf("║  Saldo Atual: R$ %,-25.2f ║%n", cliente.getConta().getSaldo());
        System.out.println(BASE);
    }

    private void exibirErro(String mensagem) {
        System.out.println("\n" + TOPO);
        System.out.println("║       STATUS DA TRANSAÇÃO: ERRO            ║");
        System.out.println(LINHA);
        System.out.printf("║  [ ! ] %-34s ║%n", mensagem);
        System.out.println(BASE);
    }

    private void exibirSucesso(String mensagem) {
        System.out.println("\n" + TOPO);
        System.out.println("║      STATUS DA TRANSAÇÃO: SUCESSO          ║");
        System.out.println(LINHA);
        System.out.printf("║  [OK]  %-34s ║%n", mensagem);
        System.out.println(BASE);
    }

    private void exibirBanner() {
        System.out.println();
        System.out.println(TOPO);
        System.out.println("║        FIAP BANK - TERMINAL ATM            ║");
        System.out.println("║       Simulador de Caixa Eletrônico        ║");
        System.out.println(BASE);
        System.out.println();
    }

    private Integer lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
