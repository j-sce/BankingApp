package com.example.BankingApp.controllers.web;

import com.example.BankingApp.core.services.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WithdrawController {

    private final WithdrawService withdrawService;

    @Autowired
    public WithdrawController(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    @GetMapping("/withdraw")
    public String showWithdrawForm() {
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String processWithdrawRequest(@RequestParam("accountId") Long accountId,
                                         @RequestParam("withdrawAmount") Double withdrawAmount,
                                         ModelMap modelMap) {
        try {
            if (withdrawService.withdrawFromAccount(accountId, withdrawAmount)) {
                return "redirect:/";
            } else {
                modelMap.addAttribute("error", "Insufficient balance for withdrawal.");
                return "withdraw";
            }
        } catch (IllegalArgumentException e) {
            modelMap.addAttribute("error", e.getMessage());
            return "withdraw";
        }
    }
}