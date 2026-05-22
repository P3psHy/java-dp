package fr.sdv.strategy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.sdv.strategy.Enum.TypeTri;

public class StrategyFactoryTest {

    private StrategyFactory factory;

    @Before
    public void setUp() {
        factory = new StrategyFactory();
    }

    @Test
    public void testBubbleSort() {

        Integer[] array = {12, -5, 7, 0, 8, 4, -3, 9, 15};

        IStrategy strategy = factory.createStrategy(TypeTri.BUBBLE);

        strategy.trier(array);

        Integer[] expected = {-5, -3, 0, 4, 7, 8, 9, 12, 15};

        assertArrayEquals(expected, array);
    }

    @Test
    public void testInsertionSort() {

        Integer[] array = {12, -5, 7, 0, 8, 4, -3, 9, 15};

        IStrategy strategy = factory.createStrategy(TypeTri.INSERTION);

        strategy.trier(array);

        Integer[] expected = {-5, -3, 0, 4, 7, 8, 9, 12, 15};

        assertArrayEquals(expected, array);
    }

    @Test
    public void testSelectionSort() {

        Integer[] array = {12, -5, 7, 0, 8, 4, -3, 9, 15};

        IStrategy strategy = factory.createStrategy(TypeTri.SELECTION);

        strategy.trier(array);

        Integer[] expected = {-5, -3, 0, 4, 7, 8, 9, 12, 15};

        assertArrayEquals(expected, array);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullTypeThrowsException() {

        factory.createStrategy(null);
    }
}