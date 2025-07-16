package com.dassonville.api.util;

import com.dassonville.api.projection.IdAndNameProjection;

public class TestIdAndNameProjection implements IdAndNameProjection {
    public Long getId() { return 1L; }
    public String getName() { return "Un nom"; }
}
