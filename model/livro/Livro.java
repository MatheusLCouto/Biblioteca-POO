package model.livro;

public class Livro {
  private String titulo;
  private String autor;
  private String isbn; // International Standard Book Number
  private String editora;
  private int anoPublicacao;
  private String situacao; //Verificar se livro está disponivel

  public Livro(String titulo, String autor, String isbn, String editora, int anoPublicacao) {
    this.titulo = titulo;
    this.autor = autor;
    this.isbn = isbn;
    this.editora = editora;
    this.anoPublicacao = anoPublicacao;
    this.situacao = "Disponível";
  }

  public String verificarSituacao(String situacao) throws LivroNaoDisponivelException {
    try {
      if (situacao.equals("Disponível")) {
        return situacao;
      } else {
        throw new LivroNaoDisponivelException();
      }
    } catch (LivroNaoDisponivelException ex) {
    return "Indisponível";
    }
  }

  public String getTitulo() {
    return this.titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getAutor() {
    return this.autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public String getIsbn() {
    return this.isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getEditora() {
    return this.editora;
  }

  public void setEditora(String editora) {
    this.editora = editora;
  }

  public int getAnoPublicacao() {
    return this.anoPublicacao;
  }

  public void setAnoPublicacao(int anoPublicacao) {
    this.anoPublicacao = anoPublicacao;
  }

  public String getSituacao() {
    return this.situacao;
  }

  public void setSituacao(String situacao) {
    this.situacao = situacao;
  }
}