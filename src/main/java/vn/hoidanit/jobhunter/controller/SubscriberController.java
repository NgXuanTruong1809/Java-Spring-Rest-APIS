package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.service.SubscriberService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribers")
    @ApiMessage("Create a subscriber")
    public ResponseEntity<Subscriber> create(@Valid @RequestBody Subscriber rSubscriber) throws IdInvalidException {
        if (this.subscriberService.isExistsByEmail(rSubscriber.getEmail())) {
            throw new IdInvalidException("Email đã tồn tại");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.subscriberService.create(rSubscriber));
    }

    @PutMapping("/subscribers")
    public ResponseEntity<Subscriber> update(@RequestBody Subscriber rSubscriber)
            throws IdInvalidException {
        Subscriber subscriber = this.subscriberService.findById(rSubscriber.getId());
        if (subscriber == null) {
            throw new IdInvalidException("Id không tồn tại");
        }

        return ResponseEntity.status(HttpStatus.OK).body(this.subscriberService.update(subscriber, rSubscriber));
    }

}
