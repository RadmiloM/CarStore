package com.cars.RentACar.repository;

import com.cars.RentACar.entity.Car;
import com.cars.RentACar.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {
    @Query("SELECT DISTINCT c.car FROM Contract c JOIN c.car car " +
            "WHERE (c.startDate BETWEEN :startDate AND :endDate) " +
            "AND (:year IS NULL OR car.year >= :year) " +
            "AND (:make IS NULL OR car.make LIKE %:make%) " +
            "AND (:model IS NULL or car.model = :model) " +
            "AND (:automatic IS NULL OR car.automatic = :automatic) " +
            "AND (:price IS NULL OR car.price <= :price) " +
            "AND (:power IS NULL OR car.power <= :power) " +
            "AND (:doors IS NULL OR car.doors = :doors)")
    List<Car> findByStartDateBetween(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate,
                                     @Param("year") Integer year,
                                     @Param("make") String make,
                                     @Param("model") String model,
                                     @Param("automatic") Boolean automatic,
                                     @Param("price") Double price,
                                     @Param("power") Integer power,
                                     @Param("doors") Integer doors);

    @Query("SELECT c FROM Contract c WHERE c.car.carId = :carId AND c.approved = false")
    List<Contract> findByCarId(@Param("carId") UUID carId);


    @Query("SELECT c FROM Contract c WHERE (c.user.userId = :userId) " +
            "AND (c.car.carId = :carId) " +
            "AND c.signed= FALSE")
    List<Contract> searchContractsWhichClientCanSign(@Param("userId") UUID userId,
                                                     @Param("carId") UUID carId);

    @Query("SELECT c FROM Contract c WHERE c.approved = FALSE")
    List<Contract> searchUnsignedContracts();

    @Query("SELECT c FROM Contract c WHERE c.user.userId= :userId")
    List<Contract> fetchClientContracts(@Param("userId") UUID userId);
}
