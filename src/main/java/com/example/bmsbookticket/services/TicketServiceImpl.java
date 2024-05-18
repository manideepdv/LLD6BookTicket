package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.*;
import com.example.bmsbookticket.repositories.ShowRepository;
import com.example.bmsbookticket.repositories.ShowSeatRepository;
import com.example.bmsbookticket.repositories.TicketRepository;
import com.example.bmsbookticket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private final UserRepository userRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(UserRepository userRepository, ShowSeatRepository showSeatRepository, ShowRepository showRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(List<Integer> showSeatIds, int userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new Exception("User not found");
        }
        User user = optionalUser.get();

        // synchronized (this) {
            List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
            if (showSeats.size() != showSeatIds.size() || showSeats.isEmpty()) {
                throw new Exception("Show seats do not match");
            }
            Optional<Show> optionalShow = showRepository.findById(showSeats.get(0).getShow().getId());
            if (optionalShow.isEmpty()) {
                throw new Exception("Show not found");
            }
            Show show = optionalShow.get();
            List<Integer> unAvailableSeatIds = new ArrayList<>();
            for (ShowSeat showSeat : showSeats) {
                if (!showSeat.getStatus().equals(SeatStatus.AVAILABLE)) {
                    unAvailableSeatIds.add(showSeat.getId());
                }
            }
            if (!unAvailableSeatIds.isEmpty()) {
                throw new Exception("Seats are not available " + unAvailableSeatIds);
            }
            for(ShowSeat showSeat : showSeats) {
                showSeat.setStatus(SeatStatus.BLOCKED);
                showSeatRepository.save(showSeat);
            }
            List<Seat> seats = showSeats.stream().map(ShowSeat::getSeat).toList();

            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setShow(show);
            ticket.setSeats(seats);
            ticket.setStatus(TicketStatus.UNPAID);
            ticket.setTimeOfBooking(new Date());
            return ticketRepository.save(ticket);
        // }
    }
}
