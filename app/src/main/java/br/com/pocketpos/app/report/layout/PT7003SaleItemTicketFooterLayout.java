package br.com.pocketpos.app.report.layout;

import br.com.pocketpos.app.report.exception.FailPrinterException;
import br.com.pocketpos.app.report.exception.InParametersErrorPrinterException;
import br.com.pocketpos.app.report.exception.NoPaperPrinterException;
import br.com.pocketpos.app.report.exception.TimeoutPrinterException;
import br.com.pocketpos.app.report.util.PT7003Printer;

public class PT7003SaleItemTicketFooterLayout extends SaleItemTicketFooterLayout {

    private final PT7003Printer printer;

    public PT7003SaleItemTicketFooterLayout(PT7003Printer printer){

        this.printer = printer;

    }

    public void print() throws
            TimeoutPrinterException,
            InParametersErrorPrinterException,
            FailPrinterException,
            NoPaperPrinterException {

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