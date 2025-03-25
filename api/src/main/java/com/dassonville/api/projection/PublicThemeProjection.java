package com.dassonville.api.projection;

import java.time.LocalDate;

public interface PublicThemeProjection {
    Long getId();
    String getName();
    String getDescription();
    LocalDate getCreatedAt();
}
