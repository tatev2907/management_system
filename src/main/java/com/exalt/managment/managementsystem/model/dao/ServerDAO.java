package com.exalt.managment.managementsystem.model.dao;


import com.exalt.managment.managementsystem.service.Status;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type ServerDAO.
 */
public class ServerDAO {

  /**
   * The maximum server size.
   */
  public static final float SERVER_SIZE = 100;
  /**
   * The time taken until the newly created server become in active status.
   */
  public static final int SERVER_STARTUP_DELAY = 6000;
  private static final Logger LOGGER =
      LoggerFactory.getLogger(ServerDAO.class);
  private final long id;
  private float capacity;
  private int users;
  private Status status;

  /**
   * Instantiates a new ServerDAO.
   */
  public ServerDAO(int id) {
    super();
    this.status = Status.NEWLYCREATED;
    this.capacity = SERVER_SIZE;
    this.id = id;
    startServer();
  }

  /**
   * Checks if the current server has enough memory
   *
   * @param requiredMemory the required memory
   * @return boolean result
   */
  public boolean isAvailable(float requiredMemory) {
    return capacity >= requiredMemory;
  }

  /**
   * Allocate the requested memory.
   *
   * @param memory the memory
   */
  public void allocate(float memory) {
    capacity -= memory;
    users++;
  }

  private void startServer() {
    try {
      Thread.sleep(SERVER_STARTUP_DELAY);
      status = Status.ACTIVE;
      LOGGER.info("Server " + id + " is now active.");
    } catch (InterruptedException e) {
      LOGGER.error("Server startup interrupted: " + e.getMessage());
    }
  }

  /**
   * Gets the current server capacity.
   *
   * @return the capacity
   */
  public float getCapacity() {
    return capacity;
  }

  /**
   * Gets all current users of server.
   *
   * @return the users
   */
  public int getUsers() {
    return users;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Gets current server status.
   *
   * @return the status
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Sets status.
   *
   * @param status the status
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * Checks if the server is active.
   *
   * @return the boolean
   */
  public boolean isActive() {
    return status == Status.ACTIVE;
  }
}
