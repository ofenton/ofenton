package com.example.hello.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on 4/13/15.
 */
@XmlRootElement(name = "temperature")
public final class Temperature {

    @XmlElement
    private double celsius;
    @XmlElement
    private double fahrenheit;

    public void setCelsius(double celsius) {
        this.celsius = celsius;
    }

    public void setFahrenheit(double fahrenheit) {
        this.fahrenheit = fahrenheit;
    }

}
