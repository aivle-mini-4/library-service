//package aivle.domain.event;
//
//import aivle.domain.model.Book;
//import aivle.infrastructure.messaging.AbstractEvent;
//import lombok.Data;
//import lombok.ToString;
//
////<<< DDD / Domain Event
//@Data
//@ToString
//public class BestsellerRegistered extends AbstractEvent {
//
//    private Long id;
//    private String authorId;
//    private String coverImageUrl;
//    private String title;
//    private String summary;
//    private String category;
//    private Integer price;
//    private String content;
//    private Integer views;
//
//    public BestsellerRegistered(Book aggregate) {
//        super(aggregate);
//    }
//
//    public BestsellerRegistered() {
//        super();
//    }
//}