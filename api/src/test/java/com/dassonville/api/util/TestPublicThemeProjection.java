package com.dassonville.api.util;

import com.dassonville.api.projection.PublicThemeProjection;

import java.time.LocalDateTime;

public class TestPublicThemeProjection implements PublicThemeProjection {
    public Long getId() { return 1L; }
    public String getName() { return "Un thème"; }
    public String getDescription() { return "Une description de thème"; }
    public LocalDateTime getCreatedAt() { return LocalDateTime.now(); }
}
