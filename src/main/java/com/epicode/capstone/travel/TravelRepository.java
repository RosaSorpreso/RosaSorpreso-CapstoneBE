package com.epicode.capstone.travel;

import com.epicode.capstone.category.Category;
import com.epicode.capstone.continent.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Long> {

    List<Travel> findByContinent(Continent continent);

    List<Travel> findByCategory(Category category);

    List<Travel> findByIsSoldOut(boolean isSoldOut);

    List<Travel> findByPassportIsRequired(boolean passportIsRequired);

    @Query("SELECT t FROM Travel t WHERE MONTH(t.startDate) = :month OR MONTH(t.endDate) = :month")
    List<Travel> findByMonth(@Param("month") int month);

}
