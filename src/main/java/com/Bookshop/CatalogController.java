package com.Bookshop;

import com.Bookshop.models.Clients;
import com.Bookshop.models.PortfolioBooks;
import com.Bookshop.repositories.ClientsRepository;
import com.Bookshop.repositories.PortfolioBooksRepository;
import com.Bookshop.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


@Controller
public class CatalogController {
    @Autowired
    private ImageService imageService;
    private Logger logger = LoggerFactory.getLogger(CatalogController.class);

    private final PortfolioBooksRepository portfolioBooksRepository;
    private final ClientsRepository clientsRepository;

    @Autowired
    public CatalogController(PortfolioBooksRepository portfolioBooksRepository, ClientsRepository clientsRepository) {
        this.portfolioBooksRepository = portfolioBooksRepository;
        this.clientsRepository = clientsRepository;
    }


    @GetMapping("/services")
    public String services(Model model) {
        Iterable<PortfolioBooks> portfolioBooks = portfolioBooksRepository.findAll();
        model.addAttribute("portfolioBooks", portfolioBooks);
        return "services";
    }

    @GetMapping("/request")
    public String request(Model model) {
        Iterable<Clients> clients = clientsRepository.findAll();
        model.addAttribute("clients", clients);
        return "request";
    }

    @GetMapping("/services/add")
    public String servicesAdd(Model model) {
        return "servicesAdd";
    }

    @PostMapping("/services/add")
    public String servicesPostAdd(@RequestParam String name, @RequestParam String author, @RequestParam String description, @RequestParam String year,
                                  @RequestParam String price, @RequestParam String genre,
                                  @RequestParam(name = "image1", required = false) MultipartFile image1, Model model) throws IOException {

        PortfolioBooks PortfolioBooks = new PortfolioBooks(name, author, description, year, price, genre);

        storeFileAndSetItem(PortfolioBooks, image1);


        portfolioBooksRepository.save(PortfolioBooks);
        return "redirect:/services";
    }

    private void storeFileAndSetItem(PortfolioBooks portfolioBooks, MultipartFile multipartFile) throws IOException {
        if (null == multipartFile || multipartFile.isEmpty()) {
            return;
        }
        portfolioBooks.addImage(imageService.store(multipartFile));
    }

    @GetMapping("/services/{id:^[0-9]+$}")
    public String portfolio(@PathVariable(value = "id") long id, Model model) {
        if (!portfolioBooksRepository.existsById(id)) {
            return "redirect:/services";
        }
        Optional<PortfolioBooks> portfolioBooks = portfolioBooksRepository.findById(id);
        ArrayList<PortfolioBooks> res = new ArrayList<>();
        portfolioBooks.ifPresent(res::add);
        model.addAttribute("portfolios", res);
        return "portfolio";
    }

    @GetMapping("/services/{id:^[0-9]+$}/buying")
    public String buying(@PathVariable(value = "id") long id, Model model) {
        if (!portfolioBooksRepository.existsById(id)) {
            return "redirect:/services";
        }
        Optional<PortfolioBooks> portfolioBooks = portfolioBooksRepository.findById(id);
        ArrayList<PortfolioBooks> res = new ArrayList<>();
        portfolioBooks.ifPresent(res::add);
        model.addAttribute("portfolios", res);
        model.addAttribute("bookId", id);
        return "buying";
    }

    @GetMapping("/services/{id:^[0-9]+$}/buying/final")
    public String itog(@PathVariable(value = "id") long id, Model model) {
        if (!portfolioBooksRepository.existsById(id)) {
            return "redirect:/services";
        }
        Optional<PortfolioBooks> portfolioBooks = portfolioBooksRepository.findById(id);
        ArrayList<PortfolioBooks> res = new ArrayList<>();
        portfolioBooks.ifPresent(res::add);
        model.addAttribute("portfolios", res);
        return "final";
    }

    @PostMapping("/services/{id:^[0-9]+$}/buying")
    public String newOrder(@RequestParam String surname, @RequestParam String name,
                           @RequestParam String patronymic, @RequestParam String mobile,
                           @RequestParam String email, @RequestParam String bookId) {
        logger.debug(String.format("%s %s %s %s %s %s", bookId, surname, name, patronymic, mobile, email));
        Optional<PortfolioBooks> book = portfolioBooksRepository.findById(Long.parseLong(bookId));

        Clients clients = new Clients(surname, name, patronymic, mobile, email, book.orElseThrow(IllegalArgumentException::new));
        clientsRepository.save(clients);

        return "redirect:/services/" + bookId + "/buying/final";
    }


}

