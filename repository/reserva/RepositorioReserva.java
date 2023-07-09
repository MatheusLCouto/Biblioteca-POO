package repository.reserva;

import model.livro.Livro;
import model.usuario.Usuario;
import model.reserva.Reserva;
import java.util.List;

public interface RepositorioReserva {

  void inserirReserva(Reserva reserva);

  void alterarReserva(Reserva reserva) throws ReservaNaoEncontradaException;

  void deletarReserva(Reserva reserva) throws ReservaNaoEncontradaException;

  Reserva buscarReserva(Usuario usuario, Livro livro) throws ReservaNaoEncontradaException;

  List<Reserva> getAll(Usuario usuario);

  List<Reserva> getAll(Livro livro);

  List<Reserva> getAll();

}