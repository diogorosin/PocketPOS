package br.com.pocketpos.app.report.exception;

import br.com.pocketpos.data.util.Messaging;

public class TimeoutPrinterException extends Exception implements Messaging{

    public TimeoutPrinterException(){

        super("O tempo de conexão com a impressora foi esgotado.");

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}