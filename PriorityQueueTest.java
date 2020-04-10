import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
//import java.util.Comparator;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PriorityQueueTest {

    @Test
    public void offerException() {
        // First Exception Test: NullPointerException
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Exception exception = assertThrows(NullPointerException.class, ()->{pq.offer(null);});
    }

    @Test
    public void initialCapacityException() {
        // Second Exception Test: IllegalArgumentException
        Exception exception = assertThrows(IllegalArgumentException.class,
                                           ()->{PriorityQueue<Integer> pq = new PriorityQueue<>(-1);});
    }

    @Test
    public void NoElementException() {
        // Third Exception Test: NoSuchElementException
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(9);
        pq.offer(4);
        pq.offer(6);
        pq.offer(1);
        pq.offer(5);
        //System.out.println(pq);
        Iterator i = pq.iterator();
        Exception exception = assertThrows(NoSuchElementException.class,
                                           ()->{while(i.hasNext()) {System.out.println(i.next());
                                                                    System.out.println(i.next());} });
    }

    @ParameterizedTest
    @MethodSource("CorrectArrayAndRandomArrayProvider")
    public void poll(List correctArray, List randomArray, Class<?> c, int order) {
        List pollArray;

        if (Integer.class == c) {
            pollArray = PriorityQueueTest.<Integer>resultArray(randomArray, Integer.class, order);
        }
        else if (String.class == c) {
            pollArray = PriorityQueueTest.<String>resultArray(randomArray, String.class, order);
        }
        else if (Double.class == c) {
            pollArray = PriorityQueueTest.<Double>resultArray(randomArray, Double.class, order);
        }
        else {
            pollArray = new ArrayList();
        }

        assertEquals(correctArray, pollArray);
    }

    public static <T> List<T> resultArray(List<T> randomArray, Class<?> c, int order) {
        List<T> pollArray = new ArrayList<>();

        Comparator comp = null;

        if (order == 1) {
            if (Integer.class == c) {
                comp = new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        return (Integer)o2 - (Integer)o1;
                    }
                };
            }
            else if (String.class == c) {
                comp = new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        return ((String)o1).length() - ((String)o2).length();
                    }
                };
            }
            else {
                comp = null;
            }
        }

        PriorityQueue<T> pq = new PriorityQueue<>(comp);

        for (T t: randomArray) {
            pq.offer(t);
        }

        T cur;
        while((cur = pq.poll()) != null) {
            pollArray.add(cur);
        }

        return pollArray;
    }

    static Stream<Arguments> CorrectArrayAndRandomArrayProvider() {
        return Stream.of(
                arguments(Arrays.asList(15, 88, 123, 1024, 65536, 999999),
                          Arrays.asList(1024, 65536, 123, 15, 999999, 88),
                          Integer.class,
                          0),
                arguments(Arrays.asList("and", "ans", "ant", "back", "can", "cap", "car", "cat"),
                          Arrays.asList("car", "cap", "ant", "cat", "and", "can", "back", "ans"),
                          String.class,
                          0),
                arguments(Arrays.asList("a", "he", "dog", "lake", "zebra", "banana", "forward"),
                          Arrays.asList("zebra", "forward", "lake", "he", "dog", "banana", "a"),
                          String.class,
                          1),
                arguments(Arrays.asList(654321, 654320, 246800, 19998, 2000, 300, 92, 1),
                          Arrays.asList(2000, 300, 654320, 246800, 1, 19998, 92, 654321),
                          Integer.class,
                          1),
                arguments(Arrays.asList(0.53, 1.25, 3.702, 5.97, 10.60),
                          Arrays.asList(1.25, 5.97, 3.702, 10.60, 0.53),
                          Double.class,
                          0)
        );
    }
}
