package com.example.BankingApp.controllers.web;

import com.example.BankingApp.core.services.RemoveBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RemoveBankAccountController {

    private final RemoveBankAccountService removeBankAccountService;

    @Autowired
    public RemoveBankAccountController(RemoveBankAccountService removeBankAccountService) {
        this.removeBankAccountService = removeBankAccountService;
    }

    @GetMapping("/removeBankAccount")
    public String showRemoveBankAccountPage() {
        return "removeBankAccount";
    }

    @PostMapping("/removeBankAccount")
    public String processRemoveBankAccountRequest(@RequestParam("accountId") Long accountId, ModelMap modelMap) {
        try {
            removeBankAccountService.removeBankAccount(accountId);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            modelMap.addAttribute("error", e.getMessage());
            return "removeBankAccount";
        }
    }
}