package br.com.pocketpos.app.report.util;

import android.pt.printer.Printer;
import android.util.Log;

import br.com.pocketpos.app.report.exception.FailPrinterException;
import br.com.pocketpos.app.report.exception.InParametersErrorPrinterException;
import br.com.pocketpos.app.report.exception.NoPaperPrinterException;
import br.com.pocketpos.app.report.exception.TimeoutPrinterException;

public class PT7003Printer{

    private Printer printer;

    public PT7003Printer(){

        printer = new Printer();

    }

    public void open() throws FailPrinterException {

        switch (printer.open()){

            case -1:

                throw new FailPrinterException("OPEN");

        }

    }

    public int status(){

        return printer.queState();

    }

    public void init() throws TimeoutPrinterException, FailPrinterException {

        switch (printer.init()){

            case -1:

                throw new FailPrinterException("INIT");

            case -2:

                throw new TimeoutPrinterException();

        }

    }

    public void close() throws FailPrinterException {

        switch (printer.close()){

            case -1:

                throw new FailPrinterException("CLOSE");

        }

    }

    public void printString(String content) throws
            FailPrinterException,
            TimeoutPrinterException,
            InParametersErrorPrinterException,
            NoPaperPrinterException {

        switch (printer.printString(content)){

            case 9:

                throw new NoPaperPrinterException();

            case -1:

                throw new FailPrinterException("PRINTSTRING");

            case -2:

                throw new TimeoutPrinterException();

            case -3:

                throw new InParametersErrorPrinterException("PRINTSTRING");

        }

    }

    public void setFontSize(int fontSize) throws
            FailPrinterException,
            TimeoutPrinterException,
            InParametersErrorPrinterException {

        switch (printer.setFontSize(fontSize)){

            case -1:

                throw new FailPrinterException("SETFONTSIZE");

            case -2:

                throw new TimeoutPrinterException();

            case -3:

                throw new InParametersErrorPrinterException("SETFONTSIZE");

        }

    }

    public void setAlignment(int alignment) throws
            FailPrinterException,
            TimeoutPrinterException,
            InParametersErrorPrinterException {

        switch (printer.setAlignment(alignment)){

            case -1:

                throw new FailPrinterException("SETALIGNMENT");

            case -2:

                throw new TimeoutPrinterException();

            case -3:

                throw new InParametersErrorPrinterException("SETALIGNMENT");

        }

    }

    public void setBold(boolean bold) throws
            FailPrinterException,
            TimeoutPrinterException,
            InParametersErrorPrinterException {

        switch (printer.setBold(bold)){

            case -1:

                throw new FailPrinterException("SETBOLD");

            case -2:

                throw new TimeoutPrinterException();

            case -3:

                throw new InParametersErrorPrinterException("SETBOLD");

        }

    }

    public void setFontHeightZoomIn(int fontHeightZoomIn)throws
            FailPrinterException,
            TimeoutPrinterException,
            InParametersErrorPrinterException {

        switch (printer.setFontHeightZoomIn(fontHeightZoomIn)){

            case -1:

                throw new FailPrinterException("SETFONTHEIGHTZOOMIN");

            case -2:

                throw new TimeoutPrinterException();

            case -3:

                throw new InParametersErrorPrinterException("SETFONTHEIGHTZOOMIN");

        }

    }

}
