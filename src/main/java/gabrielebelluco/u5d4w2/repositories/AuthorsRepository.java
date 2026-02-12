package gabrielebelluco.u5d4w2.repositories;

import gabrielebelluco.u5d4w2.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorsRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByEmail(String email);
}
