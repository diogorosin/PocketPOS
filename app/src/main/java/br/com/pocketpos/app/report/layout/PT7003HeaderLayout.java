package br.com.pocketpos.app.report.layout;

import android.pt.printer.Printer;

import br.com.pocketpos.core.util.StringUtils;

public class PT7003HeaderLayout extends HeaderLayout {


    private final Printer printer;


    public PT7003HeaderLayout(Printer printer){

        this.printer = printer;

    }


    public void print(){

        printer.setAlignment(1);

        printer.setBold(true);

        printer.setFontHeightZoomIn(2);

        printer.printString(getTitle());

        if (getSubtitle() != null && !getSubtitle().isEmpty())

            printer.printString(getSubtitle());

        printer.setFontHeightZoomIn(1);

        printer.setBold(false);

        printer.printString(" ");

        printer.printString(StringUtils.formatDateTime(getDateTime()));

        printer.printString(getAlias());

        printer.printString(" ");

    }


}