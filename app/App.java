package app;

import facade.BibliotecaFacade;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import model.livro.Livro;
import model.usuario.Usuario;
import model.emprestimo.Emprestimo;
import model.reserva.Reserva;

import repository.RepositoryException;
import facade.BibliotecaException;

import javax.swing.*;

class App {

  static BibliotecaFacade facade = new BibliotecaFacade();
  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    facade = new BibliotecaFacade();

    CriaDadosDeTeste();

    int opcao;

    do {
      limpaTela();
      System.out.println("MENU PRINCIPAL");
      System.out.println("==== =========");
      System.out.println();
      System.out.println("<1> Gerenciar Usuarios");
      System.out.println("<2> Gerenciar Livros");
      System.out.println("<3> Gerenciar Empréstimos");
      System.out.println("<4> Gerenciar Reservas");
      System.out.println("<0> Sair");
      System.out.println();
      System.out.print("Escolha uma opção: ");

      try {
        opcao = Integer.valueOf(scanner.nextLine());
      } catch (Exception e) {
        opcao = 0;
      }

      switch (opcao) {
        case 0:
          limpaTela();
          break;
        case 1:
          gestorUsuarios();
          break;
        case 2:
          gestorLivros();
          break;
        case 3:
          gestorEmprestimos();
          break;
        case 4:
          gestorReservas();
      }
    } while (opcao != 0);

