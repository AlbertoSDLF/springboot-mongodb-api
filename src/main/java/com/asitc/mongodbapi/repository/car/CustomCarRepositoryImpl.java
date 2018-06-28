package com.asitc.mongodbapi.repository.car;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import lombok.extern.slf4j.Slf4j;

@CacheConfig(cacheNames = "cars")
@Slf4j
public class CustomCarRepositoryImpl implements CustomCarRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@Cacheable
	public List<Car> findByMake(final String make) {
		log.info("findByMake:: Non-cached execution");
		final Query query = new Query();
		query.addCriteria(Criteria.where("make").is(make));
		final List<Car> carList = this.mongoTemplate.find(query, Car.class);
		return carList;
	}

	@Override
	public List<Car> findByYear(final Integer year, final String make, final String model) {
		final List<Criteria> criteriaList = new ArrayList<Criteria>();
		criteriaList.add(Criteria.where("year").is(year));
		if (make != null) {
			criteriaList.add(Criteria.where("make").is(make));
		}
		if (model != null) {
			criteriaList.add(Criteria.where("model").is(model));
		}
		final Query query = new Query();
		query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
		// query.addCriteria(Criteria.where("year").is(year));
		// if (make != null) {
		// query.addCriteria(Criteria.where("make").is(make));
		// }
		// if (model != null) {
		// query.addCriteria(Criteria.where("model").is(model));
		// }
		final List<Car> carList = this.mongoTemplate.find(query, Car.class);
		return carList;
	}
}
