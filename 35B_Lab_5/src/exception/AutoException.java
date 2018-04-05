package exception;

import debug.Debuggable;
import util.FileIO;

public class AutoException extends Exception implements Debuggable {
	/////////////////////////////////////////
	// CONSTRUCTORS
	private int errorno;
	private String errormsg;
	private FileIO FIO = new FileIO();

	/////////////////////////////////////////
	// CONSTRUCTORS
	public AutoException() {
		super();
	}

	public AutoException(String errormsg) {
		super();
		this.errormsg = errormsg;
	}

	public AutoException(int errorno) {
		super();
		this.errorno = errorno;
	}

	public AutoException(int errorno, String errormsg) {
		super();
		this.errorno = errorno;
		this.errormsg = errormsg;
	}
	
	/////////////////////////////////////////
	// GETTERS / SETTERS
	public int getErrorno() {
		return errorno;
	}

	public void setErrorno(int errorno) {
		this.errorno = errorno;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	/////////////////////////////////////////
	// METHODS
	public void log() {
		FIO.errorLog(this.errorno, this.errormsg);
	}
	
	public void printProblem() {
		System.out.println("\n\nERROR # " + this.errorno + ": " + this.errormsg); 
	}
	
	/////////////////////////////////////////
	// FIXER
	public void FixErr(int errNum) {

		this.errorno = errNum;
		
		FixFileRead_0_100 Fx0_100 = new FixFileRead_0_100();
		
		switch (errNum) {
		case 1:
			this.errormsg = ("INCORRECT NUMBER OF METADATA ITEMS");
			this.printProblem();
			this.log();
			Fx0_100.fix1();
			break;
		case 2:
			this.errormsg = ("ODD NUMBER OF OPTIONS");
			this.printProblem();
			this.log();
			Fx0_100.fix2();	
			break;
		case 3:
			this.errormsg = ("OPTIONSETS DONT MATCH METADATA");
			this.printProblem();
			this.log();
			Fx0_100.fix3();
			break;
		case 4:
			this.errormsg = ("OPTIONSET NAMES DONT MATCH METADATA");
			this.printProblem();
			this.log();
		//	Fx0_100.fix4();	
			break;
		case 5:
			this.errormsg = ("OPTIONS NOT SUCCESSFULLY CONFIGURED");
			this.printProblem();
			this.log();
		//	Fx0_100.fix5();
			break;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
