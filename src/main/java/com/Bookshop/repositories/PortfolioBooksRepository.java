package com.Bookshop.repositories;

import com.Bookshop.models.PortfolioBooks;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PortfolioBooksRepository  extends CrudRepository<PortfolioBooks, Long> {
    List<PortfolioBooks> getPortfolioBooksByNameContainingIgnoreCase(@NonNull String name);
}

