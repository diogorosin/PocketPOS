package br.com.pocketpos.app.report.exception;

import br.com.pocketpos.data.util.Messaging;

public class NoPaperPrinterException extends Exception implements Messaging {

    public NoPaperPrinterException(){

        super("Impressora sem papel.");

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}