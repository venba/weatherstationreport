package com.weatherstation.report.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.weatherstation.report.entity.Station;

@Repository
public interface StationRepository extends CrudRepository<Station, Long> {
    @Override
    public List<Station> findAll();

    @Query("SELECT t FROM Station t WHERE t.date>= :startDate and t.date<=:endDate")
    public List<Station> search(@Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate);
}
