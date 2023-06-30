package repository.reserva;

import repository.RepositoryException;

public class ReservaJaRealizadaException extends RepositoryException {
    public ReservaJaRealizadaException() {
        super("Reserva já realizada.");
    }
}
