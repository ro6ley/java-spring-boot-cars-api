package com.example.cars;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.example.cars.models.Car;

/**
 * @author <ro6ley.github.io>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarsApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarsApplicationTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int port;

  private String getRootUrl() {
    return "http://localhost:" + port + "/api/v1";
  }

  @Test
  public void contextLoads() {
	}

	/**
	 * Here we test that we can get all the cars in the database
	 * using the GET method
	 */
  @Test
  public void testGetAllCars() {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/cars",
        HttpMethod.GET, entity, String.class);

    Assert.assertNotNull(response.getBody());
  }

	/**
	 * Here we test that we can fetch a single car using its id
	 */
  @Test
  public void testGetCarById() {
    Car car = restTemplate.getForObject(getRootUrl() + "/cars/1", Car.class);
		System.out.println(car.getCarName());
    Assert.assertNotNull(car);
  }

	/**
	 * Here we test that we can create a car using the POST method
	 */
  @Test
  public void testCreateCar() {
    Car car = new Car();
    car.setCarName("Prius");
    car.setDoors(4);

    ResponseEntity<Car> postResponse = restTemplate.postForEntity(getRootUrl() + "/cars", car, Car.class);
    Assert.assertNotNull(postResponse);
    Assert.assertNotNull(postResponse.getBody());
  }

	/**
	 * Here we test that we can update a car's information using the PUT method
	 */
  @Test
  public void testUpdateCar() {
    int id = 1;
    Car car = restTemplate.getForObject(getRootUrl() + "/cars/" + id, Car.class);
    car.setCarName("Tesla");
    car.setDoors(2);

    restTemplate.put(getRootUrl() + "/cars/" + id, car);

    Car updatedCar = restTemplate.getForObject(getRootUrl() + "/cars/" + id, Car.class);
    Assert.assertNotNull(updatedCar);
  }

	/**
	 * Here we test that we can delete a car by using the DELETE method,
	 * then we verify that it no longer exists in the database
	 */
  @Test
  public void testDeleteCar() {
    int id = 2;
    Car car = restTemplate.getForObject(getRootUrl() + "/cars/" + id, Car.class);
    Assert.assertNotNull(car);

    restTemplate.delete(getRootUrl() + "/cars/" + id);

    try {
      car = restTemplate.getForObject(getRootUrl() + "/cars/" + id, Car.class);
    } catch (final HttpClientErrorException e) {
      Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
    }
  }

}
