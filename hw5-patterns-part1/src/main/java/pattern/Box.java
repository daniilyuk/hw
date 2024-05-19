package pattern;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Box implements Iterable<String> {
    private final List<String> list1=List.of("a","b","c","d");
    private final List<String> list2=List.of("e","f","g","h");
    private final List<String> list3=List.of("i","j","k","l");
    private final List<String> list4=List.of("m","n","o","p");

    @Override
    public Iterator<String> iterator() {
        return new BoxIterator();
    }

    public final class BoxIterator implements Iterator<String> {
        private final List<String> list = Stream.of(list1, list2, list3, list4).flatMap(List::stream).toList();
        private int index=0;

        @Override
        public boolean hasNext() {
            return index<list.size();
        }

        @Override
        public String next() {
            return list.get(index++);
        }
    }
}
