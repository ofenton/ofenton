package com.example.hello;

import com.example.hello.resources.CtoFResource;
import com.example.hello.resources.FtoCResource;
import com.example.hello.resources.HelloResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created on 3/31/15.
 */
public class HelloApplication extends Application<HelloConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public void initialize(Bootstrap<HelloConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(HelloConfiguration configuration,
                    Environment environment) {

        environment.jersey().register(new HelloResource());
        environment.jersey().register(new CtoFResource());
        environment.jersey().register(new FtoCResource());
    }
}
