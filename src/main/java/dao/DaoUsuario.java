package dao;

import model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGeneric<UsuarioPessoa> {

	private static final long serialVersionUID = -255394275035886489L;

	public void removerUsuario(UsuarioPessoa pessoa) throws Exception{
		
		getEntityManager().getTransaction().begin();
		
		String sqlDelteFone = "delete from telefoneuser where usuariopessoa_id = " + pessoa.getId();
		getEntityManager().createNativeQuery(sqlDelteFone).executeUpdate();
		getEntityManager().getTransaction().commit();
		
		super.deletarPorId(pessoa);
	}
	
}
