package com.Bookshop.repositories;

import com.Bookshop.models.Clients;
import org.springframework.data.repository.CrudRepository;

public interface ClientsRepository extends CrudRepository<Clients, Long> {
}
