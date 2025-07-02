package WebhotelBackend.controller;

import WebhotelBackend.model.Comment;
import WebhotelBackend.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/hotel/{hotelId}")
    public List<Comment> getCommentsByHotelId(@PathVariable Long hotelId) {
        return commentService.getCommentsByHotelId(hotelId);
    }

    @PostMapping
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }
}
