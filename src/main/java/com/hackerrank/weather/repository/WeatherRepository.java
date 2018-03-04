package com.hackerrank.weather.repository;

import com.hackerrank.weather.entity.WeatherEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface WeatherRepository extends CrudRepository<WeatherEntity, Long> {

    void deleteAllByDateBetweenAndLocationId(Date d1, Date d2, long id);

    WeatherEntity findByWeatherId(long id);

    List<WeatherEntity> getAllByWeatherIdIsNotNullOrderByWeatherId();


    List<WeatherEntity> findAllByLocationIdOrderByWeatherId(Long id);


    List<WeatherEntity> findAllByDateGreaterThanEqualAndDateLessThanEqual(Date d1, Date d2);
}
