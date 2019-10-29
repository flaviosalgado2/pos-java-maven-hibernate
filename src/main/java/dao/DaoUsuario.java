package dao;

import java.util.List;

import javax.persistence.Query;

import model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGeneric<UsuarioPessoa> {

	private static final long serialVersionUID = -255394275035886489L;

	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {

		getEntityManager().getTransaction().begin();

		getEntityManager().remove(pessoa);

//		String sqlDelteFone = "delete from telefoneuser where usuariopessoa_id = " + pessoa.getId();
//		getEntityManager().createNativeQuery(sqlDelteFone).executeUpdate();
		getEntityManager().getTransaction().commit();

		// super.deletarPorId(pessoa);
	}

	public List<UsuarioPessoa> pesquisar(String campoPesquisa) {
		
		Query query = super.getEntityManager()
				.createQuery("from UsuarioPessoa where nome like '%" + campoPesquisa + "%'");
		return query.getResultList();
			
	}

}
