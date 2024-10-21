package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

@Repository
public interface IPositionRepository extends JpaRepository<Position, Long> {
    /**
     * Created by: PhongTD
     * Date created: 12/07/2023
     *
     * @return List position
     */
    @Query("SELECT position FROM Position position")
    List<Position> findAll();

    /**
     * Created by: PhongTD
     * Date created: 12/07/2023
     *
     * @param id
     * @return position was found by id
     */
    @Query("SELECT position FROM Position position WHERE position.positionId = ?1")
    Position findAllById(Long id);

    @Query(value = "select p.position_id, p.position_name from position p where p.position_id = :id",
            nativeQuery = true)
    Optional<Position> findById(@Param("id") Long id);
}
