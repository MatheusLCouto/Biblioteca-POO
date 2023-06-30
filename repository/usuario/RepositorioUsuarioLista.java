package repository.usuario;

import model.usuario.Usuario;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarioLista implements RepositorioUsuario {

  List<Usuario> usuarios;

  public RepositorioUsuarioLista() {
    usuarios = new ArrayList<>();
  }

  @Override
  public void inserirUsuario(Usuario usuario) throws UsuarioJaCadastradoException, CpfJaCadastradoException {
    try {
      buscarUsuario(usuario.getId());
      throw new UsuarioJaCadastradoException();
    } catch (UsuarioNaoCadastradoException ex) {
      for (Usuario verificarCpfUsuario : usuarios) {
        if (verificarCpfUsuario.getCpf().equals(usuario.getCpf())) {
          throw new CpfJaCadastradoException();
        }
      }
      usuarios.add(usuario);
    }
  }

  @Override
  public void alterarUsuario(Usuario usuario) throws UsuarioNaoCadastradoException {
    // Em memória, não há necessidade de atualizar objeto
    buscarUsuario(usuario.getId());
  }

  @Override
  public void deletarUsuario(Usuario usuario) throws UsuarioNaoCadastradoException {
    if (!usuarios.remove(usuario)) {
      throw new UsuarioNaoCadastradoException();
    }
  }

  @Override
  public Usuario buscarUsuario(String id) throws UsuarioNaoCadastradoException {

    for (Usuario usuario : usuarios) {
      if (usuario.getId().equals(id)) {
        return usuario;
      }
    }
    throw new UsuarioNaoCadastradoException();
  }

  @Override
  public List<Usuario> getAll() {
    return new ArrayList<>(usuarios);
  }

}