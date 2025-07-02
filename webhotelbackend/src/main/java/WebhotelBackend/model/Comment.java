package WebhotelBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorName;
    private String content;
    private double rating;
    private LocalDate date;

    @Column
    private String service;

    @Column
    private Double score;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    private Hotel hotel;

    public Comment() {}

    public Comment(String authorName, String content, double rating, LocalDate date, Hotel hotel,String service,Double score) {
        this.authorName = authorName;
        this.content = content;
        this.rating = rating;
        this.date = date;
        this.hotel = hotel;
        this.service= service;
        this.score=score;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
