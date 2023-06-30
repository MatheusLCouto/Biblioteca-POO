package repository.usuario;

import model.usuario.Usuario;
import java.util.List;

public interface RepositorioUsuario {

  void inserirUsuario(Usuario usuario) throws UsuarioJaCadastradoException, CpfJaCadastradoException;

  void alterarUsuario(Usuario usuario) throws UsuarioNaoCadastradoException;

  void deletarUsuario(Usuario usuario) throws UsuarioNaoCadastradoException;

  Usuario buscarUsuario(String id) throws UsuarioNaoCadastradoException;

  List<Usuario> getAll();

}