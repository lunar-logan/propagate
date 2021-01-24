package org.propagate.server.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.server.service.FeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/ff")
@AllArgsConstructor
@Validated
@Slf4j
public class FeatureFlagController {
    private final FeatureFlagService service;
    private final ScheduledExecutorService scheduledExecutorService = null;//Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    private final Deque<WeakReference<SseEmitter>> emitterQueue = new ConcurrentLinkedDeque<>();

    //    @PostConstruct
    private void init() {
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            final List<FeatureFlag> ffs = service.getAllFeatureFlags().toList().blockingGet();
            log.info("Emitting: {}", ffs);

            Iterator<WeakReference<SseEmitter>> itr = emitterQueue.iterator();
            while (itr.hasNext()) {
                SseEmitter emitter = itr.next().get();
                if (emitter == null) {
                    itr.remove();
                } else {
                    try {
                        emitter.send(ffs);
                    } catch (IOException e) {
                        log.error("Exception emitting event", e);
                    }
                }
            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }


    @PostMapping
    public ResponseEntity<List<FeatureFlag>> createFeatureFlags(@RequestBody @Valid FeatureFlag flag) {
        return service.createOrUpdate(flag)
                .toList()
                .map(ffs -> {
                    log.info("FeatureFlags: {}", ffs);
                    return ffs;
                })
                .map(ResponseEntity::ok)
                .toObservable()
                .blockingFirst();
    }

    //    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter emitFeatureFlags() {
        final SseEmitter emitter = new SseEmitter();
        emitterQueue.offer(new WeakReference<>(emitter));
        return emitter;
    }

    @GetMapping
    public ResponseEntity<List<FeatureFlag>> getAllFeatureFlags() {
        return service.getAllFeatureFlags()
                .toList()
                .map(ResponseEntity::ok)
                .blockingGet();
    }
}
