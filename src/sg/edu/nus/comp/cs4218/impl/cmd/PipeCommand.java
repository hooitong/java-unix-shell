package sg.edu.nus.comp.cs4218.impl.cmd;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.Shell;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class PipeCommand implements Command {
	
	static final String WHITESPACE = "\\s+";
	static final char PIPE = '|';
	static final int ZERO = 0;
	static final int ONE = 0;
	String app;
	String cmdline, inputStreamS, outputStreamS;
	ArrayList<String> argsList;
	ArrayList<CallCommand> cmdList = new ArrayList<CallCommand>();
	Boolean error;
	String errorMsg;
	
	public PipeCommand(String cmdLine) throws ShellException{
		this.argsList = new ArrayList<String>();
		this.cmdline = cmdLine;
		separateIntoIndividualCommands(cmdLine);
	}
	private void separateIntoIndividualCommands(String cmdLine) {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < cmdLine.length(); i++) {
			if(cmdLine.charAt(i) != PIPE){
				sb.append(cmdLine.charAt(i));
			}else{
				this.argsList.add(sb.toString().trim());
				sb.setLength(ZERO);
			}
		}
		if(sb.length() != ZERO){
			this.argsList.add(sb.toString().trim());
		}
	}
	public static void main(String[] args) throws ShellException, AbstractApplicationException {
		String temp = "head file3.txt | tail -n 5";
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();;
		ShellImpl shell = new ShellImpl();
		shell.parseAndEvaluate(temp, stdout);
		System.out.println(stdout);
		
	}
    /**
     * Evaluates command using data provided through stdin stream. Write result
     * to stdout stream.
     */
    public void evaluate(InputStream stdin, OutputStream stdout) throws AbstractApplicationException, ShellException {
    	ByteArrayOutputStream outgoingPipe = new ByteArrayOutputStream();
		for (int i = 0; i < this.cmdList.size(); i++) {
			if(i == 0){
				cmdList.get(i).evaluate(stdin, outgoingPipe);
			}else if(i < this.cmdList.size() - 1){
				InputStream incomingPipe = new ByteArrayInputStream(outgoingPipe.toByteArray());
				outgoingPipe.reset();
				cmdList.get(i).evaluate(incomingPipe, outgoingPipe);
			}else{
				InputStream incomingPipe = new ByteArrayInputStream(outgoingPipe.toByteArray());
				cmdList.get(i).evaluate(incomingPipe, stdout);
			}			
		}
    }

    /**
     * Terminates current execution of the command (unused for now)
     */
    @Override
    public void terminate() {
        // TODO Auto-generated method stub
    }

    public void parse() throws ShellException{
		for (int i = 0; i < argsList.size(); i++) {
			ShellImpl shell = new ShellImpl();
			Command command = shell.parse(this.argsList.get(i));
			cmdList.add((CallCommand)command);
			
		}
    }
}
