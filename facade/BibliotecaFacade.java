package facade;

import repository.livro.RepositorioLivro;
import model.livro.LivroNaoDisponivelException;
import repository.livro.LivroJaCadastradoException;
import repository.livro.LivroNaoCadastradoException;
import repository.livro.LivroJaReservadoException;
import model.livro.LivroNaoDisponivelException;
import model.livro.Livro;
import repository.usuario.RepositorioUsuario;
import repository.usuario.CpfJaCadastradoException;
import repository.usuario.UsuarioJaCadastradoException;
import repository.usuario.UsuarioNaoCadastradoException;
import model.usuario.Usuario;
import repository.emprestimo.RepositorioEmprestimo;
import repository.emprestimo.LivroJaEmprestadoException;
//import repository.emprestimo.EmprestimoJaRealizadoException;
import repository.emprestimo.EmprestimoNaoEncontradoException;
import model.emprestimo.Emprestimo;
import repository.reserva.RepositorioReserva;
import repository.reserva.ReservaJaRealizadaException;
import repository.reserva.ReservaNaoEncontradaException;
import model.reserva.Reserva;

import java.util.List;
import java.time.LocalDate;

import repository.livro.RepositorioLivroLista;
import repository.usuario.RepositorioUsuarioLista;
import repository.emprestimo.RepositorioEmprestimoLista;
import repository.reserva.RepositorioReservaLista;

public class BibliotecaFacade {

  private RepositorioLivro repositorioLivro;
  private RepositorioUsuario repositorioUsuario;
  private RepositorioEmprestimo repositorioEmprestimo;
  private RepositorioReserva repositorioReserva;

  public BibliotecaFacade() {
    repositorioLivro = new RepositorioLivroLista();
    repositorioUsuario = new RepositorioUsuarioLista();
    repositorioEmprestimo = new RepositorioEmprestimoLista();
    repositorioReserva = new RepositorioReservaLista();
  }

  public void inserirLivro(Livro livro) throws LivroJaCadastradoException {
    repositorioLivro.inserirLivro(livro);
  }

  public void alterarLivro(Livro livro) throws LivroNaoCadastradoException {
    repositorioLivro.alterarLivro(livro);
  }

  public Livro buscarLivro(String isbn) throws LivroNaoCadastradoException {
    return repositorioLivro.buscarLivro(isbn);
  }

  public void excluirLivro(Livro livro) throws LivroNaoCadastradoException, BibliotecaException {
    if (livro.getSituacao().equals("Disponível")) {
      repositorioLivro.deletarLivro(livro);
    }
    else {
      throw new BibliotecaException("Não é possível excluir livro em circulação.");
    }
  }
  
  private void atualizarSituacaoLivro(Emprestimo emprestimo) throws LivroNaoDisponivelException {
    Livro livro = emprestimo.getLivro();
    String situacaoEmprestimo = emprestimo.getSituacao();
    String situacaoLivro = "";
    if (situacaoEmprestimo.equals("Devolvido")) {
      situacaoLivro = "Disponível";
    }
    String situacao = livro.verificarSituacao(situacaoLivro);
    livro.setSituacao(situacao);
  }
    
  public List<Livro> getAllLivros() {
    return repositorioLivro.getAll();
  }

  public void inserirUsuario(Usuario usuario) throws UsuarioJaCadastradoException, CpfJaCadastradoException {
    repositorioUsuario.inserirUsuario(usuario);
  }

  public void alterarUsuario(Usuario usuario) throws UsuarioNaoCadastradoException {
    repositorioUsuario.alterarUsuario(usuario);
  }

  public Usuario buscarUsuario(String id) throws UsuarioNaoCadastradoException {
    return repositorioUsuario.buscarUsuario(id);
  }

  public void excluirUsuario(Usuario usuario) throws UsuarioNaoCadastradoException {
    repositorioUsuario.deletarUsuario(usuario);
  }

  public List<Usuario> getAllUsuarios() {
    return repositorioUsuario.getAll();
  }

