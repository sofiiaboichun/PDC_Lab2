public class Book {
    private String title;

    public Book(String title) {
        this.title = title;
    };

    public String getTitle() {
        return title;
    }

    public static int getRandomNum(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    };
}
