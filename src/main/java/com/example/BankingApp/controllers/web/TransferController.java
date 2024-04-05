package com.example.BankingApp.controllers.web;

import com.example.BankingApp.core.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/transfer")
    public String showTransferForm() {
        return "transfer";
    }

    @PostMapping("/transfer")
    public String processTransferRequest(@RequestParam("fromAccountId") Long fromAccountId,
                                         @RequestParam("toAccountId") Long toAccountId,
                                         @RequestParam("transferAmount") Double transferAmount,
                                         ModelMap modelMap) {
        try {
            if (transferService.transferFunds(fromAccountId, toAccountId, transferAmount)) {
                return "redirect:/";
            } else {
                modelMap.addAttribute("error", "Transfer failed. Please check your inputs.");
                return "transfer";
            }
        } catch (IllegalArgumentException e) {
            modelMap.addAttribute("error", e.getMessage());
            return "transfer";
        }
    }
}