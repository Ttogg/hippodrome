import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class HippodromeTest {

    @Test
    public void NullExceptionAndMessage() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void EmptyExceptionAndMessage() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>())
        );
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("name", i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        new Hippodrome(horses).move();

        for (Horse horse : horses) {
            Mockito.verify(horse, Mockito.only()).move();
        }
    }

    @Test
    public void getWinner() {
        Random random = new Random();
        List<Horse> horses = new ArrayList<>();
        double maxDistance = Double.MIN_VALUE;
        double distance;
        int winnerNumber = 0;
        for (int i = 0; i < random.nextInt(10) + 1; i++) {
            distance = random.nextDouble() * 100;
            if (distance > maxDistance) {
                maxDistance = distance;
                winnerNumber = i;
            }
            horses.add(new Horse("name" + i, 1, distance));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        assertSame(hippodrome.getHorses().get(winnerNumber), hippodrome.getWinner());
    }
}
