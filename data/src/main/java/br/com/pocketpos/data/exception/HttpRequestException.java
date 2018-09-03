package br.com.pocketpos.data.exception;


import br.com.pocketpos.data.util.Messaging;

public class HttpRequestException extends Exception implements Messaging {

    public HttpRequestException(){

        super("Sem conexão com a internet ou servidor fora de operação.");

    }

    public String[] getMessages(){

        return new String[]{getMessage()};

    }

}