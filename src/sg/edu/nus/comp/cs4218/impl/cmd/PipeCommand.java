package sg.edu.nus.comp.cs4218.impl.cmd;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

import java.io.InputStream;
import java.io.OutputStream;

public class PipeCommand implements Command {

    public PipeCommand(CallCommand incoming, CallCommand outgoing) {
    	if(incoming == null || outgoing == null){
    		return;
    	}
    }

    /**
     * Evaluates command using data provided through stdin stream. Write result
     * to stdout stream.
     */
    public void evaluate(InputStream stdin, OutputStream stdout) throws AbstractApplicationException, ShellException {
        // TODO Auto-generated method stub
    }

    /**
     * Terminates current execution of the command (unused for now)
     */
    @Override
    public void terminate() {
        // TODO Auto-generated method stub
    }

    public void parse() {

    }
}
