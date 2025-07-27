# 🚀 Sistema de Gestão de Espaços Acadêmicos (SIGA)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![SQLite](https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-blue?style=for-the-badge&logo=java&logoColor=white)

## 📄 Sobre o Projeto

[cite_start]Este projeto é um **Sistema de Gestão de Espaços Acadêmicos** [cite: 2] [cite_start]desenvolvido como trabalho final para a disciplina de Linguagem de Programação II (LP2)[cite: 1].

[cite_start]O objetivo é aplicar conceitos de programação orientada a objetos, persistência de dados com banco de dados relacional, criação de interfaces gráficas e controle de acesso de usuários para criar um sistema completo e funcional[cite: 4]. [cite_start]A aplicação permite o gerenciamento e a reserva de diversos espaços dentro de uma instituição de ensino, como salas de aula, laboratórios e auditórios[cite: 4, 7].

## ✨ Funcionalidades Principais

* **Gestão de Espaços:**
    * [cite_start]Cadastro de múltiplos tipos de espaços: salas de aula, laboratórios, salas de reunião, quadras/campos esportivos e auditórios[cite: 7].
    * [cite_start]Cada tipo de espaço possui características comuns e atributos únicos[cite: 7].
    * [cite_start]CRUD completo para todos os tipos de espaços[cite: 23].

* **Gestão de Usuários:**
    * [cite_start]Sistema de login com autenticação e níveis de acesso diferenciados[cite: 13].
    * [cite_start]Gerenciamento de usuários com perfis de **Administrador** e **Usuário Comum**[cite: 8].

* **Sistema de Reservas:**
    * [cite_start]Permite a reserva de espaços por usuários[cite: 9].
    * [cite_start]Controla a disponibilidade dos espaços por data e horário, evitando conflitos[cite: 9].

* **Relatórios e Exportação:**
    * [cite_start]Geração de relatórios e estatísticas de uso dos espaços[cite: 10].
    * [cite_start]Exportação de documentos importantes[cite: 11]:
        * [cite_start]Comprovante de reserva[cite: 11].
        * [cite_start]Arquivo CSV com registros de reservas[cite: 11, 12].
        * [cite_start]Arquivo TXT com log de ações no sistema[cite: 12].

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 17+)
* [cite_start]**Interface Gráfica:** Java Swing [cite: 4]
* [cite_start]**Banco de Dados:** SQLite (pode ser adaptado para MySQL) [cite: 22]
* [cite_start]**Arquitetura:** Model-View-Controller (MVC) [cite: 25]
* [cite_start]**Conceitos:** Programação Orientada a Objetos (POO) [cite: 26]

## ⚙️ Como Executar o Projeto

Siga os passos abaixo para configurar e executar o projeto em seu ambiente local.

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:
* [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/downloads/) - Versão 17 ou superior.
* Uma IDE Java, como [Eclipse](https://www.eclipse.org/), [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou [VS Code](https://code.visualstudio.com/) com o Java Extension Pack.
* [DB Browser for SQLite](https://sqlitebrowser.org/) para visualizar e gerenciar o banco de dados.

### Passos para Instalação

1.  **Clone o repositório:**
    ```bash
    git clone [https://URL-DO-SEU-REPOSITORIO.git](https://https://github.com/Irregular-JV/TRABALHO-DE-LP2-FINAL.git)
    ```

2.  **Abra o projeto na sua IDE:**
    * Importe o projeto clonado na sua IDE de preferência.

3.  **Configure a Biblioteca (Driver JDBC):**
    * Certifique-se de que o driver do SQLite (`sqlite-jdbc-....jar`) está na pasta `lib` do projeto.
    * Adicione o arquivo `.jar` ao **Build Path** do projeto na sua IDE. (Ex: Clique direito no .jar > Build Path > Add to Build Path no Eclipse).

4.  **Configure o Banco de Dados:**
    * O banco de dados `banco.db` está localizado na raiz do projeto.
    * Abra o `banco.db` com o DB Browser for SQLite e execute um script SQL para criar as tabelas `Usuario` e `Espaco`.

5.  **Execute a Aplicação:**
    * Encontre a classe `Main.java` (ou a classe que contém o método `main`).
    * Clique com o botão direito e execute como "Java Application". A tela de login deve aparecer.

## 👨‍💻 Contribuidores

Este projeto foi desenvolvido com dedicação pela seguinte equipe:

* **Francisco Matheus de Oliveira Lima**
* **João Vitor dos Santos Conceição**
* **Rafael Soares Britto**
* **Carlos Eduardo Carvalho Lobato**

---
[cite_start]*Projeto apresentado como requisito parcial para aprovação na disciplina de Linguagem de Programação II[cite: 1].*
