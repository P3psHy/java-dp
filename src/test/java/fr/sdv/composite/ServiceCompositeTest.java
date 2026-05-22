package fr.sdv.composite;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceCompositeTest {
        
    Employe employe1 = new Employe();
    Employe employe2 = new Employe();
    Employe employe3 = new Employe();
    Employe employe4 = new Employe();
    Employe employe5 = new Employe();
    Employe employe6 = new Employe();

    Service service1 = new Service();
    Service service2 = new Service();
    Service service3 = new Service();


    

    @Before
    public void setUp() {

        this.employe1.setNom("RAPSEY");
        this.employe1.setPrenom("Cecile");
        this.employe1.setSalaire(10000);

        this.employe2.setNom("BECHKAR");
        this.employe2.setPrenom("Bilel");
        this.employe2.setSalaire(8000);

        this.service1.addElement(employe1);
        this.service1.addElement(employe2);

        this.employe3.setNom("GUINEAU");
        this.employe3.setPrenom("kevin");
        this.employe3.setSalaire(7500);
        
        this.employe4.setNom("MARTIN");
        this.employe4.setPrenom("Paul");
        this.employe4.setSalaire(3500);

        this.service2.addElement(employe3);
        this.service2.addElement(employe4);


        this.employe5.setNom("RANMEY");
        this.employe5.setPrenom("JB");
        this.employe5.setSalaire(7500);
        
        this.employe6.setNom("DOE");
        this.employe6.setPrenom("Jane");
        this.employe6.setSalaire(3500);

        this.service3.addElement(employe5);
        this.service3.addElement(employe6);


    }

    @Test
    public void testCalculerTotalSalaireValide() {

        double salaireTotals1 = service1.calculerSalaire();
        assertEquals(18000, salaireTotals1, 0);

        double salaireTotals2 = service2.calculerSalaire();
        assertEquals(11000, salaireTotals2, 0);
        
        double salaireTotals3 = service3.calculerSalaire();
        assertEquals(11000, salaireTotals3, 0);
    }


}
