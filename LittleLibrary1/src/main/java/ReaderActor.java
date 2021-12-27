import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class ReaderActor extends AbstractActor{
    private String name;
    private ActorRef library;

    public ReaderActor(String name) {
        this.name = name;
    };

    public static Props props(String name) {
        return Props.create(ReaderActor.class, name);
    };

    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .matchEquals(Answer.SUCCESS, action -> {
                    confirmTakeBook();
                })
                .matchEquals(Answer.FAIl, actions -> {
                    askAboutAnotherBook();
                })
                .build();
    };

    public void confirmTakeBook() {
        library = getSender();
        library.tell(Action.CONFIRM_TAKE_BOOK, ActorRef.noSender());
    };

    public void askAboutAnotherBook() {
        library = getSender();
        library.tell(Answer.GIVE_ANOTHER_BOOK, ActorRef.noSender());
    };
}
