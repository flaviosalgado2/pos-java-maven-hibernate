package posjavamavenhibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

	public static EntityManagerFactory factory = null;

	static {
		init();
	}

	private static void init() {
		try {
			if (factory == null) {
				factory = Persistence.createEntityManagerFactory("pos-java-maven");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static EntityManager getEntityManager() {

		return factory.createEntityManager();// provem a parte de persistencia

	}

	public static Object getPrimaryKey(Object entity) { // retorna a primay key

		return factory.getPersistenceUnitUtil().getIdentifier(entity);

	}
}
