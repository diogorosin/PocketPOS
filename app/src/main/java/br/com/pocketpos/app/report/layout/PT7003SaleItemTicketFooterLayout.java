package br.com.pocketpos.app.report.layout;

import android.pt.printer.Printer;

public class PT7003SaleItemTicketFooterLayout extends SaleItemTicketFooterLayout {

    private final Printer printer;

    public PT7003SaleItemTicketFooterLayout(Printer printer){

        this.printer = printer;

    }

    public void print(){

        printer.setFontSize(1);

        if (getNote() != null && !getNote().isEmpty()) {

            printer.printString(" ");

            printer.printString(getNote());

        }

        if (getFooter() != null && !getFooter().isEmpty()) {

            printer.printString(" ");

            printer.printString(getFooter());

        }

        printer.setFontSize(0);

        printer.printString(" ");

        printer.printString("--------------------------------");

        printer.printString(" ");

    }

}