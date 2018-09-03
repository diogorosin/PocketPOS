package br.com.pocketpos.data.exception;


import br.com.pocketpos.data.util.Messaging;

public class CannotInitializeDatabaseException extends Exception implements Messaging {

    public CannotInitializeDatabaseException(){

        super("Não foi possível iniciar o banco de dados.");

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}