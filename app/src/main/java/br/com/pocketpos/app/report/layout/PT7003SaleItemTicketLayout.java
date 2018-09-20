package br.com.pocketpos.app.report.layout;

import android.pt.printer.Printer;

import br.com.pocketpos.core.util.StringUtils;

public class PT7003SaleItemTicketLayout extends SaleItemTicketLayout {

    private final Printer printer;

    public PT7003SaleItemTicketLayout(Printer printer){

        this.printer = printer;

    }

    public void print(){

        printer.setFontSize(1);

        printer.printString(" ");

        printer.printString("PDV--------- VENDA-- ITM CUPOM--");

        printer.printString(
                StringUtils.rightPad(getDeviceAlias().toString(), 12, ' ') + ' ' +
                        StringUtils.leftPad(getSale().toString(), 7, '0') + ' ' +
                        StringUtils.leftPad(getItem().toString(), 3, '0') + ' ' +
                        StringUtils.leftPad(getTicket().toString(), 3, '0') + '/' +
                        StringUtils.leftPad(getOf().toString(), 3, '0'));

        printer.printString("OPERADOR------------ DATA/HORA--");

        printer.printString(
                StringUtils.rightPad(getUserName(), 20, ' ') + ' ' +
                        StringUtils.formatShortDateTime(getDateTime())

        );

        printer.printString(" ");

        printer.setFontSize(0);

        printer.setAlignment(1);

        printer.setBold(true);

        printer.setFontHeightZoomIn(2);

        printer.printString(getDenomination());

//      printer.printString(StringUtils.formatQuantity(getQuantity()) + getMeasureUnit().toUpperCase());

        printer.setFontHeightZoomIn(1);

        printer.setBold(false);

    }

}