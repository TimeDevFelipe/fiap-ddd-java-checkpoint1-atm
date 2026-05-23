# FIAP Bank ATM - Sistema de Caixa Eletrônico

Checkpoint 3 da disciplina de Domain Driven Design - Java (2ESPG).

## Sobre o projeto

Simulação de um terminal de autoatendimento (ATM) rodando via console, desenvolvido em Java com arquitetura orientada a objetos, contratos via interfaces e resiliência com exceções de domínio.

## Estrutura de Pacotes

```
src/br/com/fiapbank/
├── model/
│   ├── interfaces/        # Contratos (Autorizavel)
│   ├── exceptions/        # Exceções de domínio
│   ├── Conta.java         # Classe abstrata base
│   ├── ContaCorrente.java
│   ├── ContaPoupanca.java
│   ├── Cliente.java
│   └── ContaAcesso.java   # implements Autorizavel
├── application/           # Regras de aplicação
├── infrastructure/        # Repositório em memória
└── presentation/          # Terminal ATM (menu)
```

## Funcionalidades

- Autenticação com bloqueio após 3 tentativas incorretas
- Consulta de saldo
- Saque com validação (ContaCorrente usa limite)
- Depósito com validação de valor
- Extrato de movimentações
- Tratamento de erros amigável — sistema não quebra no console

## Contas de Teste

| Conta | Senha | Tipo | Saldo | Limite |
|-------|-------|------|-------|--------|
| 001-1 | 1234 | Corrente | R$ 1.500,00 | R$ 500,00 |
| 002-1 | 4321 | Poupança | R$ 800,00 | — |

## Como executar

1. Compile (na raiz do projeto):
```bash
javac -d out $(find src -name "*.java")
```
Windows (PowerShell):
```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java | % { $_.FullName })
```

2. Execute:
```bash
java -cp out br.com.fiapbank.presentation.TerminalATM
```

## Tecnologias

- Java — OOP com herança, polimorfismo e interfaces
- Classes Wrapper (Integer, Double, Boolean)
- Exceções customizadas de domínio (RuntimeException)
- Scanner para entrada de dados

## Autor

Felipe — FIAP Engenharia de Software
