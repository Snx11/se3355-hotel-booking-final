package WebhotelBackend.repository;

import WebhotelBackend.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("""
      SELECT h FROM Hotel h
       WHERE lower(h.city) = lower(:city)
         AND h.capacity >= :guests
         AND h.availableFrom <= :from
         AND h.availableTo   >= :to
    """)
    List<Hotel> searchByCityAndDatesAndCapacity(
            @Param("city") String city,
            @Param("from") LocalDate from,
            @Param("to")   LocalDate to,
            @Param("guests") int guests
    );
    List<Hotel> findByCityIgnoreCaseAndAvailableWeekendTrueAndCapacityGreaterThanEqualOrderByRatingDesc(
            String city, int guests
    );
}
