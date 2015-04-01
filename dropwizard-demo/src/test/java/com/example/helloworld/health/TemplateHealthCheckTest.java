package com.example.helloworld.health;

import com.codahale.metrics.health.HealthCheck;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

/**
 * Created on 4/1/15.
 */
public class TemplateHealthCheckTest {

    @Test
    public void checkSuccessTest() throws Exception {
        TemplateHealthCheck thc = new TemplateHealthCheck("TEST");
        HealthCheck.Result r = thc.check();
        assertTrue(r.isHealthy());
    }

    @Test
    public void checkFailureTest() throws Exception {
        TemplateHealthCheck thc = new TemplateHealthCheck("John");
        HealthCheck.Result r = thc.check();
        assertFalse(r.isHealthy());
        assertEquals(r.getMessage(), "template doesn't include a name");
    }
}
