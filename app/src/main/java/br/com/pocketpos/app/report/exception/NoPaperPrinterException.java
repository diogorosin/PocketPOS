package br.com.pocketpos.app.report.exception;

import br.com.pocketpos.data.util.Messaging;

public class NoPaperPrinterException extends Exception implements Messaging {

    public NoPaperPrinterException(){

        super("<b>A impressora está sem papel</b>. Descarte o último cupom impresso pela metade, subistitua a bobina e tente novamente.");

    }

    public String[] getMessages(){

        return new String[]{ getMessage() };

    }

}