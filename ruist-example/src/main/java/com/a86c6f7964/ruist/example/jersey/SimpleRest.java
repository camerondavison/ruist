package com.a86c6f7964.ruist.example.jersey;

import com.indeed.util.varexport.Export;
import com.indeed.util.varexport.VarExporter;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 */
@Singleton
@Path("/api")
public class SimpleRest {
    private volatile long i = 0;

    public SimpleRest() {
        VarExporter.forNamespace(getClass().getSimpleName()).includeInGlobal().export(this, "");
    }

    @Export(name = "i")
    public long getI() {
        return i;
    }

    @GET()
    @Path("/ruok")
    public String ruok() {
        i++;
        return "imok";
    }
}
