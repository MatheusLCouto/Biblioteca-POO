package repository.usuario;

import repository.RepositoryException;

public class UsuarioNaoCadastradoException extends RepositoryException {

  public UsuarioNaoCadastradoException() {
    super("Usuário não cadastrado.");
  }
}