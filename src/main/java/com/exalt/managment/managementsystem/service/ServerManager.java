package com.exalt.managment.managementsystem.service;


import com.exalt.managment.managementsystem.exception.RefusedRequestException;
import com.exalt.managment.managementsystem.exception.ServerNotFoundException;
import com.exalt.managment.managementsystem.model.dao.ServerDAO;
import java.util.List;

/**
 * The interface for Servers Manager service. The ServerManager is the service responsible for
 * creating and allocating servers upon requests
 */
public interface ServerManager {

  /**
   * Method to allocate free server memory upon request, or create a new server in case existing
   * if there is no enough memory.
   *
   * @param requestedMemory the memory size requested
   * @return the server allocated to the request
   */
  ServerDAO allocateServer(float requestedMemory)
      throws RefusedRequestException, InterruptedException;

  /**
   * Gets all currently running servers.
   *
   * @return the servers list
   * @throws ServerNotFoundException Exception thrown in case no servers found
   */
  List<ServerDAO> getServers() throws ServerNotFoundException;
}
