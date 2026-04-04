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

        scanner.close();
    }
}
