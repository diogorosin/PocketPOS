package br.com.pocketpos.app.report.exception;

import br.com.pocketpos.data.util.Messaging;

public class FailPrinterException extends Exception implements Messaging {

    public FailPrinterException(String command){

        super("Falha na impressora. Comando: " + command);

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}