package com.example.cars.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * This class will represent our car and its attributes:
 * - ID
 * - Name
 * - Number of doors
 * 
 * @author Robley Gori - ro6ley.github.io
 */
@Entity
@Table(name = "cars")   // the table in the database that will contain our cars data
@EntityListeners(AuditingEntityListener.class)
public class Car {
  
  /**
   * The attributes of the car
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;    // Each car will be given an auto-generated unique identifier when stored

  @Column(name = "car_name", nullable = false)
  private String carName;    // We will also save the name of the car

  @Column(name = "doors", nullable = false)
  private int doors;    // We will also save the number of doors that a car has

  /**
   * Our getters and setters for the attributes above
   */
  @ApiModelProperty(name="id",
                    value="The id of the car",
                    example="1")
  public long getId() {
    return id;
  }

  public void setId(long value) {
    this.id = value;
  }

  @ApiModelProperty(name="carName",
                    value="The name of the car to be saved",
                    example="Bugatti",
                    required=true)
  public String getCarName() {
    return carName;
  }

  public void setCarName(String value) {
    this.carName = value;
  }
  
  @ApiModelProperty(name="doors",
                    value="The number of doors that the car has",
                    example="2",
                    required=true)
  public int getDoors() {
    return doors;
  }

  public void setDoors(int value) {
    this.doors = value;
  }

}
