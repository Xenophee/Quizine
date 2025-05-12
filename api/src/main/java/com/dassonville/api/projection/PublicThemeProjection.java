package com.dassonville.api.projection;

import java.time.LocalDateTime;

public interface PublicThemeProjection {
    Long getId();
    String getName();
    String getDescription();
    LocalDateTime getCreatedAt();
}
