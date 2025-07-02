package WebhotelBackend.dto;

public class CommentBreakdownDto {
    private String service;
    private double score;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
