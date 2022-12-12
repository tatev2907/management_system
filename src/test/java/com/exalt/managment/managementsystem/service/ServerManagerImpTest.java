package com.exalt.managment.managementsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.lt;
import static org.mockito.ArgumentMatchers.anyFloat;

import com.exalt.managment.managementsystem.exception.RefusedRequestException;
import com.exalt.managment.managementsystem.model.dao.ServerDAO;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ServerManagerImpTest {

  private static void addServersIntoServerManager(ServerDAO... newServers)
      throws IllegalAccessException, NoSuchFieldException {
    Field serversArrayField = ServerManagerImp.class.getDeclaredField("servers");
    serversArrayField.setAccessible(true);
    Object serversObject = serversArrayField.get(ServerManagerImp.getINSTANCE());
    try {
      if (serversObject instanceof ArrayList<?>) {
        ArrayList<?> ManagerServers = ((ArrayList<?>) serversObject);
        ((ArrayList<ServerDAO>) ManagerServers)
            .addAll(Arrays.asList(newServers));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @BeforeAll
  static void setUp() throws NoSuchFieldException, IllegalAccessException {
    ServerDAO server1 = Mockito.mock(ServerDAO.class);
    ServerDAO server2 = Mockito.mock(ServerDAO.class);
    addServersIntoServerManager(server1, server2);
  }

  @Test
  void getServers_TwoServers_True() {
    assertDoesNotThrow(() ->
        assertEquals(2, ServerManagerImp.getINSTANCE().getServers().size(),
            "Incorrect number of servers retrieved"));

  }

  @Test
  void allocateServer_MoreThanMaximumSize_ExceptionThrown() {
    assertThrowsExactly(RefusedRequestException.class, () ->
            ServerManagerImp.getINSTANCE().allocateServer(ServerDAO.SERVER_SIZE * 2),
        "Can't allocate memory more than the Server's maximum size.");
  }

  @Test
  void allocateServer_ServerIsInActiveState_True()
      throws InterruptedException, RefusedRequestException {
    ServerDAO newServer = ServerManagerImp.getINSTANCE().allocateServer(ServerDAO.SERVER_SIZE);
    assertEquals(Status.ACTIVE, newServer.getStatus(), "Server isn't in active state yet");
  }
}