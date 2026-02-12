package gabrielebelluco.u5d4w2.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import gabrielebelluco.u5d4w2.entities.Author;
import gabrielebelluco.u5d4w2.exceptions.BadRequestException;
import gabrielebelluco.u5d4w2.exceptions.NotFoundException;
import gabrielebelluco.u5d4w2.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class AuthorsService {

    private final AuthorsRepository authorsRepository;
    private final Cloudinary cloudinaryUploader;


    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository, Cloudinary cloudinaryUploader) {
        this.authorsRepository = authorsRepository;
        this.cloudinaryUploader = cloudinaryUploader;
    }

    public Author save(Author body) {
        authorsRepository.findByEmail(body.getEmail()).ifPresent(user -> {
            throw new BadRequestException("L'email " + body.getEmail() + " è già stata utilizzata");
        });
        body.setAvatar("https://ui-avatars.com/api/?name=" + body.getName().charAt(0) + "+" + body.getSurname().charAt(0));
        return authorsRepository.save(body);
    }

    public Page<Author> getAuthors(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return authorsRepository.findAll(pageable);
    }

    public Author findById(int id) {
        return authorsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) {
        Author found = this.findById(id);
        authorsRepository.delete(found);
    }

    public Author findByIdAndUpdate(int id, Author body) {

        Author found = this.findById(id);
        found.setEmail(body.getEmail());
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setDateOfBirth(body.getDateOfBirth());
        found.setAvatar(body.getAvatar());
        return authorsRepository.save(found);
    }


    public String uploadAvatar(MultipartFile file) {
        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            String imageUrl = (String) result.get("secure_url");
            return imageUrl;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
