package com.hackerrank.weather.repository;

import com.hackerrank.weather.entity.LocationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRespository extends CrudRepository<LocationEntity, Long> {

    LocationEntity findByLatitudeAndLongitude(float lat, float longit);

}
