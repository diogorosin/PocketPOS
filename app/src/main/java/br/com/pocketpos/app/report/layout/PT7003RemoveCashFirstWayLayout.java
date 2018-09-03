package br.com.pocketpos.app.report.layout;

import android.pt.printer.Printer;

import br.com.pocketpos.core.util.StringUtils;

public class PT7003RemoveCashFirstWayLayout extends RemoveCashFirstWayLayout {


    private final Printer printer;


    public PT7003RemoveCashFirstWayLayout(Printer printer){

        this.printer = printer;

    }


    public void print(){

        printer.setAlignment(1);

        printer.setBold(true);

        printer.printString(getReportName());

        printer.printString("VIA DO(A) GERENTE");

        printer.setBold(false);

        printer.setAlignment(0);

        printer.printString(" ");

        printer.printString("DATA/HORA---------- VALOR-------");

        printer.printString("USUARIO-------------------------");

        printer.printString(
                StringUtils.formatDateTime(getDateTime()) + " " +
                        StringUtils.leftPad(StringUtils.formatCurrency(getValue()), 12, ' '));

        printer.printString(getUserName().toUpperCase());

        printer.printString(" ");

        printer.printString(" ");

    }


}