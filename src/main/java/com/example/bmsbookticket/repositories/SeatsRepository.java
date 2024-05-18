package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatsRepository extends JpaRepository<Seat, Integer> {
    @Override
    Optional<Seat> findById(Integer integer);
}
