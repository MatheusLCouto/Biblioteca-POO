package repository.livro;

import model.livro.Livro;
import java.util.List;

public interface RepositorioLivro {

  void inserirLivro(Livro livro) throws LivroJaCadastradoException;

  void alterarLivro(Livro livro) throws LivroNaoCadastradoException;

  void deletarLivro(Livro livro) throws LivroNaoCadastradoException;

  Livro buscarLivro(String isbn) throws LivroNaoCadastradoException;

  List<Livro> getAll();

}