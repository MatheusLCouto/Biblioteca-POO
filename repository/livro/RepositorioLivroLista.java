package repository.livro;

import model.livro.Livro;
import java.util.ArrayList;
import java.util.List;

public class RepositorioLivroLista implements RepositorioLivro {

  List<Livro> livros;

  public RepositorioLivroLista() {
    this.livros = new ArrayList<Livro>();
  }

  @Override
  public void inserirLivro(Livro livro) throws LivroJaCadastradoException {
    try {
      buscarLivro(livro.getIsbn());
      throw new LivroJaCadastradoException();
    } catch (LivroNaoCadastradoException ex) {
      livros.add(livro);
    }
  }

  @Override
  public void alterarLivro(Livro livro) throws LivroNaoCadastradoException {
    // Em memória, não há necessidade de atualizar objeto
    buscarLivro(livro.getIsbn());
  }

  @Override
  public void deletarLivro(Livro livro) throws LivroNaoCadastradoException {
    if (!livros.remove(livro)) {
      throw new LivroNaoCadastradoException();
    }
  }

  @Override
  public Livro buscarLivro(String isbn) throws LivroNaoCadastradoException {

    for (Livro livro : livros) {
      if (livro.getIsbn().equals(isbn)) {
        return livro;
      }
    }
    throw new LivroNaoCadastradoException();
  }

  @Override
  public List<Livro> getAll() {
    return new ArrayList<>(livros);
  }

}