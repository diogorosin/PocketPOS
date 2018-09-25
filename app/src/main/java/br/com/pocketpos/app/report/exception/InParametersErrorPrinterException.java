package br.com.pocketpos.app.report.exception;

import br.com.pocketpos.data.util.Messaging;

public class InParametersErrorPrinterException extends Exception implements Messaging {

    public InParametersErrorPrinterException(String command){

        super("Erro no(s) parâmetro(s) de entrada. Comando: " + command);

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}