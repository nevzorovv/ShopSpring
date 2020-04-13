package ru.vnevzorov.Shop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vnevzorov.Shop.model.Order;
import ru.vnevzorov.Shop.model.user.Admin;
import ru.vnevzorov.Shop.service.email.EmailService;
import ru.vnevzorov.Shop.service.email.Message;
import ru.vnevzorov.Shop.service.report.ReportService;
import ru.vnevzorov.Shop.service.user.AdminService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Service
public class SchedulerService {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    OrderService orderService;

    @Autowired
    ReportService reportService;

    @Autowired
    EmailService emailService;

    @Autowired
    AdminService adminService;

    //@Scheduled(fixedDelay = 100000 /*cron = "0 0 0 * * MON"*/)
    @Transactional
    public void weeklyOrderReport() {
        log.info("START SCHEDULED TASK: createWeeklyOrdersReport()");
        LocalDate previousMonday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));

        Iterable<Order> ordersSinceLastMonday = orderService.getAllByDateGreaterThanEqual(previousMonday);
        String path = reportService.createWeeklyOrderReport(ordersSinceLastMonday);

        Iterable<Admin> admins = adminService.getAllWithSendReportTrue();
        String subject = "Weekly order report " + LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        for (Admin admin : admins) {
            String to = admin.getEmail();
            String text = "Dear " + admin.getFirstName() + ",\nNew weekly order report in attachment";

            emailService.sendMessage(new Message(to, subject, text, path));
        }
    }
}
