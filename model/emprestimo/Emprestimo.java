package model.emprestimo;

import model.livro.Livro;
import model.usuario.Usuario;
import java.time.LocalDate;
import java.text.DecimalFormat;

public class Emprestimo {
  private String id;
  private Livro livro;
  private Usuario usuario;
  private LocalDate dataEmprestimo;
  private LocalDate dataDevolucaoEsperada;
  private LocalDate dataDevolucaoReal;
  private int diasMaxEmprestimo;
  private String situacao;
  private static int prox_num = 1;

  public Emprestimo(Usuario usuario, Livro livro, LocalDate dataEmprestimo) {
    this.id = gerarId();
    this.usuario = usuario;
    this.livro = livro;
    this.dataEmprestimo = dataEmprestimo;
    this.diasMaxEmprestimo = 7;
    this.dataDevolucaoEsperada = dataEmprestimo.plusDays(diasMaxEmprestimo);
    this.dataDevolucaoReal = null;
    this.situacao = verificarSituacao();
  }

  private String gerarId() {
    DecimalFormat decimalFormat = new DecimalFormat("00");
    return "E" + decimalFormat.format(prox_num++);
  }

  // Se o livro não for devolvido até a data de devolucao esperada (7 dias depois do emprestimo).
  private boolean estaAtrasado() {
    LocalDate hoje = LocalDate.now();
    return hoje.isAfter(dataDevolucaoEsperada) && dataDevolucaoReal == null;
  }
  
  public String verificarSituacao() {
    if (dataDevolucaoReal != null) {
      return "Devolvido";
    } else {
      if (estaAtrasado()) {
        return "Atrasado!";
      } else {
        return "Ativo";
      }
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

  public LocalDate getDataEmprestimo() {
    return this.dataEmprestimo;
  }

  public void setDataEmprestimo(LocalDate dataEmprestimo) {
    this.dataEmprestimo = dataEmprestimo;
  }

  public LocalDate getDataDevolucaoEsperada() {
    return this.dataDevolucaoEsperada;
  }

  public void setDataDevolucaoEsperada(LocalDate dataDevolucaoEsperada) {
    this.dataDevolucaoEsperada = dataDevolucaoEsperada;
  }

  public LocalDate getDataDevolucaoReal() {
    return this.dataDevolucaoReal;
  }

  public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
    this.dataDevolucaoReal = dataDevolucaoReal;
  }

  public int getDiasMaxEmprestimo() {
    return this.diasMaxEmprestimo;
  }

  public void setDiasMaxEmprestimo(int diasMaxEmprestimo) {
    this.diasMaxEmprestimo = diasMaxEmprestimo;
  }

  public String getSituacao() {
    return this.situacao;
  }

  public void setSituacao(String situacao) {
    this.situacao = situacao;
  }
}