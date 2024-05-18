package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Override
    Optional<Ticket> findById(Integer integer);
}
