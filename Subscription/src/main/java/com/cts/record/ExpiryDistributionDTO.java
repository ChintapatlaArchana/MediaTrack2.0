package com.cts.record;

public record ExpiryDistributionDTO(
    long lessThan7Days,
    long sevenTo30Days,
    long moreThan30Days
) {}