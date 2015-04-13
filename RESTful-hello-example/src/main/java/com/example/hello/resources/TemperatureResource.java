package com.example.hello.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.hello.jaxb.Temperature;

/**
 * Created on 4/13/15.
 */
@Path("/temperature")
public class TemperatureResource {

    @GET
    @Path("/celsius/{c}")
    @Produces({ MediaType.APPLICATION_XML })
    public Temperature getTemperatureInFahrenheit(@PathParam("c") double c) {
        Temperature t = new Temperature();
        t.setCelsius(c);
        Double fahrenheit;
        Double celsius = c;
        fahrenheit = ((celsius * 9) / 5) + 32;
        t.setFahrenheit(fahrenheit);

        return t;
    }

    @GET
    @Path("/fahrenheit/{f}")
    @Produces({ MediaType.APPLICATION_XML })
    public Temperature getTemperatureInCelsius(@PathParam("f") double f) {
        Temperature t = new Temperature();
        t.setFahrenheit(f);
        double celsius;
        celsius =  (f - 32)*5/9;
        t.setCelsius(celsius);

        return t;
    }
}
