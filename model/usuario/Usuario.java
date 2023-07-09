package model.usuario;

import java.text.DecimalFormat;

public class Usuario {
  private String id;
  private String nome;
  private String cpf;
  private String telefone;
  private static int prox_num = 1;

  public Usuario(String nome, String cpf, String telefone) {
    this.nome = nome;
    this.cpf = cpf;
    this.telefone = telefone;
    this.id = gerarId();
  }

  private String gerarId() {
    DecimalFormat decimalFormat = new DecimalFormat("00");
    return decimalFormat.format(prox_num++);
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return this.cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getTelefone() {
    return this.telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }
}