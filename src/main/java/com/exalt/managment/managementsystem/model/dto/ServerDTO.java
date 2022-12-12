package com.exalt.managment.managementsystem.model.dto;

import com.exalt.managment.managementsystem.service.Status;

/**
 * The type ServerDTO is the server data transfer object. It is the object that's used between
 * service layer and controller layer.
 */
public class ServerDTO {

  float capacity;
  int users;
  long id;
  Status status;

  public ServerDTO() {
  }

  public float getCapacity() {
    return capacity;
  }

  public void setCapacity(float capacity) {
    this.capacity = capacity;
  }

  public int getUsers() {
    return users;
  }

  public void setUsers(int users) {
    this.users = users;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
