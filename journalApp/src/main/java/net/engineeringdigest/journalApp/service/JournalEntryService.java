package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    JournalEntryRepository journalEntryRepository;
    @Autowired
    UserService userService;
  @Transactional
    public void saveEntry(JournalEntry journalEntry,String userName){
        Users user=userService.findByUserName(userName);
        JournalEntry save=journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(save);
        userService.saveUserEntry(user);
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
      return  journalEntryRepository.findAll();
    }
  public Optional<JournalEntry>findById(ObjectId id){
        return journalEntryRepository.findById(id);
  }
  @Transactional
  public boolean deleteById(ObjectId id,String userName){
      boolean removed=false;
      try {
          Users user = userService.findByUserName(userName);
          removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
          if (removed) {
              userService.saveUserEntry(user);
              journalEntryRepository.deleteById(id);
          }
      }
      catch(Exception e){
          System.out.println(e);
      }
      return removed;
  }

}
