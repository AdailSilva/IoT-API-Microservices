package com.adailsilva.iot.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adailsilva.iot.api.entities.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

	Worker findByName(String name);

}
