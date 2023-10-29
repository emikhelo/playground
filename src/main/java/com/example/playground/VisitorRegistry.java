package com.example.playground;

import com.example.playground.kid.Kid;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class VisitorRegistry {
    // This can be elaborated to store whole Kid records. But we will opt for privacy :)
    private Set<String> uniqueVisitorTickets = new HashSet();
    private LocalDate visitorDate;
    private ApplicationConfig applicationConfig;
    public void registerVisitor(Kid kid) {
        getVisitorsForToday();
        uniqueVisitorTickets.add(kid.getTicketNumber());
    }

    /*
     * Note: if kid stays in a PlaySite past midnight, we don't count him as visitor for the next day!
     */
    public Set<String> getVisitorsForToday() {
        LocalDate today = ApplicationConfig.getInstance().getToday();
        if (!today.equals(this.visitorDate)) {
            visitorDate = today;
            uniqueVisitorTickets = new HashSet();
        }
        return uniqueVisitorTickets;
    }
}