  public void realizarEmprestimo(Emprestimo emprestimo) throws LivroNaoDisponivelException, LivroJaReservadoException {
    List <Reserva> reservas = getAllReservas();
    for (Reserva reserva : reservas) {
      if (reserva.getLivro().equals(emprestimo.getLivro())) { 
        LocalDate dataEmprestimo = emprestimo.getDataEmprestimo(); 
        LocalDate emprestimoAntes = reserva.getDataVencimento().plusDays(-8);
        LocalDate emprestimoDepois = reserva.getDataVencimento().plusDays(1);
        if (dataEmprestimo.isAfter(emprestimoAntes) && dataEmprestimo.isBefore(emprestimoDepois)) {
          System.err.println("Não é possível realizar o empréstimo para esta data.");
          throw new LivroJaReservadoException();
        }
      }
    }
    repositorioEmprestimo.inserirEmprestimo(emprestimo);
    atualizarSituacaoLivro(emprestimo);
  }
  
  public Emprestimo buscarEmprestimo(Usuario usuario, Livro livro) throws EmprestimoNaoEncontradoException, LivroJaEmprestadoException {
    List <Emprestimo> emprestimos = getAllEmprestimos(livro);
    Emprestimo emprestimo = repositorioEmprestimo.buscarEmprestimo(usuario, livro);
    
    if (!emprestimo.getSituacao().equals("Devolvido")) {
      return emprestimo;
    }
    
    for (Emprestimo verif_emprestimo : emprestimos) {
      if (verif_emprestimo.getUsuario().equals(usuario) && !verif_emprestimo.getSituacao().equals("Devolvido")) {
        return verif_emprestimo;
      }
      else {
        emprestimo = verif_emprestimo;
      }    
    }
    return emprestimo;
  }

  public void excluirEmprestimo(Emprestimo emprestimo) throws EmprestimoNaoEncontradoException, BibliotecaException {
    if (emprestimo.getSituacao().equals("Devolvido")) {
      repositorioEmprestimo.deletarEmprestimo(emprestimo);
    }
    else {
      throw new BibliotecaException("Não é possível excluir um empréstimo não devolvido.");
    }
  }

  public void alterarEmprestimo(Emprestimo emprestimo1, Emprestimo emprestimo2) throws EmprestimoNaoEncontradoException, LivroJaEmprestadoException, BibliotecaException {
    List <Emprestimo> emprestimos = getAllEmprestimos(emprestimo2.getLivro());
    if (!emprestimo1.getLivro().equals(emprestimo2.getLivro())) {
      if (emprestimo2.getLivro().getSituacao().equals("Indisponível")) {
        System.err.println("Não foi possível alterar este empréstimo.");
        for (Emprestimo emprestimo : emprestimos) {
          Usuario usuario = emprestimo.getUsuario();
          if (!emprestimo.getSituacao().equals("Devolvido")) {
            if (usuario.equals(emprestimo1.getUsuario()) || usuario.equals(emprestimo2.getUsuario())) {
              throw new BibliotecaException("Livro já está emprestado pelo usuário.");
            }
            else {
              throw new LivroJaEmprestadoException();
            }
          }
        }
      }
      else {
        emprestimo1.getLivro().setSituacao("Disponível");
        emprestimo2.getLivro().setSituacao("Indisponível");
        emprestimo1.setLivro(emprestimo2.getLivro());
      }
    }
    emprestimo1.setUsuario(emprestimo2.getUsuario());
    emprestimo1.setDataEmprestimo(emprestimo2.getDataEmprestimo());
    atualizarSituacaoEmprestimo(emprestimo1);
    repositorioEmprestimo.alterarEmprestimo(emprestimo1);
  }

  public void devolverEmprestimo(Emprestimo emprestimo, LocalDate dataDevolucao) throws EmprestimoNaoEncontradoException, LivroJaEmprestadoException, LivroNaoDisponivelException {
    emprestimo.setDataDevolucaoReal(dataDevolucao);
    atualizarSituacaoEmprestimo(emprestimo);
    atualizarSituacaoLivro(emprestimo);
    repositorioEmprestimo.alterarEmprestimo(emprestimo);
  }

  public void renovarEmprestimo(Emprestimo emprestimo) throws EmprestimoNaoEncontradoException, LivroJaEmprestadoException, LivroJaReservadoException {
    try {
      Reserva reserva = buscarReserva(emprestimo.getUsuario(), emprestimo.getLivro());
      System.err.println("Não é possível renovar o empréstimo.");
      throw new LivroJaReservadoException();
    } catch (ReservaNaoEncontradaException ex) {
      atualizarSituacaoEmprestimo(emprestimo);
      repositorioEmprestimo.alterarEmprestimo(emprestimo);
    }
  }

