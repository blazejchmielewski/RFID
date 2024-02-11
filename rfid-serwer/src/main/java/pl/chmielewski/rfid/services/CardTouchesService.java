package pl.chmielewski.rfid.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.chmielewski.rfid.entities.CardTouches;
import pl.chmielewski.rfid.repositories.CardTouchesRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CardTouchesService {
    private CardTouchesRepo cardTouchesRepo;
    private EmployeeService employeeService;

    @Autowired

    public CardTouchesService(CardTouchesRepo cardTouchesRepo, EmployeeService
            employeeService) {
        this.cardTouchesRepo = cardTouchesRepo;
        this.employeeService = employeeService;
    }

    public void createNewTouch(String code) {
        CardTouches cardTouches = new CardTouches();
        cardTouches.setCode(code);

        cardTouches.setEmployeeId(String.valueOf(employeeService.getEmployeeIdByCode(code)));
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        cardTouches.setDateTime(formattedDateTime);
        cardTouchesRepo.save(cardTouches);
    }

    public List<CardTouches> getCardTouchesList() {
        return cardTouchesRepo.findAll();
    }
}
