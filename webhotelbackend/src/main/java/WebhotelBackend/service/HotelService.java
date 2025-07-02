package WebhotelBackend.service;

import WebhotelBackend.dto.CommentBreakdownDto;
import WebhotelBackend.dto.HotelDetailDto;
import WebhotelBackend.dto.SearchRequestDto;
import WebhotelBackend.model.Comment;
import WebhotelBackend.model.Hotel;
import WebhotelBackend.repository.CommentRepository;
import WebhotelBackend.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Long id, Hotel updatedHotel) {
        return hotelRepository.findById(id).map(hotel -> {
            hotel.setName(updatedHotel.getName());
            hotel.setCity(updatedHotel.getCity());
            hotel.setCountry(updatedHotel.getCountry());
            hotel.setRating(updatedHotel.getRating());
            hotel.setCapacity(updatedHotel.getCapacity());
            hotel.setLatitude(updatedHotel.getLatitude());
            hotel.setLongitude(updatedHotel.getLongitude());
            hotel.setAvailableWeekend(updatedHotel.isAvailableWeekend());
            hotel.setAvailableFrom(updatedHotel.getAvailableFrom());
            hotel.setAvailableTo(updatedHotel.getAvailableTo());
            hotel.setBasePrice(updatedHotel.getBasePrice());
            hotel.setDiscountPercentage(updatedHotel.getDiscountPercentage());
            hotel.setMapUrl(updatedHotel.getMapUrl());
            hotel.setImageUrl(updatedHotel.getImageUrl());
            hotel.setDescription(updatedHotel.getDescription());
            hotel.setAmenities(updatedHotel.getAmenities());
            return hotelRepository.save(hotel);
        }).orElse(null);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    public List<Hotel> search(SearchRequestDto dto) {
        List<Hotel> allHotels = hotelRepository.findAll();

        return allHotels.stream()
                .filter(hotel -> {
                    boolean matches = true;

                    if (dto.getCity() != null && !dto.getCity().isBlank()) {
                        matches &= hotel.getCity().equalsIgnoreCase(dto.getCity());
                    }

                    if (dto.getFrom() != null && dto.getTo() != null) {
                        matches &= (hotel.getAvailableFrom() != null && hotel.getAvailableTo() != null) &&
                                (!dto.getFrom().isBefore(hotel.getAvailableFrom()) &&
                                        !dto.getTo().isAfter(hotel.getAvailableTo()));
                    }

                    if (dto.getGuestCount() > 0) {
                        matches &= hotel.getCapacity() >= dto.getGuestCount();
                    }

                    return matches;
                })
                .toList();
    }

    public HotelDetailDto getHotelDetails(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        List<Comment> comments = commentRepository.findByHotelId(hotelId);

        HotelDetailDto dto = new HotelDetailDto();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setCity(hotel.getCity());
        dto.setCountry(hotel.getCountry());
        dto.setRating(hotel.getRating());
        dto.setCapacity(hotel.getCapacity());
        dto.setLatitude(hotel.getLatitude());
        dto.setLongitude(hotel.getLongitude());
        dto.setAvailableWeekend(hotel.isAvailableWeekend());
        dto.setBasePrice(hotel.getBasePrice());
        dto.setDiscountPercentage(hotel.getDiscountPercentage());
        dto.setAvailableFrom(hotel.getAvailableFrom());
        dto.setAvailableTo(hotel.getAvailableTo());
        dto.setMapUrl(hotel.getMapUrl());
        dto.setImageUrl(hotel.getImageUrl());
        dto.setDescription(hotel.getDescription());
        dto.setAmenities(hotel.getAmenities());
        dto.setCommentCount(comments.size());

        List<CommentBreakdownDto> breakdown = comments.stream().map(comment -> {
            CommentBreakdownDto b = new CommentBreakdownDto();
            b.setService(comment.getService());
            b.setScore(comment.getScore());
            return b;
        }).collect(Collectors.toList());

        dto.setComments(breakdown);

        return dto;
    }
}
