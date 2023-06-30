package repository.emprestimo;

import model.livro.LivroNaoDisponivelException;

import model.livro.Livro;
import model.usuario.Usuario;
import model.emprestimo.Emprestimo;
import java.util.List;

public interface RepositorioEmprestimo {

  void inserirEmprestimo(Emprestimo emprestimo) throws LivroNaoDisponivelException;

  void alterarEmprestimo(Emprestimo emprestimo) throws EmprestimoNaoEncontradoException, LivroJaEmprestadoException;

  void deletarEmprestimo(Emprestimo emprestimo) throws EmprestimoNaoEncontradoException;

  Emprestimo buscarEmprestimo(Usuario usuario, Livro livro) throws EmprestimoNaoEncontradoException, LivroJaEmprestadoException;

  List<Emprestimo> getAll(Livro livro);

  List<Emprestimo> getAll();

}