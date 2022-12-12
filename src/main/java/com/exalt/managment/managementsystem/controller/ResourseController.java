package com.exalt.managment.managementsystem.controller;

import com.exalt.managment.managementsystem.exception.ErrorMessage;
import com.exalt.managment.managementsystem.exception.RefusedRequestException;
import com.exalt.managment.managementsystem.exception.ServerNotFoundException;
import com.exalt.managment.managementsystem.mapper.ServerMapper;
import com.exalt.managment.managementsystem.model.dto.ServerDTO;
import com.exalt.managment.managementsystem.service.ServerManagerImp;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

@Path("/server")
public class ResourseController {

  @POST
  @Path("/allocateMemory/{size}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createMemory(@PathParam("size") float size) throws InterruptedException {
    ServerDTO allocatedServer;
    try {
      allocatedServer = ServerMapper.INSTANCE.serverDaoToDto(
          ServerManagerImp.getINSTANCE().allocateServer(size));
    } catch (RefusedRequestException e) {
      return Response.status(Status.METHOD_NOT_ALLOWED)
          .entity(new ErrorMessage(e.getMessage(), Status.METHOD_NOT_ALLOWED.getStatusCode()))
          .build();
    }
    return Response.ok().entity(allocatedServer).build();
  }

  @GET
  @Path("/all")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllServers() {
    List<ServerDTO> serversDTO;
    try {
      serversDTO = ServerMapper.INSTANCE.serverListToDto(
          ServerManagerImp.getINSTANCE().getServers());
    } catch (ServerNotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(new ErrorMessage(e.getMessage(), Status.NOT_FOUND.getStatusCode()))
          .build();
    }
    return Response.ok().entity(serversDTO).build();
  }
}
