package com.example.BankingApp.controllers.web;

import com.example.BankingApp.core.domain.BankAccount;
import com.example.BankingApp.core.services.AddBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddBankAccountController {

    private final AddBankAccountService addBankAccountService;

    @Autowired
    public AddBankAccountController(AddBankAccountService addBankAccountService) {
        this.addBankAccountService = addBankAccountService;
    }

    @GetMapping("/addBankAccount")
    public String showAddBankAccountPage(ModelMap modelMap) {
        modelMap.addAttribute("bankAccount", new BankAccount());
        return "addBankAccount";
    }

    @PostMapping("/addBankAccount")
    public String processAddBankAccountRequest(@ModelAttribute("bankAccount") BankAccount bankAccount, ModelMap modelMap) {
        try {
            addBankAccountService.addBankAccount(bankAccount);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            modelMap.addAttribute("error", e.getMessage());
            return "addBankAccount";
        }
    }
}
