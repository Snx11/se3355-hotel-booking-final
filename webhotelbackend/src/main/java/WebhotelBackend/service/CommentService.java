package WebhotelBackend.service;

import WebhotelBackend.model.Comment;
import WebhotelBackend.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsByHotelId(Long hotelId) {
        return commentRepository.findByHotelId(hotelId);
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
