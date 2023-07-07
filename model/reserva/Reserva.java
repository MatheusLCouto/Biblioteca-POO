package model.reserva;

import model.livro.Livro;
import model.usuario.Usuario;
import java.time.LocalDate;
import java.text.DecimalFormat;

public class Reserva {
  private String id;
  private Livro livro;
  private Usuario usuario;
  private LocalDate dataReserva;
  private LocalDate dataVencimento;
  private String situacao;
  private static int prox_num = 1;

  public Reserva(Usuario usuario, Livro livro) {
    this.id = gerarId();
    this.usuario = usuario;
    this.livro = livro;
    this.dataReserva = null;
    this.dataVencimento = null;
    this.situacao = verificarSituacao();
  }

  private String gerarId() {
    DecimalFormat decimalFormat = new DecimalFormat("00");
    return "R" + decimalFormat.format(prox_num++);
  }
  
  // Se ultrapassou o dia da solicitação da reserva
  private boolean estaVencida() {
    LocalDate hoje = LocalDate.now();
    if (dataVencimento != null) {
      return hoje.isAfter(dataVencimento);
    }
    return false;
  }

  public String verificarSituacao() {
    if (estaVencida()) {
      return "Expirada";
    } else {
      return "Ativa";
    }
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
  public Usuario getUsuario() {
    return this.usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Livro getLivro() {
    return this.livro;
  }

  public void setLivro(Livro livro) {
    this.livro = livro;
  }

  public LocalDate getDataReserva() {
    return this.dataReserva;
  }

  public void setDataReserva(LocalDate dataReserva) {
    this.dataReserva = dataReserva;
  }

  public LocalDate getDataVencimento() {
    return this.dataReserva;
  }

  public void setDataVencimento(LocalDate dataVencimento) {
    this.dataVencimento = dataVencimento;
  }

  public String getSituacao() {
    return this.situacao;
  }

  public void setSituacao(String situacao) {
    this.situacao = situacao;
  }
}