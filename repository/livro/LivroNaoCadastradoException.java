package repository.livro;

import repository.RepositoryException;

public class LivroNaoCadastradoException extends RepositoryException {

  public LivroNaoCadastradoException() {
    super("Livro não cadastrado.");
  }
}