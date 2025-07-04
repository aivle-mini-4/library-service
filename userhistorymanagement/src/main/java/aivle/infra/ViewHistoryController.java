package aivle.infra;

import aivle.domain.*;
import aivle.infra.QueryViewHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/viewHistories")
public class ViewHistoryController {

    @Autowired
    ViewHistoryRepository viewHistoryRepository;

    @Autowired
    QueryViewHistoryRepository queryViewHistoryRepository;

    
    @GetMapping("/viewHistories")
    public List<QueryViewHistory> getAllViewHistories(
        @RequestHeader("X-User-Id") Long userId
    ) {
        return queryViewHistoryRepository.findByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewHistory> get(@PathVariable Long id) {
        Optional<ViewHistory> viewHistory = viewHistoryRepository.findById(id);
        return viewHistory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<ViewHistory> create(@RequestBody ViewHistory viewHistory, @RequestHeader("X-User-Id") Long userId) {
        
        viewHistory.setId(null);
        viewHistory.setUserId(userId);

        ViewHistory saved = viewHistoryRepository.save(viewHistory);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<ViewHistory> update(@PathVariable Long id, @RequestBody ViewHistory viewHistory, @RequestHeader("X-User-Id") Long userId) {
        Optional<ViewHistory> existing = viewHistoryRepository.findById(id);
        if (!existing.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        viewHistory.setId(id);
        viewHistory.setUserId(userId);

        ViewHistory updated = viewHistoryRepository.save(viewHistory);

        return ResponseEntity.ok(updated);
    }
}
