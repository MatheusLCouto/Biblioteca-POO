package repository.usuario;

import repository.RepositoryException;

public class UsuarioJaCadastradoException extends RepositoryException {

  public UsuarioJaCadastradoException() {
    super("Usuário já cadastrado.");
  }
}