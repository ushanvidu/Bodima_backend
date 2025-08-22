package com.Test.Bodima.Service;
import com.Test.Bodima.Model.Room;
import com.Test.Bodima.Repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

    @Service
    public class RoomService {
        @Autowired
        private RoomRepo roomRepository;

        public Room createRoom(Room room) {
            return roomRepository.save(room);
        }

        public Optional<Room> getRoomById(Integer id) {
            return roomRepository.findById(id);
        }

        public List<Room> getAllRooms() {
            return roomRepository.findAll();
        }

        public Room updateRoom(Integer id, Room roomDetails) {
            return roomRepository.findById(id)
                    .map(room -> {
                        room.setRoomNumber(roomDetails.getRoomNumber());
                        room.setCapacity(roomDetails.getCapacity());
                        return roomRepository.save(room);
                    })
                    .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        }

        public void deleteRoom(Integer id) {
            roomRepository.deleteById(id);
        }
    }

