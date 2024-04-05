package com.example.BankingApp.controllers.web;

import com.example.BankingApp.core.services.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DepositController {

    private final DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @GetMapping("/deposit")
    public String showDepositForm() {
        return "deposit";
    }

    @PostMapping("/deposit")
    public String processDepositRequest(@RequestParam("accountId") Long accountId,
                                        @RequestParam("depositAmount") Double depositAmount,
                                        ModelMap modelMap) {
        try {
            depositService.depositToAccount(accountId, depositAmount);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            modelMap.addAttribute("error", e.getMessage());
            return "deposit";
        }
    }
}