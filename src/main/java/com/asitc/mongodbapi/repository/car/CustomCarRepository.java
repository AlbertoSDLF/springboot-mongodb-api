package com.asitc.mongodbapi.repository.car;

import java.util.List;

import org.springframework.data.repository.query.Param;

public interface CustomCarRepository {

	List<Car> findByMake(@Param("make") String make);

	List<Car> findByYear(@Param("year") Integer year, @Param("make") String make, @Param("model") String model);
}