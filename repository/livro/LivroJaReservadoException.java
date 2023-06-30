package repository.livro;

import repository.RepositoryException;

public class LivroJaReservadoException extends RepositoryException {

  public LivroJaReservadoException() {
    super("Livro já reservado.");
  }
}