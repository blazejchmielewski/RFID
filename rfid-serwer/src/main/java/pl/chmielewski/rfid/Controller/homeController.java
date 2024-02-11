package pl.chmielewski.rfid.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.chmielewski.rfid.entities.Employee;
import pl.chmielewski.rfid.services.CardTouchesService;
import pl.chmielewski.rfid.services.EmployeeService;

@Controller
public class homeController {
    private EmployeeService employeeService;
    private CardTouchesService cardTouchesService;

    @Autowired
    public homeController(EmployeeService employeeService, CardTouchesService
            cardTouchesService) {
        this.employeeService = employeeService;
        this.cardTouchesService = cardTouchesService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("getCardTouchesList", cardTouchesService.getCardTouchesList());
        return "home";
    }

    public void sendData(String lastReceivedRFID) {
        employeeService.addNewCode(lastReceivedRFID);
        cardTouchesService.createNewTouch(lastReceivedRFID);
    }

    @PostMapping("/newEmployee")
    public String newEmployee(@ModelAttribute Employee employee) {
        employeeService.addNewEmployee(employee);
        return "redirect:/employee";
    }

    @GetMapping("/addEmployee")
    public String getEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    @GetMapping("/employee")
    public String getEmployeeList(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee";
    }

    @GetMapping("/edit/{id}")
    public String getEmployeeEdit(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        model.addAttribute("Codes", employeeService.getAllCodes());
        return "edit";
    }

    @PostMapping("/edit")
    public String editEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        employeeService.edit(employee);
        return "redirect:/employee";
    }
}

