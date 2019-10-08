package posjavamavenhibernate;

import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {
	@Test
	public void testeHibenateUtil() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();

		pessoa.setIdade(45);
		pessoa.setLogin("teste");
		pessoa.setNome("marcelino");
		pessoa.setSenha("123456");
		pessoa.setEmail("marcio@alexmarcio.com");
		;

		daoGeneric.salvar(pessoa);
	}

	@Test
	public void testeBuscar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();

		pessoa.setId(2L);

		pessoa = daoGeneric.pesquisar(pessoa);

		System.out.println(pessoa);
	}

	@Test
	public void testeBuscar2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L, UsuarioPessoa.class);

		System.out.println(pessoa);

	}

	@Test
	public void testeUpdate() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L, UsuarioPessoa.class);

		pessoa.setIdade(999);
		pessoa.setNome("nome atualizado");

		pessoa = daoGeneric.updateMerge(pessoa);

		System.out.println(pessoa);

	}

	@Test
	public void testeDelete() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(10L, UsuarioPessoa.class);

		daoGeneric.deletarPoId(pessoa);

	}

	@Test
	public void testeConsultar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.listar(UsuarioPessoa.class);

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("----------------------");
		}
	}

	@Test
	public void testQueryList() {

		DaoGeneric<UsuarioPessoa> DaoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = DaoGeneric.getEntityManager().createQuery(" from UsuarioPessoa").getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}

	@Test
	public void testQueryListMaxResult() {

		DaoGeneric<UsuarioPessoa> DaoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = DaoGeneric.getEntityManager().createQuery(" from UsuarioPessoa order by id")
				.setMaxResults(15).getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}

	@Test
	public void testQueryListMaxParameter() {

		DaoGeneric<UsuarioPessoa> DaoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = DaoGeneric.getEntityManager().createQuery(" from UsuarioPessoa where nome = :nome")
				.setParameter("nome", "Alex").getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}

	@Test
	public void testQuerySomaIdade() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		Long somaIdade = (Long) daoGeneric.getEntityManager().createQuery("select sum(u.idade) from UsuarioPessoa u ")
				.getSingleResult();

		System.out.println("Soma de todas as idade é -->" + somaIdade);

	}

	@Test
	public void testeNamedQuery1() {

		DaoGeneric<UsuarioPessoa> DaoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = DaoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.todos")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}

	}

	@Test
	public void testeGravaTelefone() {

		DaoGeneric daoGeneric = new DaoGeneric();

		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(13L, UsuarioPessoa.class);

		TelefoneUser telefoneUser = new TelefoneUser();

		telefoneUser.setTipo("casa");
		telefoneUser.setNumero("12345678");
		telefoneUser.setUsuarioPessoa(pessoa);

		daoGeneric.salvar(telefoneUser);

	}
	
	@Test
	public void testeConsultaTelefones() {

		DaoGeneric daoGeneric = new DaoGeneric();

		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(13L, UsuarioPessoa.class);

		for (TelefoneUser fone : pessoa.getTelefoneUser()) {
			System.out.println(fone.getNumero());
			System.out.println(fone.getTipo());
			System.out.println(fone.getUsuarioPessoa().getNome());
			System.out.println("--------------------------");
		}
	}
}
