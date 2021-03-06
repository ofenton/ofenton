package com.example.hello.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 4/13/15.
 */
@Path("/ftocservice")
public class FtoCResource {

    @GET
    @Produces("application/json")
    public Response convertFtoC() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        Double fahrenheit = 98.24;
        Double celsius;
        celsius = (fahrenheit - 32)*5/9;
        jsonObject.put("F Value", fahrenheit);
        jsonObject.put("C Value", celsius);

        String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
        return Response.status(200).entity(result).build();
    }

    @Path("{s}")
    @GET
    @Produces("application/json")
    public Response convertFtoCfromInput(@PathParam("s") String s) throws JSONException {

        Float f = null;
        try {
            f = Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Request must be a number").build();
        }

        JSONObject jsonObject = new JSONObject();
        float celsius;
        celsius =  (f - 32)*5/9;
        jsonObject.put("F Value", f);
        jsonObject.put("C Value", celsius);

        String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
        return Response.status(200).entity(result).build();
    }

}
