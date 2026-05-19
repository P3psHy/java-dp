package org.sebsy.grasps;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import org.sebsy.factory.beans.Element;
import org.sebsy.factory.beans.Ingredient;
import org.sebsy.factory.beans.Additif;
import org.sebsy.factory.beans.Allergene;
import org.sebsy.factory.beans.Enum.UNITE;
import org.sebsy.factory.beans.Enum.TYPE_ELEMENT;
import org.sebsy.factory.ElementFactory;


public class FactoryTest {

    private ElementFactory factory;

    @Before
    public void setUp() {
        factory = new ElementFactory();
    }

    @Test
    public void testCreateIngredient() {
        System.out.println("Testing createIngredient...");
        Element e = factory.createElement(
                TYPE_ELEMENT.INGREDIENT,
                "Sucre",
                10.0,
                UNITE.MILLI_GRAMMES
        );

        assertNotNull(e);
        assertTrue(e instanceof Ingredient);
        assertEquals("Sucre", e.getNom());
        assertEquals(10.0, e.getValeur(), 0.0001);
        assertEquals(UNITE.MILLI_GRAMMES, e.getUnite());
    }

    @Test
    public void testCreateAdditif() {

        Element e = factory.createElement(
                TYPE_ELEMENT.ADDITIF,
                "E100",
                5.0,
                UNITE.MICRO_GRAMMES
        );

        assertNotNull(e);
        assertTrue(e instanceof Additif);
        assertEquals("E100", e.getNom());
        assertEquals(5.0, e.getValeur(), 0.0001);
        assertEquals(UNITE.MICRO_GRAMMES, e.getUnite());
    }

    @Test
    public void testCreateAllergene() {

        Element e = factory.createElement(
                TYPE_ELEMENT.ALLERGENE,
                "Gluten",
                1.0,
                UNITE.MILLI_GRAMMES
        );

        assertNotNull(e);
        assertTrue(e instanceof Allergene);
        assertEquals("Gluten", e.getNom());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullTypeThrowsException() {

        factory.createElement(null, "test", 1.0, UNITE.MICRO_GRAMMES);
    }
}