package com.example.hello.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created on 3/31/15.
 */
@Path("/hello")
public class HelloResource {

    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String msg) {

        String output = "Jersey say : hello " + msg;

        return Response.status(200).entity(output).build();

    }

}
