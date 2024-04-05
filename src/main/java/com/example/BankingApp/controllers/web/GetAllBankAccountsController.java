package com.example.BankingApp.controllers.web;

import com.example.BankingApp.core.domain.BankAccount;
import com.example.BankingApp.core.services.GetAllBankAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GetAllBankAccountsController {

    private final GetAllBankAccountsService getAllBankAccountsService;

    @Autowired
    public GetAllBankAccountsController(GetAllBankAccountsService getAllBankAccountsService) {
        this.getAllBankAccountsService = getAllBankAccountsService;
    }

    @GetMapping("/getAllBankAccounts")
    public String getAllBankAccounts(ModelMap modelMap) {
        List<BankAccount> bankAccounts = getAllBankAccountsService.getAllBankAccounts();
        modelMap.addAttribute("bankAccounts", bankAccounts);
        return "getAllBankAccounts";
    }
}