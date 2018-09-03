package br.com.pocketpos.app.report.layout;

import android.pt.printer.Printer;

import br.com.pocketpos.core.util.StringUtils;

public class PT7003CloseCashSummaryLayout extends CloseCashSummaryLayout {


    private final Printer printer;


    public PT7003CloseCashSummaryLayout(Printer printer){

        this.printer = printer;

    }


    public void print(){

        printer.printString("  " +
                StringUtils.formatShortDateTime(getDateTime()) + " " + getPayment() + " " +
                        StringUtils.leftPad(StringUtils.formatCurrency(  getType().equals("S") ? getValue() * -1 : getValue()), 14, ' '));

        printer.printString("  " +
                StringUtils.rightPad(getUserName(), 30, ' '));

    }


}