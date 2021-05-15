package org.propagate.server.rest;

import lombok.AllArgsConstructor;
import org.propagate.common.rest.entity.NamespaceRestEntity;
import org.propagate.server.service.NamespaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/namespaces")
@AllArgsConstructor
public class NamespaceResource {
    private final NamespaceService namespaceService;

    @GetMapping
    public ResponseEntity<List<NamespaceRestEntity>> getAllNamespaces() {
        final List<NamespaceRestEntity> ns = namespaceService.getAllNamespaces()
                .stream()
                .map(NamespaceRestEntity::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ns);
    }

    @PostMapping
    public ResponseEntity<NamespaceRestEntity> createNamespace(@RequestBody @Valid NamespaceRestEntity namespaceRestEntity) {
        String namespace = namespaceService.createNamespace(namespaceRestEntity.getNamespace());
        return ResponseEntity.ok(new NamespaceRestEntity(namespace));
    }
}
