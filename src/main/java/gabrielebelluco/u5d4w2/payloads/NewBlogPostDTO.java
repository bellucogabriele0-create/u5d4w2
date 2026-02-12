package gabrielebelluco.u5d4w2.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewBlogPostDTO {
    @NotBlank(message = "l'id è obbligatorio")
    private int authorId;
    @Size(min = 2, max = 30, message = "la categoria deve essere tra i 2 e i 30 caratteri")
    @NotBlank(message = "la categoria è obbligatorio")
    private String category;
    @NotBlank(message = "il content è obbligatorio")
    @Size(min = 5, max = 300, message = "Il content deve essere tra i 5 e i 300 caratteri")
    private String content;
    @NotBlank(message = "il tempo di lettura è obbligatorio")
    private double readingTime;
    @NotBlank(message = "il titolo è obbligatorio")
    @Size(min = 2, message = "il titolo deve essere minimo di 2 caratteri")
    private String title;

    public NewBlogPostDTO(int authorId, String category, String title, String content, double readingTime) {
        this.authorId = authorId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.readingTime = readingTime;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public double getReadingTime() {
        return readingTime;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }
}

