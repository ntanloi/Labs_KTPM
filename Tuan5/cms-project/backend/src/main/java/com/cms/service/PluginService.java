package com.cms.service;

import com.cms.plugin.CmsPlugin;
import com.cms.plugin.PluginRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PluginService {

    private final PluginRegistry pluginRegistry;

    public List<Map<String, String>> getAllPlugins() {
        return pluginRegistry.getAllPlugins().stream()
            .map(p -> Map.of(
                "name", p.getName(),
                "version", p.getVersion()
            ))
            .collect(Collectors.toList());
    }
}
