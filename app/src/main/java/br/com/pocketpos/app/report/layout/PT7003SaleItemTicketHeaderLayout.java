package br.com.pocketpos.app.report.layout;

import android.pt.printer.Printer;

import br.com.pocketpos.app.report.exception.FailPrinterException;
import br.com.pocketpos.app.report.exception.InParametersErrorPrinterException;
import br.com.pocketpos.app.report.exception.NoPaperPrinterException;
import br.com.pocketpos.app.report.exception.TimeoutPrinterException;
import br.com.pocketpos.app.report.util.PT7003Printer;
import br.com.pocketpos.core.util.StringUtils;

public class PT7003SaleItemTicketHeaderLayout extends SaleItemTicketHeaderLayout {

    private final PT7003Printer printer;

    public PT7003SaleItemTicketHeaderLayout(PT7003Printer printer){

        this.printer = printer;

    }

    public void print() throws
            TimeoutPrinterException,
            InParametersErrorPrinterException,
            FailPrinterException,
            NoPaperPrinterException {

        printer.setAlignment(1);

        printer.setBold(true);

        printer.setFontHeightZoomIn(2);

        printer.printString(getTitle());

        if (getSubtitle() != null && !getSubtitle().isEmpty())

            printer.printString(getSubtitle());

        printer.setFontHeightZoomIn(1);

        printer.setBold(false);

    }

}