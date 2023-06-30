package repository.emprestimo;

import repository.RepositoryException;

public class EmprestimoNaoEncontradoException extends RepositoryException {

  public EmprestimoNaoEncontradoException() {
    super("Emprestimo não realizado.");
  }
}