package repository.reserva;

import model.livro.Livro;
import model.usuario.Usuario;
import model.reserva.Reserva;
import java.util.ArrayList;
import java.util.List;

public class RepositorioReservaLista implements RepositorioReserva {

  List<Reserva> reservas;

  public RepositorioReservaLista() {
    this.reservas = new ArrayList<Reserva>();
  }

  @Override
  public void inserirReserva(Reserva reserva) {
    reservas.add(reserva);
  }     

  @Override
  public void alterarReserva(Reserva reserva) throws ReservaNaoEncontradaException {
    // Em memória, não há necessidade de atualizar objeto
    buscarReserva(reserva.getUsuario(), reserva.getLivro());
  }

  @Override
  public void deletarReserva(Reserva reserva) throws ReservaNaoEncontradaException {
    if (!reservas.remove(reserva)) {
      throw new ReservaNaoEncontradaException();
    }
  }

  @Override
  public Reserva buscarReserva(Usuario usuario, Livro livro) throws ReservaNaoEncontradaException {
    Reserva ultimaReserva = null;
    
    for (Reserva reserva : reservas) {
      if (reserva.getUsuario().equals(usuario) && reserva.getLivro().equals(livro)) {
        ultimaReserva = reserva;
      }
    }

    if (ultimaReserva != null) {
      return ultimaReserva;
    }
    else {
      throw new ReservaNaoEncontradaException();    
    }
  }

  @Override
  public List<Reserva> getAll(Livro livro) {
    List <Reserva> reservas_por_livro = new ArrayList<>();
    for (Reserva reserva : reservas) {
      if (reserva.getLivro().equals(livro)) {
        reservas_por_livro.add(reserva);
      }
    }
    return reservas_por_livro;
  }

  @Override
  public List<Reserva> getAll() {
    return new ArrayList<>(reservas);
  }

}