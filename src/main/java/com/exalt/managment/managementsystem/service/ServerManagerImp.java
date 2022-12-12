package com.exalt.managment.managementsystem.service;

import com.exalt.managment.managementsystem.exception.RefusedRequestException;
import com.exalt.managment.managementsystem.exception.ServerNotFoundException;
import com.exalt.managment.managementsystem.model.dao.ServerDAO;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implementation of ServerManager Interface
 */
public class ServerManagerImp implements ServerManager {

  private static final ServerManagerImp INSTANCE = new ServerManagerImp();
  private static final List<ServerDAO> servers = new ArrayList<>();

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ServerManagerImp.class);

  private ServerManagerImp() {
    super();
  }

  /**
   * Get the Singleton instance of the ServerManager.
   *
   * @return the ServerManager Instance
   */
  public static ServerManagerImp getINSTANCE() {
    return INSTANCE;
  }

  public ServerDAO allocateServer(float requestedMemory)
      throws RefusedRequestException, InterruptedException {
    LOGGER.info("Requested {}GB of memory", requestedMemory);
    if (requestedMemory > ServerDAO.SERVER_SIZE || requestedMemory < 1) {
      LOGGER.warn("Maximum server size is {}GB can't allocate {}GB.", ServerDAO.SERVER_SIZE,
          requestedMemory);
      throw new RefusedRequestException(
          "Request failed. The Maximum server this is 100GB.");
    }
    ServerDAO allocatedServer = allocateToSuitableServer(requestedMemory);
    waitUntilServerActivate(allocatedServer);
    return allocatedServer;
  }

  private synchronized ServerDAO allocateToSuitableServer(float requestedMemory) {
    ServerDAO availableServer = findFreeServer(requestedMemory);
    String newlyCreated = "";
    if (availableServer == null) {
      availableServer = createNewServer();
      newlyCreated = "Newly created ";
    }
    availableServer.allocate(requestedMemory);
    LOGGER.info("Allocated {}GB of memory from {}server {}", requestedMemory, newlyCreated,
        availableServer.getId());
    return availableServer;
  }

  private ServerDAO findFreeServer(float requestedMemory) {
    synchronized (servers) {
      Optional<ServerDAO> freeServer = servers.stream()
          .filter(server -> server.isAvailable(requestedMemory))
          .findAny();
      return freeServer.orElse(null);
    }
  }

  private ServerDAO createNewServer() {
    ServerDAO newServer = new ServerDAO(servers.size()+1);
    servers.add(newServer);
    return newServer;
  }

  private void waitUntilServerActivate(@NotNull ServerDAO allocatedServer)
      throws InterruptedException {
    if (!allocatedServer.isActive()) {
      CountDownLatch latch = new CountDownLatch(1);
      allocatedServer.addWaitingLatch(latch);
      latch.await();
      LOGGER.info("Server {} is active", allocatedServer.getId());
    }
  }

  public List<ServerDAO> getServers() throws ServerNotFoundException {
    if (servers.isEmpty()) {
      throw new ServerNotFoundException("No servers in the system.");
    }
    return servers;
  }
}
