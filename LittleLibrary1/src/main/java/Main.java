import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Book> books = new ArrayList<Book>();

        books.add(new Book("The Lord of the Rings"));
        books.add(new Book("Pride and Prejudice"));
        books.add(new Book("Harry Potter and the Goblet of Fire"));
        books.add(new Book("Nineteen Eighty-Four"));
        books.add(new Book("The Hobbit, or There and Back Again"));

        ActorSystem akkaSystem = ActorSystem.create("library");
        ActorRef library = akkaSystem.actorOf(LibraryActor.props(books));

        ActorRef visitor = akkaSystem.actorOf(ReaderActor.props("Sofiia"));

        library.tell(Action.TAKE_BOOK_AT_HOME, visitor);
        library.tell(Action.TAKE_BOOK_IN_READING_ROOM, visitor);

    }
}
