package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TimeEntryRepositoryImpl implements TimeEntryRepository {

    List<TimeEntry> repository;
    int idCounter = 0;

    public TimeEntryRepositoryImpl() {
        this.repository = new ArrayList<TimeEntry>();
    }

    public TimeEntryRepositoryImpl(List<TimeEntry> list) {
        this.repository = list;
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        this.repository.add(timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        for(TimeEntry entry : this.repository) {
            if(entry.getId() == id){
                return entry;
            }
        }
        return null;
    }

    @Override
    public List<TimeEntry> list() {
        return this.repository;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        for(int i = 0; i < this.repository.size(); i++) {
            if(this.repository.get(i).getId() == id) {
                this.repository.set(i, timeEntry);
                return timeEntry;
            }
        }
        return null;
    }

    @Override
    public Boolean delete(long id) {
        for(int i = 0; i < this.repository.size(); i++) {
            if(this.repository.get(i).getId() == id) {
                this.repository.remove(i);
                 return true;
            }
        }
        return false;
    }
}
