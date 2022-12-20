package by.markov.checkrunnerspringboot.repositories;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {

    Optional<DiscountCard> findByNumber(long number);
}
