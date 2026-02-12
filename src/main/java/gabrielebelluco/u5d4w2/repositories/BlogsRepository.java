package gabrielebelluco.u5d4w2.repositories;


import gabrielebelluco.u5d4w2.entities.Author;
import gabrielebelluco.u5d4w2.entities.Blogpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogsRepository extends JpaRepository<Blogpost, Integer> {
    List<Blogpost> findByAuthor(Author author);
}
