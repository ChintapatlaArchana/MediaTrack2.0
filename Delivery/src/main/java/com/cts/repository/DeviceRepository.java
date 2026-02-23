package com.cts.repository;

import com.cts.model.DRMEvent;
import com.cts.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {


}

