package repository.reserva;

import repository.RepositoryException;

public class ReservaNaoEncontradaException extends RepositoryException {

  public ReservaNaoEncontradaException() {
    super("Reserva não realizada.");
  }
}