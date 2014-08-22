package jpaBookExample.ui.consoleClient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpaBookExample.domain.entities.Book;

public class Main {

	public static void main(String[] args) {
		// Creates an instance of book
		 Book book = new Book("H2G2", "The Hitchhiker's Guide to the Galaxy", 12.5F, 
		 "1-84023-742-2", 354, false);
		 // Obtains an entity manager and a transaction
		 EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("chapter04PU");
		 EntityManager entityManager = entityManagerFactory.createEntityManager();
		 // Persists the book to the database
		 EntityTransaction transaction = entityManager.getTransaction();
		 transaction.begin();
		 entityManager.persist(book);
		 transaction.commit();
		 // Closes the entity manager and the factory
		 entityManager.close();
		 entityManagerFactory.close();
	}

}
