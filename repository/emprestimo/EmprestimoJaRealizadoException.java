package repository.emprestimo;

import repository.RepositoryException;

public class EmprestimoJaRealizadoException extends RepositoryException {
    public EmprestimoJaRealizadoException() {
        super("Emprestimo já realizado.");
    }
}
