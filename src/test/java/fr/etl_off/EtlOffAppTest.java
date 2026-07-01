package fr.etl_off;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EtlOffAppTest {
    @Test
    public void shouldBuildExpectedMessage() {
        EtlOffApp app = new EtlOffApp();
        assertEquals("ETL OFF ready: demo", app.buildMessage("demo"));
    }
}
