package com.hackerrank.weather.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.weather.entity.LocationEntity;
import com.hackerrank.weather.entity.WeatherEntity;
import com.hackerrank.weather.model.DeleteParams;
import com.hackerrank.weather.model.Location;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.model.WeatherJson;
import com.hackerrank.weather.repository.LocationRespository;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class WeatherApiRestController {

    @Autowired
    private LocationRespository locationRespository;

    @Autowired
    private WeatherRepository weatherRepository;


    @RequestMapping(path = "/erase", method = RequestMethod.DELETE)
    public void deleteAll( HttpServletResponse response){

        try{


                weatherRepository.deleteAll();


            response.setStatus(HttpServletResponse.SC_OK);

        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

    }


    @RequestMapping(path = "/erase", method = RequestMethod.DELETE, params = "lat")
    public void deleteAll(@ModelAttribute DeleteParams deleteParams, HttpServletResponse response){

        try{

            if(deleteParams.getStartDate() != null && deleteParams.getEndDate() != null && deleteParams.getLat() != 0 && deleteParams.getLon() != 0){

                LocationEntity locationEntity;
                locationEntity   =   locationRespository.findByLatitudeAndLongitude(deleteParams.getLat(),deleteParams.getLon());


                weatherRepository.deleteAllByDateBetweenAndLocationId(deleteParams.getStartDate(),deleteParams.getEndDate(),locationEntity.getId());

            }

            response.setStatus(HttpServletResponse.SC_OK);

        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

    }


    @RequestMapping(path = "/weather", method = RequestMethod.POST)
    public void saveWeatherData(@RequestBody String payload, HttpServletResponse response)  {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Weather weather =objectMapper.readValue(payload,Weather.class);
            WeatherEntity weatherEntity;
            weatherEntity = weatherRepository.findByWeatherId(weather.getId());

          if(weatherEntity == null){




              Location location = weather.getLocation();

              LocationEntity locationEntity;


              locationEntity = locationRespository.findByLatitudeAndLongitude(location.getLatitude(),location.getLongitude());

              if(locationEntity == null){
                  locationEntity = new LocationEntity();

                  locationEntity.setCity(location.getCityName());
                  locationEntity.setLatitude(location.getLatitude());
                  locationEntity.setLongitude(location.getLongitude());
                  locationEntity.setState(location.getStateName());


                  locationEntity = locationRespository.save(locationEntity);
              }

              weatherEntity = new WeatherEntity();



              Float[] temp =weather.getTemperature();

              String tempString = "";
              for (int i=0;i<temp.length;i++){
                  if(i==0){
                      tempString += temp[i];
                  }else{
                      tempString +=","+temp[i];
                  }

              }
              weatherEntity.setWeatherId(weather.getId());
              weatherEntity.setTemperature(tempString);
              weatherEntity.setLocationId(locationEntity.getId());
              weatherEntity.setDate(weather.getDateRecorded());

              weatherRepository.save(weatherEntity);

                response.setStatus(HttpServletResponse.SC_OK);
          }else{
              response.setStatus(400);
          }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


    }



    @RequestMapping(path = "/weather", method = RequestMethod.GET)
    public List<WeatherJson> getAllWeatherData(){


      List<WeatherEntity> entityList=  weatherRepository.getAllByWeatherIdIsNotNullOrderByWeatherId();
        List<WeatherJson> weatherList = new ArrayList<>();
      if(entityList!= null){


          for (WeatherEntity e: entityList){
              WeatherJson weather = new WeatherJson();

                weather.setId(e.getWeatherId());
                weather.setDateRecorded(new SimpleDateFormat("yyyy-MM-dd").format(e.getDate()));

                String[] tempsList =e.getTemperature().split(",");
                Float[] floats = new Float[tempsList.length];
                for(int i =0; i<tempsList.length;i++){
                    floats[i]= Float.parseFloat(tempsList[i]);
                }

                weather.setTemperature(floats);


                LocationEntity locationEntity = locationRespository.findOne(e.getLocationId());

                Location location = new Location();
                location.setCityName(locationEntity.getCity());
                location.setLatitude(locationEntity.getLatitude());
                location.setLongitude(locationEntity.getLongitude());
                location.setStateName(locationEntity.getState());

                weather.setLocation(location);

                weatherList.add(weather);


          }

          return weatherList;
      }else {
          return  null;
      }


    }


    @RequestMapping(path = "/weather", method = RequestMethod.GET,params = "lat")
    public List<WeatherJson> getAllWeatherData(@RequestParam("lat") Float lat, @RequestParam("lon") Float lon,HttpServletResponse response){


        LocationEntity locationEntity = locationRespository.findByLatitudeAndLongitude(lat,lon);
        List<WeatherJson> weatherList = new ArrayList<>();
        if(locationEntity != null){

            List<WeatherEntity> entityList;
                Location location = new Location();
                location.setStateName(locationEntity.getState());
                location.setLongitude(locationEntity.getLongitude());
                location.setLatitude(locationEntity.getLatitude());
                location.setCityName(locationEntity.getCity());

                WeatherJson weatherJson=new WeatherJson();

            entityList = weatherRepository.findAllByLocationIdOrderByWeatherId(locationEntity.getId());

            for (WeatherEntity e: entityList){
                WeatherJson weather = new WeatherJson();

                weather.setId(e.getWeatherId());
                weather.setDateRecorded(new SimpleDateFormat("yyyy-MM-dd").format(e.getDate()));

                String[] tempsList =e.getTemperature().split(",");
                Float[] floats = new Float[tempsList.length];
                for(int i =0; i<tempsList.length;i++){
                    floats[i]= Float.parseFloat(tempsList[i]);
                }

                weather.setTemperature(floats);

                 weather.setLocation(location);

                weatherList.add(weather);


            }


            return  weatherList;


        }else{
            response.setStatus(404);
        }

        return null;
    }




    @RequestMapping(path = "/weather", method = RequestMethod.GET,params = "start")
    public List<Object> findDateData(@RequestParam("start") java.sql.Date start, @RequestParam("end") java.sql.Date end){



        List<WeatherEntity> entityList;
        entityList = weatherRepository.findAllByDateGreaterThanEqualAndDateLessThanEqual(start,end);
        if(entityList!=null){

            for (WeatherEntity e:entityList){

            }



        }  else{
            return null;
        }




        return null;
    }

}
