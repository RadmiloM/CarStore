package com.cars.RentACar.repository;

import com.cars.RentACar.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
    @Query("SELECT c FROM Car c WHERE (:year IS NULL OR c.year >= :year) " +
            " AND (:make IS NULL OR c.make LIKE %:make%) " +
            " AND (:model IS NULL OR c.model LIKE %:model%) " +
            " AND (:automatic IS NULL OR c.automatic = :automatic) " +
            " AND (:price IS NULL OR c.price <= :price) " +
            " AND (:power IS NULL OR c.power <= :power) " +
            " AND (:doors IS NULL OR c.doors = :doors)")
    List<Car> searchCar(@Param("year") Integer year,
                        @Param("make") String make,
                        @Param("model") String model,
                        @Param("automatic") Boolean automatic,
                        @Param("price") Double price,
                        @Param("power") Integer power,
                        @Param("doors") Integer doors);
}
