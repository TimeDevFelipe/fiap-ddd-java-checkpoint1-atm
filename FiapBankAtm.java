import java.util.Scanner;

public class FiapBankAtm {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // cadastro do cliente
        System.out.println("=== Bem-vindo ao FIAP Bank ATM ===");
        System.out.print("Por favor, digite seu nome completo: ");
        String nomeCompleto = scanner.nextLine().trim();

        // pega so o primeiro nome pra usar nas mensagens
        String primeiroNome = nomeCompleto.split(" ")[0];
        System.out.println("Ola, " + primeiroNome + "! Vamos cadastrar sua senha de acesso.");

        // a senha precisa ser forte pra garantir a seguranca da conta
        String senhaForte = "";
        String regexSenha = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=?><]).{8,}$";

        while (true) {
            System.out.println("\nA senha precisa ter:");
            System.out.println("  - No minimo 8 caracteres");
            System.out.println("  - Pelo menos um numero");
            System.out.println("  - Pelo menos uma letra maiuscula");
            System.out.println("  - Pelo menos um caractere especial: !@#$%^&*()-_+=?><");
            System.out.print("Crie sua senha: ");
            String tentativaSenha = scanner.nextLine();

            if (tentativaSenha.matches(regexSenha)) {
                senhaForte = tentativaSenha;
                System.out.println("Senha cadastrada com sucesso!");
                break;
            } else {
                System.out.println("Essa senha nao e forte o suficiente, tente novamente.");
            }
        }

        // autenticacao - o usuario tem no maximo 3 tentativas
        System.out.println("\nAgora vamos fazer o login.");
        int tentativas = 0;
        boolean autenticado = false;

        while (tentativas < 3) {
            System.out.print("Digite sua senha: ");
            String senhaDigitada = scanner.nextLine();

            // usando .equals() pra comparar strings, nunca ==
            if (senhaDigitada.equals(senhaForte)) {
                autenticado = true;
                System.out.println("Acesso liberado! Bem-vindo, " + primeiroNome + "!");
                break;
            } else {
                tentativas++;
                int restantes = 3 - tentativas;
                if (restantes > 0) {
                    System.out.println("Senha incorreta! Voce tem " + restantes + " tentativa(s) restante(s).");
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
        double saldo = 250.0;
        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("[ 1 ] Consultar Saldo");
            System.out.println("[ 2 ] Fazer Deposito");
            System.out.println("[ 3 ] Fazer Saque");
            System.out.println("[ 4 ] Sair");
            System.out.print("Escolha uma opcao: ");

            // verifica se o usuario digitou um numero valido
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Opcao invalida! Digite um numero entre 1 e 4.");
                scanner.nextLine();
                continue;
            }

            // fase c - operacoes bancarias
            if (opcao == 1) {
                // exibe o saldo com duas casas decimais
                System.out.printf("Seu saldo atual e: R$ %.2f%n", saldo);

            } else if (opcao == 2) {
                System.out.print("Digite o valor do deposito: R$ ");

                if (scanner.hasNextDouble()) {
                    double valorDeposito = scanner.nextDouble();
                    scanner.nextLine();

                    // nao permite deposito de valor negativo ou zero
                    if (valorDeposito <= 0) {
                        System.out.println("Valor invalido! O deposito precisa ser maior que zero.");
                    } else {
                        saldo = saldo + valorDeposito;
                        System.out.printf("Deposito realizado! Novo saldo: R$ %.2f%n", saldo);
                    }
                } else {
                    System.out.println("Valor invalido! Digite um numero.");
                    scanner.nextLine();
                }

            } else if (opcao == 3) {
                System.out.print("Digite o valor do saque: R$ ");

                if (scanner.hasNextDouble()) {
                    double valorSaque = scanner.nextDouble();
                    scanner.nextLine();

                    // validacoes do saque
                    if (valorSaque <= 0) {
                        System.out.println("Valor invalido! O saque precisa ser maior que zero.");
                    } else if (valorSaque > saldo) {
                        System.out.printf("Saldo insuficiente! Seu saldo atual e: R$ %.2f%n", saldo);
                    } else {
                        saldo = saldo - valorSaque;
                        System.out.printf("Saque realizado! Novo saldo: R$ %.2f%n", saldo);
                    }
                } else {
                    System.out.println("Valor invalido! Digite um numero.");
                    scanner.nextLine();
                }

            } else if (opcao == 4) {
                System.out.println("O FIAP Bank agradece sua preferencia!");

            } else {
                System.out.println("Opcao invalida! Escolha entre 1 e 4.");
            }
        }

        scanner.close();
    }
}
