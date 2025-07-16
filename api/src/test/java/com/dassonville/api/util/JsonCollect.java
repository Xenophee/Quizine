package com.dassonville.api.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class JsonCollect {

    public static void collectAllStrings(JsonNode node, List<String> collector) {
        if (node.isTextual()) {
            collector.add(node.asText());
        } else if (node.isContainerNode()) {
            for (JsonNode child : node) {
                collectAllStrings(child, collector);
            }
        }
    }
}
