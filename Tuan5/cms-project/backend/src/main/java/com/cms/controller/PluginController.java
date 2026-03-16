package com.cms.controller;

import com.cms.service.PluginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plugins")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PluginController {

    private final PluginService pluginService;

    @GetMapping
    public ResponseEntity<List<Map<String, String>>> getAllPlugins() {
        return ResponseEntity.ok(pluginService.getAllPlugins());
    }
}
