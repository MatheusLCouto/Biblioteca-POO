package repository.emprestimo;

import repository.RepositoryException;

public class LivroJaEmprestadoException extends RepositoryException {

  public LivroJaEmprestadoException() {
    super("Livro emprestado à outro usuário.");
  }
}