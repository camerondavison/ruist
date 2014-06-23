package com.a86c6f7964.ruist.example.jersey;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 */
@Singleton
@Path("/api")
public class SimpleRest {
    @GET()
    @Path("/ruok")
    public String ruok() {
        return "imok";
    }
}
