package WebhotelBackend.repository;

import WebhotelBackend.model.Comment;
import WebhotelBackend.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByHotelId(Long hotelId);
}
