package br.com.pocketpos.data.exception;


import br.com.pocketpos.data.util.Messaging;

public class ValidationException extends Exception implements Messaging {

    public ValidationException(String message){

        super(message);

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}