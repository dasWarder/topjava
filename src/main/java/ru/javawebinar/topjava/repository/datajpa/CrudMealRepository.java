package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.*;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    int delete(@Param("id") int id, @Param("user_id") int userId);

    @Transactional
    @Query("SELECT m FROM Meal m " +
            "WHERE  m.dateTime >= :startDateTime AND m.dateTime < :endDateTime AND m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> getAllBetween(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime")LocalDateTime endDateTime, @Param("userId")int userId);

    @Transactional
    @Query("SELECT m FROM Meal m " +
            "WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> getAll(@Param("userId")int userId);

    @Transactional
    @Query("SELECT m FROM Meal m " +
            "WHERE m.id=:id AND m.user.id=:userId")
    Meal get(@Param("id")int id, @Param("userId")int userId);

//    @Transactional
//    @Modifying
//    @Query("UPDATE Meal m SET m.user.id=:user_id")
//    Meal update(@Param("id") Meal meal, @Param("user_id") int userId);

}
