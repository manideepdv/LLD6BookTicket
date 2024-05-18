package com.example.bmsbookticket.repositories;


import com.example.bmsbookticket.models.Seat;
import com.example.bmsbookticket.models.Show;
import com.example.bmsbookticket.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer> {
    @Override
    Optional<ShowSeat> findById(Integer integer);

    @Override
    List<ShowSeat> findAllById(Iterable<Integer> integers);

    Optional<ShowSeat> findByShow(Show show);

    Optional<ShowSeat> findBySeat(Seat seat);
}
