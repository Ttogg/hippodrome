import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;

public class HorseTest {

    @Test
    public void nameNullExceptionAndMessage() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 1, 1)
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t ", "\n", "\r", "\f", "  ", "\t\t", "\n\n\n"})
    public void blankNameExceptionAndMessage(String name) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 1, 1)
        );
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void negativeSpeedExceptionAndMessage() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("name", -1, 1)
        );
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void negativeDistanceExceptionAndMessage() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("name", 1, -1)
        );
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName() {
        String name = "name";
        Horse horse = new Horse(name, 1, 1);
        assertEquals(name, horse.getName());
    }

    @Test
    public void getSpeed() {
        double speed = 1;
        Horse horse = new Horse("name", speed, 1);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void getDistance() {
        double distance = 1;
        Horse horse = new Horse("name", 1, distance);
        assertEquals(distance, horse.getDistance());
    }

    @Test
    public void defaultDistance() {
        Horse horse = new Horse("name", 1);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void move () {
        try (MockedStatic<Horse> mockStatic =  Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("name", 1, 1);
            horse.move();
            mockStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 0.5, 0.8})
    public void newDistance (double randomDouble) {
        try (MockedStatic<Horse> mockStatic =  Mockito.mockStatic(Horse.class)) {
            mockStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);

            Horse horse = new Horse("name", 31, 283);
            double distance = 283 + 31 * randomDouble;
            horse.move();

            assertEquals(distance, horse.getDistance());
        }
    }
}
