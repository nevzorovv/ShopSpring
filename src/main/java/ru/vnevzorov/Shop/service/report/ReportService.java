package ru.vnevzorov.Shop.service.report;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vnevzorov.Shop.model.Order;
import ru.vnevzorov.Shop.model.OrderedProduct;
import ru.vnevzorov.Shop.service.OrderService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    OrderService orderService;

    public String createWeeklyOrderReport(Iterable<Order> orders) {
        String path = "WeeklyOrderReport_" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Лист1");

        List<OrderDataModel> orderList = fillOrderListForLastWeek(orders);

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        List<String> headers = List.of("ID", "Date", "Users login", "First name", " Last name", "Email",
                                                "Products", "Total price", "Payment", "Shipment", "Current status");
/*
        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/mm/yyyy"));*/

        for (int cellNumber = 0; cellNumber < headers.size(); cellNumber++) {
            Cell cell = row.createCell(cellNumber);
            /*if (header.equals("Date")) { //TODO установить формат - Дата
                cell.setCellStyle(cellStyle);
            }*/
            cell.setCellValue(headers.get(cellNumber));
        }

        for (OrderDataModel orderModel : orderList) {
            fillOrderModelData(sheet, ++rowNum, orderModel);
        }

        for (int cellNumber = 0; cellNumber < headers.size(); cellNumber++) {
            sheet.autoSizeColumn(cellNumber);
        }

        try (FileOutputStream out = new FileOutputStream(new File(path))) {
            workbook.write(out);
        } catch (IOException e) {
            e.getMessage();
        }

        return path;
    }

    private List<OrderDataModel> fillOrderListForLastWeek(Iterable<Order> orders) {
        List<OrderDataModel> orderList = new ArrayList<>();
        for (Order order : orders) {
            OrderDataModel orderModel = new OrderDataModel();
            orderModel.setId(order.getId());
            orderModel.setDate(order.getDate());
            orderModel.setLogin(order.getUser().getLogin());
            orderModel.setFirstName(order.getUser().getFirstName());
            orderModel.setLastName(order.getUser().getLastName());
            orderModel.setEmail(order.getUser().getEmail());
            orderModel.setProducts(order.getOrderedProducts());
            orderModel.setTottalPrice(order.getTotalPrice());
            orderModel.setPayment(order.getPayment().getType());
            orderModel.setShipment(order.getShipment().getType());
            orderModel.setStatus(order.getStatus().toString());

            orderList.add(orderModel);
        }
        return orderList;
    }

    private void fillOrderModelData(HSSFSheet sheet, int rowNum, OrderDataModel orderModel) {
        Row row = sheet.createRow(rowNum);

        StringBuilder stringBuilder = new StringBuilder();
        for (OrderedProduct orderedProduct : orderModel.getProducts()) {
            stringBuilder.append(orderedProduct.getProduct().getManufacturer() + " ");
            stringBuilder.append(orderedProduct.getProduct().getModel() + " - ");
            stringBuilder.append(orderedProduct.getQuantity() + "pcs;\r\n");
        }
        row.createCell(0).setCellValue(orderModel.getId());
        row.createCell(1).setCellValue(orderModel.getDate().toString());
        row.createCell(2).setCellValue(orderModel.getLogin());
        row.createCell(3).setCellValue(orderModel.getFirstName());
        row.createCell(4).setCellValue(orderModel.getLastName());
        row.createCell(5).setCellValue(orderModel.getEmail());
        row.createCell(6).setCellValue(stringBuilder.toString());
        row.createCell(7).setCellValue(orderModel.getTotalPrice());
        row.createCell(8).setCellValue(orderModel.getPayment());
        row.createCell(9).setCellValue(orderModel.getShipment());
        row.createCell(10).setCellValue(orderModel.getStatus());
    }

}
