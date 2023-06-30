package model.livro;

import repository.RepositoryException;

public class LivroNaoDisponivelException extends RepositoryException {

  public LivroNaoDisponivelException() {
    super("Livro indisponível.");
  }
}