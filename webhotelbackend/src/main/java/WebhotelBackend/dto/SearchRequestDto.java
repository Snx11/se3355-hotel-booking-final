package WebhotelBackend.dto;

import java.time.LocalDate;

public class SearchRequestDto {
    private String city;
    private LocalDate from;
    private LocalDate to;
    private int guestCount;

    // Boş constructor (JSON -> POJO dönüşümü için)
    public SearchRequestDto() {}

    // Getter / Setter
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getFrom() {
        return from;
    }
    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }
    public void setTo(LocalDate to) {
        this.to = to;
    }

    public int getGuestCount() {
        return guestCount;
    }
    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }
}
