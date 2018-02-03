package ajax;

public class ReviewA {
    private int id_course;
    private int id_user;
    private int id_assessement;
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId_assessement() {
        return id_assessement;
    }

    public void setId_assessement(int id_assessement) {
        this.id_assessement = id_assessement;
    }

    private String review;

    public ReviewA(int id_course, int id_user, String review) {
        this.id_course = id_course;
        this.id_user = id_user;
        this.review = review;
    }

    public ReviewA() {
    }

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
