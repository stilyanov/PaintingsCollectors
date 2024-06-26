package com.paintingscollectors.repository;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {

    List<Painting> findAllByOwnerId(Long id);

    @Query("SELECT p FROM Painting p WHERE p.owner.id <> :userSession")
    List<Painting> findAllByOwnerNot(@Param("userSession") Long id);

    @Transactional
    void deletePaintingByIdAndOwnerId(Long paintingId, Long userId);


}