package com.cts.record;

import java.time.LocalDate;

public record PolicyDriftDTO(
    String name,
    String email,
    String contentScope,
    LocalDate grantedDate,
    LocalDate expiryDate
) {}