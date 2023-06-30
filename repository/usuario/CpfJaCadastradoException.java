package repository.usuario;

import repository.RepositoryException;

public class CpfJaCadastradoException extends RepositoryException {

  public CpfJaCadastradoException() {
    super("CPF já cadastrado.");
  }
}