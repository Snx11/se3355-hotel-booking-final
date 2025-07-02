package WebhotelBackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import WebhotelBackend.model.Comment; // ✅ DOĞRU OLAN BU

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String country;

    private double rating;
    private int capacity;

    private double latitude;
    private double longitude;

    private boolean availableWeekend;

    private double basePrice;
    private double discountPercentage;

    private LocalDate availableFrom;
    private LocalDate availableTo;

    private String mapUrl;
    private String imageUrl;

    @Column(length = 1000)
    private String description;

    private int commentCount;

    @ElementCollection
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private List<String> amenities = new ArrayList<>();
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    // --- Constructors ---

    public Hotel() {
    }

    public Hotel(Long id, String name, String city, String country, String mapUrl, String imageUrl,
                 double rating, int capacity, double latitude, double longitude, boolean availableWeekend,
                 double basePrice, double discountPercentage, LocalDate availableFrom, LocalDate availableTo,
                 String description, int commentCount, List<String> amenities) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
        this.mapUrl = mapUrl;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.availableWeekend = availableWeekend;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.description = description;
        this.commentCount = commentCount;
        this.amenities = amenities;
    }

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isAvailableWeekend() {
        return availableWeekend;
    }

    public void setAvailableWeekend(boolean availableWeekend) {
        this.availableWeekend = availableWeekend;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalDate getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(LocalDate availableTo) {
        this.availableTo = availableTo;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
