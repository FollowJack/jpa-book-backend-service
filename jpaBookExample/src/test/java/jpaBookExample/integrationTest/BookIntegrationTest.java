package jpaBookExample.integrationTest;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolationException;

import jpaBookExample.domain.entities.Book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookIntegrationTest {

	private static EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("chapter04TestPU");
	private EntityManager entityManager;
	private EntityTransaction transaction;

	@Before
	public void initEntityManager() throws Exception {
		entityManager = entityManagerFactory.createEntityManager();
		transaction = entityManager.getTransaction();
	}

	@After
	public void closeEntityManager() throws Exception {
		if (entityManager != null)
			entityManager.close();
	}

	@Test
	public void shouldFindJavaEE7Book() throws Exception {
		Book book = entityManager.find(Book.class, 1001L);
		assertEquals("Beginning Java EE 7", book.getTitle());
	}

	@Test
	public void shouldCreateH2G2Book() throws Exception {
		// Creates an instance of book
		Book book = new Book("H2G2", "The Hitchhiker's Guide to the Galaxy",
				12.5F, "1-84023-742-2", 354, false);
		// Persists the book to the database
		transaction.begin();
		entityManager.persist(book);
		transaction.commit();
		assertNotNull("ID should not be null", book.getId());
		// Retrieves all the books from the database
		book = entityManager.createNamedQuery("findBookH2G2", Book.class)
				.getSingleResult();
		assertEquals("The Hitchhiker's Guide to the Galaxy",
				book.getDescription());
	}

	@Test(expected = ConstraintViolationException.class)
	public void shouldRaiseConstraintViolationCauseNullTitle() {
		Book book = new Book(null, "Null title, should fail", 12.5F,
				"1-84023-742-2", 354, false);
		entityManager.persist(book);
	}
}
