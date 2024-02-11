package pl.chmielewski.rfid.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.chmielewski.rfid.entities.Employee;
import pl.chmielewski.rfid.repositories.EmployeeRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    private List<String> codes = new ArrayList<>();

    public EmployeeService() {
        codes.add("Brak");
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepo.findById(id).orElse(null);
    }

    public Long getEmployeeIdByCode(String code) {
        Employee byCode = employeeRepo.findByCode(code).orElse(null);
        assert byCode != null;
        return byCode.getId();
    }

    public void addNewEmployee(Employee employee) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        employee.setCreationDate(formattedDateTime);
        employeeRepo.save(employee);
    }

    public void addNewCode(String code) {
        codes.add(code);
    }

    public List<String> getAllCodes() {
        return codes.stream().distinct().collect(Collectors.toList());
    }

    public void edit(Employee employee) {
        employeeRepo.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }
}

