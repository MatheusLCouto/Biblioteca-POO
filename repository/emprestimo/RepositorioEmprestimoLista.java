package repository.emprestimo;

import model.livro.LivroNaoDisponivelException;

import model.livro.Livro;
import model.usuario.Usuario;
import model.emprestimo.Emprestimo;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEmprestimoLista implements RepositorioEmprestimo {

  List<Emprestimo> emprestimos;

  public RepositorioEmprestimoLista() {
    this.emprestimos = new ArrayList<Emprestimo>();
  }

  @Override
  public void inserirEmprestimo(Emprestimo emprestimo) throws LivroNaoDisponivelException {
    if (emprestimo.getLivro().getSituacao().equals("Disponível")) {
        emprestimos.add(emprestimo);
    } else {
      throw new LivroNaoDisponivelException();
    }
  }

  @Override
  public void alterarEmprestimo(Emprestimo emprestimo) throws EmprestimoNaoEncontradoException, LivroJaEmprestadoException {
    // Em memória, não há necessidade de atualizar objeto
    buscarEmprestimo(emprestimo.getUsuario(), emprestimo.getLivro());
  }

  @Override
  public void deletarEmprestimo(Emprestimo emprestimo) throws EmprestimoNaoEncontradoException {
    if (!emprestimos.remove(emprestimo)) {
      throw new EmprestimoNaoEncontradoException();
    }
  }

  @Override
  public Emprestimo buscarEmprestimo(Usuario usuario, Livro livro) throws LivroJaEmprestadoException, EmprestimoNaoEncontradoException {
    Emprestimo ultimoEmprestimo = null;
    
    for (Emprestimo emprestimo : emprestimos) {
      if (emprestimo.getLivro().equals(livro)) {
        if (emprestimo.getUsuario().equals(usuario)) {
          ultimoEmprestimo = emprestimo;
        } else {
          if (!emprestimo.getSituacao().equals("Devolvido")) {
            throw new LivroJaEmprestadoException();
          }
        }
      }
    }

    if (ultimoEmprestimo != null) {
      return ultimoEmprestimo;
    }
    else {
      throw new EmprestimoNaoEncontradoException();
    }
  }

  @Override
  public List<Emprestimo> getAll(Livro livro) {
    List <Emprestimo> emprestimos_por_livro = new ArrayList<>();
    for (Emprestimo emprestimo : emprestimos) {
      if (emprestimo.getLivro().equals(livro)) {
        emprestimos_por_livro.add(emprestimo);
      }
    }
    return emprestimos_por_livro;
  }

  @Override
  public List<Emprestimo> getAll() {
    return new ArrayList<>(emprestimos);
  }

}