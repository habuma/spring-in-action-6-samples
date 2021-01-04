package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.data.service.OrderAdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
  
  private OrderAdminService adminService;

  public AdminController(OrderAdminService adminService) {
    this.adminService = adminService;
  }
  
  @GetMapping
  public String showAdminPage() {
    return "admin";
  }

  @PostMapping("/deleteOrders")
  public String deleteAllOrders() {
    adminService.deleteAllOrders();
    return "redirect:/admin";
  }

}
