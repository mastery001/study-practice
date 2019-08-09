package guava;

import com.google.common.base.MoreObjects;
import org.junit.Test;

/**
 * Created by zouziwen on 2017/4/6.
 */
public class ObjectsIntro {

    private class Book {
        private String title;
        private String publisher;
        private String isbn;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).omitNullValues()
                    .add("title" , title)
                    .add("publisher" , publisher)
                    .add("isbn" , isbn).toString();
        }
    }

    @Test
    public void testObjectsToString() {
        Book book = new Book();
        book.title = "人民的名义";
        book.publisher = "人民出版社";
        book.isbn = "1";
        System.out.println(book);
    }

    @Test
    public void testNegate() {
        System.out.println(~2);
        System.out.println(~4);
        System.out.println(1 & (~4) & (~2));
    }
}
