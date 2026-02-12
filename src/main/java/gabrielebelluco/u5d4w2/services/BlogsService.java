package gabrielebelluco.u5d4w2.services;

import gabrielebelluco.u5d4w2.entities.Author;
import gabrielebelluco.u5d4w2.entities.Blogpost;
import gabrielebelluco.u5d4w2.exceptions.NotFoundException;
import gabrielebelluco.u5d4w2.payloads.NewBlogPostDTO;
import gabrielebelluco.u5d4w2.repositories.BlogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogsService {
    private final BlogsRepository blogsRepository;
    private final AuthorsService authorsService;

    @Autowired
    public BlogsService(BlogsRepository blogsRepository, AuthorsService authorsService) {
        this.blogsRepository = blogsRepository;
        this.authorsService = authorsService;
    }

    public Blogpost save(NewBlogPostDTO body) {
        Author author = authorsService.findById(body.getAuthorId());
        Blogpost newBlogPost = new Blogpost();
        newBlogPost.setReadingTime(body.getReadingTime());
        newBlogPost.setContent(body.getContent());
        newBlogPost.setTitle(body.getTitle());
        newBlogPost.setAuthor(author);
        newBlogPost.setCategory(body.getCategory());
        newBlogPost.setCover("http://picsum.photos/200/300");
        return blogsRepository.save(newBlogPost);
    }

    public List<Blogpost> getBlogs() {
        return blogsRepository.findAll();
    }

    public Blogpost findById(int id) {
        return blogsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) {
        Blogpost found = this.findById(id);
        blogsRepository.delete(found);
    }

    public Blogpost findByIdAndUpdate(int id, NewBlogPostDTO body) {
        Blogpost found = this.findById(id);

        found.setReadingTime(body.getReadingTime());
        found.setContent(body.getContent());
        found.setTitle(body.getTitle());
        found.setCategory(body.getCategory());

        if (found.getAuthor().getId() != body.getAuthorId()) {
            Author newAuthor = authorsService.findById(body.getAuthorId());
            found.setAuthor(newAuthor);
        }

        return blogsRepository.save(found);
    }

    public List<Blogpost> findByAuthor(int authorId) {
        Author author = authorsService.findById(authorId);
        return blogsRepository.findByAuthor(author);


    }
}
