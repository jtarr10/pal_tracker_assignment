package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;

    public TimeEntryController() {
        timeEntryRepository = new InMemoryTimeEntryRepository();
    }

    public TimeEntryController(TimeEntryRepository repo) {
        timeEntryRepository = repo;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry result = timeEntryRepository.create(timeEntry);
        if(result != null)
            return ResponseEntity.created(URI.create("/time-entries/" + timeEntry.getId())).body(result);
        else
            return ResponseEntity.badRequest().body(timeEntry);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry result = timeEntryRepository.find(id);
        if(result != null)
            return ResponseEntity.ok().body(result);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok().body(timeEntryRepository.list());
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry results = timeEntryRepository.update(id, timeEntry);
        if(results != null)
            return ResponseEntity.ok().body(results);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        timeEntryRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