    facade.exit();
    System.out.println("Programa terminado.");
  }

  private static void limpaTela() {
    for (int i = 0; i < 25; i++) {
      System.out.println();
    }
  }

  private static void gestorUsuarios() {
    int opcao;
    do {
      limpaTela();
      System.out.println("GERENCIAR USUARIOS");
      System.out.println("========= ========");
      System.out.println();
      System.out.println("<1> Incluir Usuário");
      System.out.println("<2> Buscar Usuário");
      System.out.println("<3> Alterar Usuário");
      System.out.println("<4> Excluir Usuário");
      System.out.println("<5> Listar Usuários");
      System.out.println("<0> Menu Principal");
      System.out.println();
      System.out.print("Escolha uma opção: ");

      try {
        opcao = Integer.valueOf(scanner.nextLine());
      } catch (Exception e) {
        opcao = 0;
      }

      switch (opcao) {
        case 0:
          limpaTela();
          break;
        case 1:
          incluirUsuario();
          break;
        case 2:
          buscarUsuario();
          break;
        case 3:
          alterarUsuario();
          break;
        case 4:
          excluirUsuario();
          break;
        case 5:
          listarUsuarios();
          break;
      }
    } while (opcao != 0);
  }

  private static void incluirUsuario() {
    limpaTela();
    System.out.println("Cadastro de Usuário");
    System.out.println("======== == =======");
    System.out.print("Nome: ");
    String nome = scanner.next();
    System.out.print("CPF: ");
    String cpf = scanner.next();
    System.out.print("Telefone: ");
    String telefone = scanner.next();

    Usuario usuario = new Usuario(nome, cpf, telefone);

    try {
      facade.inserirUsuario(usuario);
      System.out.println();
      System.out.println("Usuário cadastrado!");
      System.out.println("ID: " + usuario.getId());
    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
    scanner.nextLine();
  }

    private static void buscarUsuario() {
    limpaTela();
    System.out.println("Buscar Usuário");
    System.out.println("====== =======");
    System.out.print("ID: ");
    String id = scanner.nextLine();

    try {
      Usuario usuario = facade.buscarUsuario(id);
      System.out.println();
      System.out.printf("Nome                 CPF            Telefone   ID\n");
      System.out.printf("==================== ============== ========== =====\n");
      System.out.printf("%-20s ", usuario.getNome());
      System.out.printf("%14s ", usuario.getCpf());
      System.out.printf("%10s ", usuario.getTelefone());
      System.out.printf("%5s\n", usuario.getId());
    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }  

  private static void alterarUsuario() {
    limpaTela();
    System.out.println("Alterar Usuário");
    System.out.println("======= =======");
    System.out.print("ID: ");
    String id = scanner.nextLine();

    try {
      Usuario usuario = facade.buscarUsuario(id);

      System.out.println();
      System.out.println("Nome: " + usuario.getNome());
      System.out.print("Nome (<enter> = Não alterar): ");
      String nome = scanner.nextLine();
      if (!nome.equals("")) {
        usuario.setNome(nome);
      }

      System.out.println("Telefone: " + usuario.getTelefone());
      System.out.print("Telefone (<enter> = Não alterar): ");
      String telefone = scanner.nextLine();
      if (!telefone.equals("")) {
        usuario.setTelefone(telefone);
      }

      System.out.println();

      facade.alterarUsuario(usuario);
      System.out.println("Usuário Alterado!");

    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void excluirUsuario() {
    limpaTela();
    System.out.println("Excluir Usuário");
    System.out.println("======= =======");
    System.out.print("ID: ");
    String id = scanner.nextLine();

    try {
      Usuario usuario = facade.buscarUsuario(id);
      System.out.println();
      System.out.printf("Nome                 CPF            Telefone   ID\n");
      System.out.printf("==================== ============== ========== =====\n");
      System.out.printf("%-20s ", usuario.getNome());
      System.out.printf("%14s ", usuario.getCpf());
      System.out.printf("%10s ", usuario.getTelefone());
      System.out.printf("%5s\n", usuario.getId());

      System.out.println();
      System.out.print("Excluí esse usuário? (s/n)? ");
      String resposta = scanner.nextLine();

      System.out.println();
      if (resposta.equalsIgnoreCase("s")) {
        facade.excluirUsuario(usuario);
        System.out.println("Usuário excluído!");
      }
    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void listarUsuarios() {
    limpaTela();
    List<Usuario> usuarios = facade.getAllUsuarios();
    System.out.printf("Nome                 CPF            Telefone   ID\n");
    System.out.printf("==================== ============== ========== =====\n");
    for (Usuario usuario : usuarios) {
      System.out.printf("%-20s ", usuario.getNome());
      System.out.printf("%14s ", usuario.getCpf());
      System.out.printf("%10s ", usuario.getTelefone());
      System.out.printf("%5s\n", usuario.getId());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void gestorLivros() {
    int opcao;
    do {
      limpaTela();
      System.out.println("GERENCIAR LIVROS");
      System.out.println("========= ======");
      System.out.println();
      System.out.println("<1> Incluir Livro");
      System.out.println("<2> Buscar Livro");
      System.out.println("<3> Alterar Livro");
      System.out.println("<4> Excluir Livro");
      System.out.println("<5> Listar Livros");
      System.out.println("<0> Menu Principal");
      System.out.println();
      System.out.print("Escolha uma opção: ");

      try {
        opcao = Integer.valueOf(scanner.nextLine());
      } catch (Exception e) {
        opcao = 0;
      }

      switch (opcao) {
        case 0:
          limpaTela();
          break;
        case 1:
          incluirLivro();
          break;
        case 2:
          buscarLivro();
          break;
        case 3:
          alterarLivro();
          break;
        case 4:
          excluirLivro();
          break;
        case 5:
          listarLivros();
          break;
      }
    } while (opcao != 0);
  }

  private static void incluirLivro() {
    limpaTela();
    System.out.println("Cadastro de Livro");
    System.out.println("======== == =====");
    System.out.print("Titulo: ");
    String titulo = scanner.next();
    System.out.print("Autor: ");
    String autor = scanner.next();
    System.out.print("Editora: ");
    String editora = scanner.next();
    System.out.print("Ano de Publicação: ");
    int anoPublicacao = scanner.nextInt();
    System.out.print("ISBN: ");
    String isbn = scanner.next();

    Livro livro = new Livro(titulo, autor, isbn, editora, anoPublicacao);

    try {
      facade.inserirLivro(livro);
      System.out.println();
      System.out.println("Livro cadastrado!");
    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
    scanner.nextLine();
  }

  private static void buscarLivro() {
    limpaTela();
    System.out.println("Buscar Livro");
    System.out.println("====== =====");
    System.out.print("ISBN: ");
    String isbn = scanner.nextLine();

    try {
      Livro livro = facade.buscarLivro(isbn);
      System.out.println();
      System.out.printf("Título               Autor                Editora    Ano de Publicação ISBN Situação\n");
      System.out.printf("==================== ==================== ========== ================= ==== ============\n");
      System.out.printf("%-20s ", livro.getTitulo());
      System.out.printf("%-20s ", livro.getAutor());
      System.out.printf("%-10s ", livro.getEditora());
      System.out.printf("%17s ", String.valueOf(livro.getAnoPublicacao()));
      System.out.printf("%4s ", livro.getIsbn());
      System.out.printf("%12s\n", livro.getSituacao());
    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void alterarLivro() {
    limpaTela();
    System.out.println("Alterar Livro");
    System.out.println("======= =====");
    System.out.print("ISBN: ");
    String isbn = scanner.nextLine();

    try {
      Livro livro = facade.buscarLivro(isbn);

      System.out.println();
      System.out.println("Título: " + livro.getTitulo());
      System.out.print("Título (<enter> = Não alterar): ");
      String titulo = scanner.nextLine();
      if (!titulo.equals("")) {
        livro.setTitulo(titulo);
      }

      System.out.println("Autor: " + livro.getAutor());
      System.out.print("Autor (<enter> = Não alterar): ");
      String autor = scanner.nextLine();
      if (!autor.equals("")) {
        livro.setAutor(autor);
      }

      System.out.println("Editora: " + livro.getEditora());
      System.out.print("Editora (<enter> = Não alterar): ");
      String editora = scanner.nextLine();
      if (!editora.equals("")) {
        livro.setEditora(editora);
      }

      System.out.println("Ano de Publicação: " + livro.getAnoPublicacao());
      System.out.print("Ano de Publicação (<enter> = Não alterar): ");
      String anoPublicacao = scanner.nextLine();
      if (!anoPublicacao.equals("")) {
        livro.setAnoPublicacao(anoPublicacao.charAt(3));
      }

      System.out.println();

      facade.alterarLivro(livro);
      System.out.println("Livro Alterado!");

    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void excluirLivro() {
    limpaTela();
    System.out.println("Excluir Livro");
    System.out.println("======= =====");
    System.out.print("ISBN: ");
    String isbn = scanner.nextLine();

    try {
      Livro livro = facade.buscarLivro(isbn);
      System.out.println();
      System.out.printf("Título               Autor                Editora    Ano de Publicação ISBN Situação\n");
      System.out.printf("==================== ==================== ========== ================= ==== ============\n");
      System.out.printf("%-20s ", livro.getTitulo());
      System.out.printf("%-20s ", livro.getAutor());
      System.out.printf("%-10s ", livro.getEditora());
      System.out.printf("%17s ", String.valueOf(livro.getAnoPublicacao()));
      System.out.printf("%4s ", livro.getIsbn());
      System.out.printf("%12s\n", livro.getSituacao());

      System.out.println();
      System.out.print("Excluí esse livro? (s/n)? ");
      String resposta = scanner.nextLine();

      System.out.println();
      if (resposta.equalsIgnoreCase("s")) {
        facade.excluirLivro(livro);
        System.out.println("Livro excluído!");
      }
    } catch (RepositoryException | BibliotecaException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void listarLivros() {
    limpaTela();
    List<Livro> livros = facade.getAllLivros();
    System.out.print("Título               Autor                Editora    Ano de Publicação ISBN Situação\n");
    System.out.print("==================== ==================== ========== ================= ==== ============\n");
    for (Livro livro : livros) {
      System.out.printf("%-20s ", livro.getTitulo());
      System.out.printf("%-20s ", livro.getAutor());
      System.out.printf("%-10s ", livro.getEditora());
      System.out.printf("%17s ", String.valueOf(livro.getAnoPublicacao()));
      System.out.printf("%4s ", livro.getIsbn());
      System.out.printf("%12s\n", livro.getSituacao());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void gestorEmprestimos() {
    int opcao;
    do {
      limpaTela();
      System.out.println("EMPRESTIMO DE LIVROS");
      System.out.println("========== == ======");
      System.out.println();
      System.out.println("<1> Realizar Empréstimo");
      System.out.println("<2> Verificar Empréstimo");
      System.out.println("<3> Alterar Empréstimo");
      System.out.println("<4> Renovar Empréstimo");
      System.out.println("<5> Excluir Empréstimo");
      System.out.println("<6> Devolver Empréstimo");
      System.out.println("<7> Listar Empréstimos");
      System.out.println("<0> Menu Principal");
      System.out.println();
      System.out.print("Escolha uma opção: ");

      try {
        opcao = Integer.valueOf(scanner.nextLine());
      } catch (Exception e) {
        opcao = 0;
      }

      switch (opcao) {
        case 0:
          limpaTela();
          break;
        case 1:
          realizarEmprestimo();
          break;
        case 2:
          verificarEmprestimo();
          break;
        case 3:
          alterarEmprestimo();
          break;
        case 4:
          renovarEmprestimo();
          break;
        case 5:
          excluirEmprestimo();
          break;
        case 6:
          devolverEmprestimo();
          break;
        case 7:
          listarEmprestimos();
          break;
      }
    } while (opcao != 0);
  }

  private static void realizarEmprestimo() {
    limpaTela();
    System.out.println("Empréstimo de Livro");
    System.out.println("========== == =====");

    System.out.print("ID do Usuário: ");
    String id = scanner.next();
    try {
      Usuario usuario = facade.buscarUsuario(id);
      System.out.println();
      System.out.printf("Usuário: %s\n", usuario.getNome());
      System.out.println();
      System.out.print("Realizar empréstimo? (s/n)? ");
      String resposta = scanner.next();

      System.out.println();
      if (resposta.equalsIgnoreCase("s")) {
        System.out.print("Dia: ");
        int dia = scanner.nextInt();
        System.out.print("Mês: ");
        int mes = scanner.nextInt();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        LocalDate dataEmprestimo = LocalDate.of(ano, mes, dia);

        do {
          System.out.print("ISBN do Livro: ");
          String isbn = scanner.next();
          Livro livro = facade.buscarLivro(isbn);
          System.out.println();
          System.out.printf("Livro: %s\n", livro.getTitulo());

          Emprestimo emprestimo = new Emprestimo(usuario, livro, dataEmprestimo);
          facade.realizarEmprestimo(emprestimo);
          System.out.println();
          System.out.println("Empréstimo realizado!");
          System.out.println();
          System.out.print("Realizar outro emprestimo? (s/n) ");
          resposta = scanner.next();
          System.out.println();
        } while (resposta.equalsIgnoreCase("s"));        
      }
    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
    scanner.nextLine();
  }

  private static void verificarEmprestimo() {
    int opcao = 0;
    List <Livro> livros = facade.getAllLivros();
    List <Emprestimo> emprestimos = new ArrayList<>();
    
    while (true) {
      limpaTela();
      
      System.out.println("\t\t\t\t\t\t\tLivros presentes na Biblioteca");
      System.out.print("\tTítulo               Autor                Editora    Ano de Publicação ISBN\n");
      System.out.print("\t==================== ==================== ========== ================= ====\n");
      for (Livro livro : livros) {
        System.out.printf("\t%-20s ", livro.getTitulo());
        System.out.printf("%-20s ", livro.getAutor());
        System.out.printf("%-10s ", livro.getEditora());
        System.out.printf("%17s ", String.valueOf(livro.getAnoPublicacao()));
        System.out.printf("%4s\n", livro.getIsbn());
      }
      
      System.out.println("\nVerificar Empréstimos");
      System.out.println("========= ===========");
      System.out.println("<1> Verificar por usuário");
      System.out.println("<2> Verificar por livro");
      System.out.println("<0> Voltar");
      System.out.println();
      System.out.print("Escolha uma opção: ");
    
      try {
        opcao = Integer.valueOf(scanner.nextLine());
      } catch (Exception e) {
        break;
      }
    
      System.out.println();
      if (opcao == 1) {
        System.out.print("ID do usuário: ");
        String id = scanner.nextLine();
        try {
          Usuario usuario = facade.buscarUsuario(id);
          emprestimos = facade.getAllEmprestimos(usuario);
          break;
        } catch (RepositoryException ex) {
          System.err.println(ex.getMessage());
          break;
        }
      }
      else if (opcao == 2) {
        System.out.print("ISBN do livro: ");
        String isbn = scanner.nextLine();
        try {
          Livro livro = facade.buscarLivro(isbn);
          emprestimos = facade.getAllEmprestimos(livro);
          break;
        } catch (RepositoryException ex) {
          System.err.println(ex.getMessage());
          break;
        }
      }
      else if (opcao == 0) {
        return;
      }
    }

    if (!emprestimos.isEmpty()) {
      limpaTela();
      System.out.printf("ID  Usuário              Livro                Data do Empréstimo Data de Devolução Situação\n");
      System.out.printf("=== ==================== ==================== ================== ================= =========\n");
      for (Emprestimo emprestimo : emprestimos) {
        System.out.printf("%3s ", emprestimo.getId());
        System.out.printf("%-20s ", emprestimo.getUsuario().getNome());
        System.out.printf("%-20s ", emprestimo.getLivro().getTitulo());
        System.out.printf("%18s ", emprestimo.getDataEmprestimo());
        System.out.printf("%17s ", emprestimo.getDataDevolucaoEsperada());
        System.out.printf("%9s\n", emprestimo.getSituacao());
      }
    }
    else {
      if (opcao == 1) {
        System.err.println("\nNão há empréstimos realizados para este usuário.");
      }
      else if (opcao == 2) {
        System.err.println("\nNão há empréstimos realizados para este livro.");
      } 
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void alterarEmprestimo() {
    limpaTela();
    System.out.println("Alterar Empréstimo");
    System.out.println("======= ==========");
    System.out.print("ID do Usuário: ");
    String id = scanner.nextLine();
    System.out.print("ISBN do Livro: ");
    String isbn = scanner.nextLine();

    try {
      Usuario usuario = facade.buscarUsuario(id);
      Livro livro = facade.buscarLivro(isbn);
      Emprestimo emprestimo = facade.buscarEmprestimo(usuario, livro);

      //criação de uma cópia do emprestimo;
      LocalDate dataEmprestimo = emprestimo.getDataEmprestimo();
      Emprestimo emprestimoAlterado = new Emprestimo(usuario, livro, dataEmprestimo);

      System.out.println();
      System.out.print("ID  Usuário              Livro                Data do Empréstimo Data de Devolução Situação\n");
      System.out.print("=== ==================== ==================== ================== ================= =========\n");
      System.out.printf("%3s ", emprestimo.getId());
      System.out.printf("%-20s ", emprestimo.getUsuario().getNome());
      System.out.printf("%-20s ", emprestimo.getLivro().getTitulo());
      System.out.printf("%18s ", emprestimo.getDataEmprestimo());
      System.out.printf("%17s ", emprestimo.getDataDevolucaoEsperada());
      System.out.printf("%9s\n", emprestimo.getSituacao());

      System.out.println();
      System.out.print("Alterar este empréstimo? (s/n) ");
      String resposta = scanner.nextLine();

      System.out.println();
      if (resposta.equalsIgnoreCase("s")) {
        System.out.println("Usuário: " + usuario.getNome());
        System.out.print("Alterar Usuário? (s/n) ");
        resposta = scanner.nextLine();
        System.out.println();
        if (resposta.equalsIgnoreCase("s")) {
          System.out.print("ID do Usuário: ");
          id = scanner.nextLine();
          usuario = facade.buscarUsuario(id);
          emprestimoAlterado.setUsuario(usuario);
          System.out.println();
        }

        System.out.println("Livro: " + livro.getTitulo());
        System.out.print("Alterar livro? (s/n) ");
        resposta = scanner.nextLine();
        System.out.println();
        if (resposta.equalsIgnoreCase("s")) {
          System.out.print("ISBN do livro: ");
          isbn = scanner.nextLine();
          livro = facade.buscarLivro(isbn);
          emprestimoAlterado.setLivro(livro);
          System.out.println();
        }

        System.out.println("Data de Empréstimo: " + emprestimo.getDataEmprestimo());
        System.out.print("Alterar data? (s/n): ");
        resposta = scanner.nextLine();
        System.out.println();
        if (resposta.equalsIgnoreCase("s")) {
          System.out.print("Dia: ");
          int dia = scanner.nextInt();
          System.out.print("Mês: ");
          int mes = scanner.nextInt();
          System.out.print("Ano: ");
          int ano = scanner.nextInt();
          dataEmprestimo = LocalDate.of(ano, mes, dia);
          emprestimoAlterado.setDataEmprestimo(dataEmprestimo);
          emprestimoAlterado.setDataDevolucaoEsperada(dataEmprestimo.plusDays(emprestimo.getDiasMaxEmprestimo()));
          System.out.println();
          scanner.nextLine();
        }

        facade.alterarEmprestimo(emprestimo, emprestimoAlterado);
        System.out.println("Empréstimo alterado!");
        System.out.println();
      }
    } catch (RepositoryException | BibliotecaException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void renovarEmprestimo() {
    limpaTela();
    System.out.println("Renovar Empréstimo");
    System.out.println("======= ==========");
    System.out.print("ID do Usuário: ");
    String id = scanner.nextLine();
    System.out.print("ISBN do Livro: ");
    String isbn = scanner.nextLine();

    try {
      Usuario usuario = facade.buscarUsuario(id);
      Livro livro = facade.buscarLivro(isbn);
      Emprestimo emprestimo = facade.buscarEmprestimo(usuario, livro);
      System.out.println();
      System.out.printf("ID  Usuário              Livro                Data do Empréstimo Data de Devolução Situação\n");
      System.out.printf("=== ==================== ==================== ================== ================= =========\n");
      System.out.printf("%3s ", emprestimo.getId());
      System.out.printf("%-20s ", emprestimo.getUsuario().getNome());
      System.out.printf("%-20s ", emprestimo.getLivro().getTitulo());
      System.out.printf("%18s ", emprestimo.getDataEmprestimo());
      System.out.printf("%17s ", emprestimo.getDataDevolucaoEsperada());
      System.out.printf("%9s\n", emprestimo.getSituacao());

      System.out.println();
      System.out.print("Renovar esse empréstimo? (s/n) ");
      String resposta = scanner.nextLine();

      System.out.println();
      if (resposta.equalsIgnoreCase("s")) {
        int diasMaxEmprestimo = emprestimo.getDiasMaxEmprestimo();
        LocalDate dataDevolucaoRenovada = emprestimo.getDataDevolucaoEsperada().plusDays(diasMaxEmprestimo);
        emprestimo.setDataDevolucaoEsperada(dataDevolucaoRenovada);

        facade.renovarEmprestimo(emprestimo);
        System.out.println("Empréstimo renovado!");
      }
    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void excluirEmprestimo() {
    limpaTela();
    System.out.println("Excluir Empréstimo");
    System.out.println("======= ==========");
    System.out.print("ID do Usuário: ");
    String id = scanner.nextLine();
    System.out.print("ISBN do Livro: ");
    String isbn = scanner.nextLine();

    try {
      Usuario usuario = facade.buscarUsuario(id);
      Livro livro = facade.buscarLivro(isbn);
      Emprestimo emprestimo = facade.buscarEmprestimo(usuario, livro);
      System.out.println();
      System.out.printf("ID  Usuário              Livro                Data do Empréstimo Data de Devolução Situação\n");
      System.out.printf("=== ==================== ==================== ================== ================= =========\n");
      System.out.printf("%3s ", emprestimo.getId());
      System.out.printf("%-20s ", emprestimo.getUsuario().getNome());
      System.out.printf("%-20s ", emprestimo.getLivro().getTitulo());
      System.out.printf("%18s ", emprestimo.getDataEmprestimo());
      System.out.printf("%17s ", emprestimo.getDataDevolucaoEsperada());
      System.out.printf("%9s\n", emprestimo.getSituacao());

      System.out.println();
      System.out.print("Excluí esse empréstimo? (s/n)? ");
      String resposta = scanner.nextLine();

      if (resposta.equalsIgnoreCase("s")) {
        facade.excluirEmprestimo(emprestimo);
        System.out.println();
        System.out.println("Empréstimo excluído!");
      }
    } catch (RepositoryException | BibliotecaException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  public static void devolverEmprestimo() {
    limpaTela();
    System.out.println("Devolução de Empréstimo");
    System.out.println("========= == ==========");
    System.out.print("ID do Usuário: ");
    String id = scanner.nextLine();
    System.out.print("ISBN do Livro: ");
    String isbn = scanner.nextLine();

    try {
      Usuario usuario = facade.buscarUsuario(id);
      Livro livro = facade.buscarLivro(isbn);
      Emprestimo emprestimo = facade.buscarEmprestimo(usuario, livro);
      System.out.println();
      System.out.printf("ID  Usuário              Livro                Data do Empréstimo Data de Devolução Situação\n");
      System.out.printf("=== ==================== ==================== ================== ================= =========\n");
      System.out.printf("%3s ", emprestimo.getId());
      System.out.printf("%-20s ", emprestimo.getUsuario().getNome());
      System.out.printf("%-20s ", emprestimo.getLivro().getTitulo());
      System.out.printf("%18s ", emprestimo.getDataEmprestimo());
      System.out.printf("%17s ", emprestimo.getDataDevolucaoEsperada());
      System.out.printf("%9s\n", emprestimo.getSituacao());

      System.out.println();
      System.out.print("Devolver? (s/n)? ");
      String resposta = scanner.nextLine();

      System.out.println();
      if (resposta.equalsIgnoreCase("s")) {
        System.out.println("Data de Devolução:");
        System.out.print("Dia: ");
        int dia = scanner.nextInt();
        System.out.print("Mês: ");
        int mes = scanner.nextInt();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        LocalDate dataDevolucao = LocalDate.of(ano, mes, dia);
        facade.devolverEmprestimo(emprestimo, dataDevolucao);
        System.out.println();

        System.out.println("Empréstimo devolvido!");
        System.out.println();
      }
    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
    scanner.nextLine();
  }

  private static void listarEmprestimos() {
    limpaTela();
    List<Emprestimo> emprestimos = facade.getAllEmprestimos();
    System.out.println("ID  Usuário              Livro                Data do Empréstimo Data de Devolução Situação");
    System.out.println("=== ==================== ==================== ================== ================= =========");
    for (Emprestimo emprestimo : emprestimos) {
      String situacao = emprestimo.getSituacao();
      if (!(situacao.equals("Devolvido"))){
        System.out.printf("%3s ", emprestimo.getId());
        System.out.printf("%-20s ", emprestimo.getUsuario().getNome());
        System.out.printf("%-20s ", emprestimo.getLivro().getTitulo());
        System.out.printf("%18s ", emprestimo.getDataEmprestimo());
        System.out.printf("%17s ", emprestimo.getDataDevolucaoEsperada());
        System.out.printf("%9s\n", emprestimo.getSituacao());
      }

    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void gestorReservas() {
    int opcao;
    do {
      limpaTela();
      System.out.println("RESERVA DE LIVROS");
      System.out.println("======= == ======");
      System.out.println();
      System.out.println("<1> Solicitar Reserva");
      System.out.println("<2> Verificar Reserva");
      System.out.println("<3> Alterar Reserva");
      System.out.println("<4> Excluir Reserva");
      System.out.println("<5> Listar Reservas");
      System.out.println("<0> Menu Principal");
      System.out.println();
      System.out.print("Escolha uma opção: ");

      try {
        opcao = Integer.valueOf(scanner.nextLine());
      } catch (Exception e) {
        opcao = 0;
      }

      switch (opcao) {
        case 0:
          limpaTela();
          break;
        case 1:
          solicitarReserva();
          break;
        case 2:
          verificarReserva();
          break;
        case 3:
          alterarReserva();
          break;
        case 4:
          excluirReserva();
          break;
        case 5:
          listarReservas();
          break;
      }
    } while (opcao != 0);
  }

  private static void solicitarReserva() {
    limpaTela();
    System.out.println("Reservar Livro");
    System.out.println("======== =====");

    System.out.print("ID do Usuário: ");
    String id = scanner.next();
    try {
      Usuario usuario = facade.buscarUsuario(id);
      System.out.println();
      System.out.printf("Usuário: %s\n", usuario.getNome());
      System.out.println();
      System.out.print("Solicitar reserva? (s/n)? ");
      String resposta = scanner.next();

      System.out.println();
      if (resposta.equalsIgnoreCase("s")) {
        System.out.print("ISBN do Livro: ");
        String isbn = scanner.next();
        Livro livro = facade.buscarLivro(isbn);
        System.out.println();
        System.out.println("Livro: " + livro.getTitulo());
        System.out.println();
        Reserva reserva = new Reserva(usuario, livro);
        facade.solicitarReserva(reserva);
        System.out.println("Reserva solicitada para: " + reserva.getDataReserva() + ".");
      }
    } catch (RepositoryException | BibliotecaException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
    scanner.nextLine();
  }

  private static void verificarReserva() {
    int opcao = 0;
    List <Livro> livros = facade.getAllLivros();
    List <Reserva> reservas = new ArrayList<>();
    
    while (true) {
      limpaTela();
      
      System.out.println("\t\t\t\t\t\t\tLivros presentes na Biblioteca");
      System.out.print("\tTítulo               Autor                Editora    Ano de Publicação ISBN\n");
      System.out.print("\t==================== ==================== ========== ================= ====\n");
      for (Livro livro : livros) {
        System.out.printf("\t%-20s ", livro.getTitulo());
        System.out.printf("%-20s ", livro.getAutor());
        System.out.printf("%-10s ", livro.getEditora());
        System.out.printf("%17s ", String.valueOf(livro.getAnoPublicacao()));
        System.out.printf("%4s\n", livro.getIsbn());
      }
      
      System.out.println("\nVerificar Reservas");
      System.out.println("========= ========");
      System.out.println("<1> Verificar por usuário");
      System.out.println("<2> Verificar por livro");
      System.out.println("<0> Voltar");
      System.out.println();
      System.out.print("Escolha uma opção: ");
    
      try {
        opcao = Integer.valueOf(scanner.nextLine());
      } catch (Exception e) {
        break;
      }
    
      System.out.println();
      if (opcao == 1) {
        System.out.print("ID do usuário: ");
        String id = scanner.nextLine();
        try {
          Usuario usuario = facade.buscarUsuario(id);
          reservas = facade.getAllReservas(usuario);
          break;
        } catch (RepositoryException ex) {
          System.err.println(ex.getMessage());
          break;
        }
      }
      else if (opcao == 2) {
        System.out.print("ISBN do livro: ");
        String isbn = scanner.nextLine();
        try {
          Livro livro = facade.buscarLivro(isbn);
          reservas = facade.getAllReservas(livro);
          break;
        } catch (RepositoryException ex) {
          System.err.println(ex.getMessage());
          break;
        }
      }
      else if (opcao == 0) {
        return;
      }
    }

    if (!reservas.isEmpty()) {
      limpaTela();
      System.out.printf("Usuário              Livro                Data da Solicitação Data de Vencimento Situação\n");
      System.out.printf("==================== ==================== =================== ================== ========\n");
      for (Reserva reserva : reservas) {
        System.out.printf("%-20s ", reserva.getUsuario().getNome());
        System.out.printf("%-20s ", reserva.getLivro().getTitulo());
        System.out.printf("%19s ", reserva.getDataReserva());
        System.out.printf("%18s ", reserva.getDataVencimento());
        System.out.printf("%8s\n", reserva.getSituacao());
      }
    }
    else {
      if (opcao == 1) {
        System.err.println("\nNão há reservas solicitadas para este usuário.");
      }
      else if (opcao == 2) {
        System.err.println("\nNão há reservas solicitadas para este livro.");
      } 
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void alterarReserva() {
    limpaTela();
    System.out.println("Alterar Reserva");
    System.out.println("======= =======");
    System.out.print("ID do Usuário: ");
    String id = scanner.nextLine();
    System.out.print("ISBN do Livro: ");
    String isbn = scanner.nextLine();

    try {
      Usuario usuario = facade.buscarUsuario(id);
      Livro livro = facade.buscarLivro(isbn);
      Reserva reserva = facade.buscarReserva(usuario, livro);
      Reserva reservaAlterada = new Reserva(usuario, livro);

      System.out.println();
      System.out.printf("Usuário              Livro                Data da Solicitação Data de Vencimento Situação\n");
      System.out.printf("==================== ==================== =================== ================== ========\n");
      System.out.printf("%-20s ", reserva.getUsuario().getNome());
      System.out.printf("%-20s ", reserva.getLivro().getTitulo());
      System.out.printf("%19s ", reserva.getDataReserva());
      System.out.printf("%18s ", reserva.getDataVencimento());
      System.out.printf("%8s\n", reserva.getSituacao());

      System.out.println();
      System.out.print("Alterar essa reserva? (s/n) ");
      String resposta = scanner.nextLine();

      System.out.println();
      if (resposta.equalsIgnoreCase("s")) {
        System.out.println("Usuário: " + usuario.getNome());
        System.out.print("Alterar usuário? (s/n) ");
        resposta = scanner.nextLine();
        System.out.println();
        if (resposta.equalsIgnoreCase("s")) {
          System.out.print("ID do usuário: ");
          id = scanner.nextLine();
          usuario = facade.buscarUsuario(id);
          reservaAlterada.setUsuario(usuario);
          System.out.println();
        }

        System.out.println("Livro: " + livro.getTitulo());
        System.out.print("Alterar livro? (s/n) ");
        resposta = scanner.nextLine();
        System.out.println();
        if (resposta.equalsIgnoreCase("s")) {
          System.out.print("ISBN do livro: ");
          isbn = scanner.nextLine();
          livro = facade.buscarLivro(isbn);
          reservaAlterada.setLivro(livro);
          System.out.println();
        }

        facade.alterarReserva(reserva, reservaAlterada);
        System.out.println("Reserva alterada!");
      }
    } catch (RepositoryException | BibliotecaException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void excluirReserva() {
    limpaTela();
    System.out.println("Excluir Reserva");
    System.out.println("======= =======");
    System.out.print("ID do Usuário: ");
    String id = scanner.nextLine();
    System.out.print("ISBN do Livro: ");
    String isbn = scanner.nextLine();

    try {
      Usuario usuario = facade.buscarUsuario(id);
      Livro livro = facade.buscarLivro(isbn);
      Reserva reserva = facade.buscarReserva(usuario, livro);
      System.out.println();
      System.out.printf("Usuário              Livro                Data da Solicitação Data de Vencimento Situação\n");
      System.out.printf("==================== ==================== =================== ================== ========\n");
      System.out.printf("%-20s ", reserva.getUsuario().getNome());
      System.out.printf("%-20s ", reserva.getLivro().getTitulo());
      System.out.printf("%19s ", reserva.getDataReserva());
      System.out.printf("%18s ", reserva.getDataVencimento());
      System.out.printf("%8s\n", reserva.getSituacao());

      System.out.println();
      System.out.print("Excluí essa reserva? (s/n)? ");
      String resposta = scanner.nextLine();

      System.out.println();
      if (resposta.equalsIgnoreCase("s")) {
        facade.excluirReserva(reserva);
        System.out.println("Reserva excluída!");
      }
    } catch (RepositoryException ex) {
      System.err.println(ex.getMessage());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();
  }

  private static void listarReservas() {
    limpaTela();
    List<Reserva> reservas = facade.getAllReservas();
    System.out.printf("Usuário              Livro                Data da Solicitação Data de Vencimento Situação\n");
    System.out.printf("==================== ==================== =================== ================== ========\n");
    for (Reserva reserva : reservas) {
      System.out.printf("%-20s ", reserva.getUsuario().getNome());
      System.out.printf("%-20s ", reserva.getLivro().getTitulo());
      System.out.printf("%19s ", reserva.getDataReserva());
      System.out.printf("%18s ", reserva.getDataVencimento());
      System.out.printf("%8s\n", reserva.getSituacao());
    }

    System.out.println();
    System.out.println("tecle <enter> para voltar");
    scanner.nextLine();    
  }

  private static void CriaDadosDeTeste() {
    try {
      //Livros testes
      facade.inserirLivro(new Livro("O Homem de Giz", "C. J. Tudor", "001", "Intrísica", 2018));
      facade.inserirLivro(new Livro("O Homem de Giz", "C. J. Tudor", "002", "Intrísica", 2018));
      facade.inserirLivro(new Livro("O Homem de Giz", "C. J. Tudor", "003", "Intrísica", 2018));
      facade.inserirLivro(new Livro("Bird Box", "Josh Malerman", "004", "Intrísica", 2014));
      facade.inserirLivro(new Livro("Bird Box", "Josh Malerman", "005", "Intrísica", 2014));
      facade.inserirLivro(new Livro("C: como programar", "Harvey M. Deitel", "006", "Pearson", 2011));
      facade.inserirLivro(new Livro("C: como programar", "Harvey M. Deitel", "007", "Pearson", 2011));
      facade.inserirLivro(new Livro("Java: como programar", "Harvey M. Deitel", "008", "Pearson", 2011));
      facade.inserirLivro(new Livro("Java: como programar", "Harvey M. Deitel", "009", "Pearson", 2017));
      facade.inserirLivro(new Livro("Java: como programar", "Harvey M. Deitel", "010", "Pearson", 2011));

      //Usuários testes
      facade.inserirUsuario(new Usuario("José Silva", "123.456.789-00", "99123-4567"));
      facade.inserirUsuario(new Usuario("Fernanda Costa", "123.456.789-01", "99765-4321"));
      facade.inserirUsuario(new Usuario("Ana Castro", "123.456.789-02", "99999-9999"));
      facade.inserirUsuario(new Usuario("João Batista", "123.456.789-03", "99191-9191"));
      facade.inserirUsuario(new Usuario("Paula Leite", "123.456.789-04", "99876-5432"));

      //Empréstimos testes
      LocalDate hoje = LocalDate.now();
      Livro livro = facade.buscarLivro("008"); // Java: como programar
      Usuario usuario = facade.buscarUsuario("04"); // João Batista
      // 10 dias atrás
      facade.realizarEmprestimo(new Emprestimo(usuario, livro, hoje.plusDays(-10)));
      // 7 dias atrás
      Emprestimo emprestimo = facade.buscarEmprestimo(usuario, livro);
      facade.devolverEmprestimo(emprestimo, hoje.plusDays(-7));
      facade.realizarEmprestimo(new Emprestimo(facade.buscarUsuario("05"), facade.buscarLivro("004"), hoje.plusDays(-7)));
      // 6 dias atrás
      facade.realizarEmprestimo(new Emprestimo(facade.buscarUsuario("03"), livro, hoje.plusDays(-6)));
      facade.realizarEmprestimo(new Emprestimo(facade.buscarUsuario("03"),facade.buscarLivro("007"),hoje.plusDays(-6)));
      // 4 dias atrás
      facade.realizarEmprestimo(new Emprestimo(facade.buscarUsuario("04"), facade.buscarLivro("005"), hoje.plusDays(-4)));
      // 3 dias atrás
      emprestimo = facade.buscarEmprestimo(facade.buscarUsuario("05"), facade.buscarLivro("004"));
      facade.devolverEmprestimo(emprestimo, hoje.plusDays(-3));      
      // hoje      
      facade.realizarEmprestimo(new Emprestimo(facade.buscarUsuario("01"), facade.buscarLivro("004"), hoje));
      
      //Reservas testes
      facade.solicitarReserva(new Reserva(facade.buscarUsuario("02"), facade.buscarLivro("004")));
      facade.solicitarReserva(new Reserva(facade.buscarUsuario("02"), facade.buscarLivro("005")));
      facade.solicitarReserva(new Reserva(facade.buscarUsuario("02"), facade.buscarLivro("002")));
    } catch (RepositoryException | BibliotecaException e) {
      System.out.println(e.getMessage());
    }
  }
}