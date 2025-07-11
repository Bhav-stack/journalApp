package com.example.journalApp.Service;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.JournalEntryRepository;
import com.example.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private  JournalEntryRepository journalEntryRepository;
@Autowired
private UserService userService;


@Transactional
    public  void saveEntry(JournalEntry journalEntry,String userName) {
    try {
        User user = userService.findByUsername(userName);

        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    } catch (Exception e) {
        System.out.println(e);
        throw new RuntimeException("an error has occured", e);
    }
}

    public  void saveEntry(JournalEntry journalEntry) {

        journalEntryRepository.save(journalEntry);
    }

    public  List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }
    public  Optional<JournalEntry> findById(ObjectId id1){
        return journalEntryRepository.findById(id1);
    }
    @Transactional
    public boolean deleteById(ObjectId id1,String userName ) {
       boolean removed = false;
        try {
            User user = userService.findByUsername(userName);

            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id1));
            if (removed) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id1);
            }

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error has occcured while deleting the entry", e);
        }
        return removed;
    }
}
