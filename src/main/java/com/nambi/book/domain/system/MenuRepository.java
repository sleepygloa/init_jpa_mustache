package com.nambi.book.domain.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT p FROM Menu p ORDER BY p.menuLev, p.menuParentSeq, p.menuSeq ASC")
    List<Menu> findAllDesc();
}
