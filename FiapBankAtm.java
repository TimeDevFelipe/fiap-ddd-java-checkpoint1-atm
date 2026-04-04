import java.util.Scanner;

public class FiapBankAtm {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // cadastro do cliente
        System.out.println("=== bem-vindo ao FIAP Bank ATM ===");
        System.out.print("por favor, digite seu nome completo: ");
        String nomeCompleto = scanner.nextLine().trim();

        // pega so o primeiro nome pra usar nas mensagens
        String primeiroNome = nomeCompleto.split(" ")[0];
        System.out.println("ola, " + primeiroNome + "! vamos cadastrar sua senha de acesso");

        // a senha precisa ser forte pra garantir a seguranca da conta
        String senhaForte = "";
        String regexSenha = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=?><]).{8,}$";

        while (true) {
            System.out.println("\na senha precisa ter:");
            System.out.println("  - no minimo 8 caracteres");
            System.out.println("  - pelo menos um numero");
            System.out.println("  - pelo menos uma letra maiuscula");
            System.out.println("  - pelo menos um caractere especial: !@#$%^&*()-_+=?><");
            System.out.print("crie sua senha: ");
            String tentativaSenha = scanner.nextLine();

            if (tentativaSenha.matches(regexSenha)) {
                senhaForte = tentativaSenha;
                System.out.println("senha cadastrada com sucesso!");
                break;
            } else {
                System.out.println("essa senha nao e forte o suficiente, tente novamente");
            }
        }

        // autenticacao - o usuario tem no maximo 3 tentativas
        System.out.println("\nagora vamos fazer o login");
        int tentativas = 0;
        boolean autenticado = false;

        while (tentativas < 3) {
            System.out.print("digite sua senha: ");
            String senhaDigitada = scanner.nextLine();

            // usando .equals() pra comparar strings, nunca ==
            if (senhaDigitada.equals(senhaForte)) {
                autenticado = true;
                System.out.println("acesso liberado! bem-vindo, " + primeiroNome);
                break;
            } else {
                tentativas++;
                int restantes = 3 - tentativas;
                if (restantes > 0) {
                    System.out.println("senha incorreta! voce tem " + restantes + " tentativa(s) restante(s)");
                }
            }
        }

        // se errou as 3 vezes, bloqueia o acesso
        if (!autenticado) {
            System.out.println("ACESSO BLOQUEADO");
            scanner.close();
            return;
        }

        // fase b - menu principal em loop
        // saldo comeca em zero conforme o enunciado
        double saldo = 0.0;
        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n=== menu principal ===");
            System.out.println("[ 1 ] consultar saldo");
            System.out.println("[ 2 ] fazer deposito");
            System.out.println("[ 3 ] fazer saque");
            System.out.println("[ 4 ] sair");
            System.out.print("escolha uma opcao: ");

            // verifica se o usuario digitou um numero valido
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("opcao invalida! digite um numero entre 1 e 4");
                scanner.nextLine();
                continue;
            }

            // fase c - operacoes bancarias
            if (opcao == 1) {
                // exibe o saldo com duas casas decimais
                System.out.printf("seu saldo atual e: R$ %.2f%n", saldo);

            } else if (opcao == 2) {
                System.out.print("digite o valor do deposito: R$ ");

                if (scanner.hasNextDouble()) {
                    double valorDeposito = scanner.nextDouble();
                    scanner.nextLine();

                    // nao permite deposito de valor negativo ou zero
                    if (valorDeposito <= 0) {
                        System.out.println("valor invalido! o deposito precisa ser maior que zero");
                    } else {
                        saldo = saldo + valorDeposito;
                        System.out.printf("deposito realizado! novo saldo: R$ %.2f%n", saldo);
                    }
                } else {
                    System.out.println("valor invalido! digite um numero");
                    scanner.nextLine();
                }

            } else if (opcao == 3) {
                System.out.print("digite o valor do saque: R$ ");

                if (scanner.hasNextDouble()) {
                    double valorSaque = scanner.nextDouble();
                    scanner.nextLine();

                    // validacoes do saque
                    if (valorSaque <= 0) {
                        System.out.println("valor invalido! o saque precisa ser maior que zero");
                    } else if (valorSaque > saldo) {
                        System.out.printf("saldo insuficiente! seu saldo atual e: R$ %.2f%n", saldo);
                    } else {
                        saldo = saldo - valorSaque;
                        System.out.printf("saque realizado! novo saldo: R$ %.2f%n", saldo);
                    }
                } else {
                    System.out.println("valor invalido! digite um numero");
                    scanner.nextLine();
                }

            } else if (opcao == 4) {
                System.out.println("o FIAP Bank agradece sua preferencia!");

            } else {
                System.out.println("opcao invalida! escolha entre 1 e 4");
            }
        }

        scanner.close();
    }
}
