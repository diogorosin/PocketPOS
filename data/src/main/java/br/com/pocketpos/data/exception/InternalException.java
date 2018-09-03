package br.com.pocketpos.data.exception;


import br.com.pocketpos.data.util.Messaging;

public class InternalException extends Exception implements Messaging {

    public InternalException(){

        super("Erro interno de processamento.");

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}