  private void atualizarSituacaoEmprestimo(Emprestimo emprestimo) {
    String situacao = emprestimo.verificarSituacao();
    emprestimo.setSituacao(situacao);
  }

  public List<Emprestimo> getAllEmprestimos(Livro livro) {
    return repositorioEmprestimo.getAll(livro);
  }

  public List<Emprestimo> getAllEmprestimos() {
    return repositorioEmprestimo.getAll();
  }

  public void solicitarReserva(Reserva reserva) throws BibliotecaException {
    LocalDate dataReserva = LocalDate.now(); 
    Usuario usuario = reserva.getUsuario();
    List <Emprestimo> emprestimos = getAllEmprestimos(reserva.getLivro());
    for (Emprestimo emprestimo : emprestimos) {
      if (!emprestimo.getSituacao().equals("Devolvido")) {
        if (emprestimo.getUsuario().equals(usuario)) {
          System.err.println("Livro já está emprestado para o usuário.");
          throw new BibliotecaException("Não é possivel solicitar reserva para um usuário com empréstimo não devolvido do mesmo livro.");
        }
        else {
          dataReserva = emprestimo.getDataDevolucaoEsperada().plusDays(1);
        }
      }
    }
    reserva.setDataReserva(dataReserva);
    LocalDate dataVencimento = dataReserva;
    reserva.setDataVencimento(dataVencimento);
    repositorioReserva.inserirReserva(reserva);
  }

  public Reserva buscarReserva(Usuario usuario, Livro livro) throws ReservaNaoEncontradaException {
    List <Reserva> reservas = getAllReservas(livro);
    Reserva reserva = repositorioReserva.buscarReserva(usuario, livro);
    
    if (reserva.getSituacao().equals("Ativa")) {
      return reserva;
    }
    
    for (Reserva verif_reserva : reservas) {
      if (verif_reserva.getUsuario().equals(usuario)) {
        if (verif_reserva.getSituacao().equals("Ativa")) {
          reserva = verif_reserva;
        }
      }
    }
    return reserva;
  }

  public void excluirReserva(Reserva reserva) throws ReservaNaoEncontradaException {
    repositorioReserva.deletarReserva(reserva);
  }

  public void alterarReserva(Reserva reserva1, Reserva reserva2) throws ReservaNaoEncontradaException, BibliotecaException {
    Usuario usuario = reserva2.getUsuario();
    Livro livro = reserva2.getLivro();
    try {
      Emprestimo emprestimo = buscarEmprestimo(usuario, livro);
      if (!emprestimo.getSituacao().equals("Devolvido")) {
        System.err.println("Não foi possível alterar esta reserva.");
        throw new BibliotecaException("Usuário possui empréstimo não devolvido do mesmo livro.");
      }
      else {
        throw new EmprestimoNaoEncontradoException();
      }
    } catch (EmprestimoNaoEncontradoException | LivroJaEmprestadoException ex) {
      reserva1.setUsuario(reserva2.getUsuario());
      reserva1.setLivro(reserva2.getLivro());
    }
    atualizarSituacaoReserva(reserva1);
    repositorioReserva.alterarReserva(reserva1);
  }

  private void atualizarSituacaoReserva(Reserva reserva) {
    String situacaoLivro = reserva.getLivro().getSituacao();
    if (!situacaoLivro.equals("Disponível")) {
      try {
        Emprestimo emprestimo = buscarEmprestimo(reserva.getUsuario(), reserva.getLivro());
        LocalDate dataReserva = emprestimo.getDataDevolucaoEsperada().plusDays(1);
        reserva.setDataReserva(dataReserva);
        LocalDate dataVencimento = dataReserva;
        reserva.setDataVencimento(dataVencimento);
        String situacao = reserva.verificarSituacao();
        reserva.setSituacao(situacao);
      } catch (EmprestimoNaoEncontradoException | LivroJaEmprestadoException ex) {
        System.err.println(ex);
      }
    }
  }
  
  public List<Reserva> getAllReservas(Livro livro) {
    return repositorioReserva.getAll(livro);
  }

  public List<Reserva> getAllReservas() {
    return repositorioReserva.getAll();
  }

  public void exit() {
  }

}