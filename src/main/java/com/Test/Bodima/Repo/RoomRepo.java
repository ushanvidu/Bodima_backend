package com.Test.Bodima.Repo;

import com.Test.Bodima.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends JpaRepository<Room, Integer> {
    Room findByRoomNumber(String roomNumber);
}