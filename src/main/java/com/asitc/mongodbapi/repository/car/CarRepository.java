package com.asitc.mongodbapi.repository.car;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "car", path = "car")
public interface CarRepository extends PagingAndSortingRepository<Car, String>, CustomCarRepository {

	List<Car> findByMakeAndModelAndYear(@Param("make") String make, @Param("model") String model,
			@Param("year") Integer year);

	// List<Car> findByOwner(@Param("person_id") Integer personId);
}
