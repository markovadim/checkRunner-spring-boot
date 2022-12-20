package by.markov.checkrunnerspringboot.repositories;

import by.markov.checkrunnerspringboot.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
