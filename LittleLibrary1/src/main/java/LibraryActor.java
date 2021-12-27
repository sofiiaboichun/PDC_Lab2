import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;

public class LibraryActor extends AbstractActor{
    private Book[] books;
    private ActorRef libraryReader;

    public LibraryActor(ArrayList<Book> books) {
        this.books = new Book[books.size()];
        this.books = books.toArray(this.books);
    };

    public static Props props(ArrayList books) {
        return Props.create(LibraryActor.class, books);
    };

    public Receive createReceive() {
        return ReceiveBuilder.create()
                .matchEquals(Action.TAKE_BOOK_AT_HOME, action -> {
                    giveBookHome();
                })
                .matchEquals(Action.TAKE_BOOK_IN_READING_ROOM, actions -> {
                    giveBookReadingRoom();
                })
                .matchEquals(Answer.GIVE_ANOTHER_BOOK, actions -> {
                    provideAnotherBook();
                })
                .matchEquals(Action.CONFIRM_TAKE_BOOK, actions -> {
                    System.out.println("GOOD");
                })
                .build();
    };

    public void giveBookHome() {
        int bookNum = Book.getRandomNum(1, 5);

        libraryReader = getSender();

        switch (bookNum) {
            case  (1):
                System.out.println("Take home");
                libraryReader.tell(Answer.SUCCESS, getSelf());
                break;
            case (2):
                System.out.println("Can't take home");
                libraryReader.tell(Answer.FAIl, getSelf());
                break;
            default:
                System.out.println("Only in the reading room");
                libraryReader.tell(Answer.FAIl, getSelf());
                break;
        }
    };

    public void giveBookReadingRoom() {
        int bookNum = Book.getRandomNum(1, 5);

        libraryReader = getSender();

        switch (bookNum) {
            case (1):
                System.out.println("Can't take book");
                libraryReader.tell(Answer.FAIl, getSelf());
                break;
            default:
                System.out.println("Take book in reading room");
                libraryReader.tell(Answer.SUCCESS, getSelf());
                break;
        }
    };

    public void provideAnotherBook() {
        int bookNum = Book.getRandomNum(1, 5);
        Book book = this.books[bookNum];
        System.out.println("Take another book: " + book.getTitle());
    };
};