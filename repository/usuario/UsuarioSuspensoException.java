package repository.usuario;

import repository.RepositoryException;

public class UsuarioSuspensoException extends RepositoryException {

  public UsuarioSuspensoException() {
    super("Usuário suspenso. Não é possível realizar empréstimos ou reservas no momento.");
  }
}