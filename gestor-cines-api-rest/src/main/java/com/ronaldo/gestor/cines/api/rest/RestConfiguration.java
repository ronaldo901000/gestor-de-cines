package com.ronaldo.gestor.cines.api.rest;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api/v1")
public class RestConfiguration extends ResourceConfig {

       public RestConfiguration() {
              packages("com.ronaldo.gestor.cines.api.rest.resources").register(MultiPartFeature.class);
       }

}
