package com.leon.hfu.util.commandLine;

/**
 * Handles execution of instances. See the #run() method
 * for more information
 *
 * @author		Stefan Hahn
 */
public final class CommandLineExecutor {
	/**
	 * Runs a given CommandLineProgram instance. This function contains
	 * the needed endless loop calling CommandLineProgram#execute() during
	 * every run.
	 *
	 * @param	program		CommandLineProgram instance object
	 * @param	args		Command line arguments
	 */
	public static final void run(CommandLineProgram program, String[] args) {
		try {
			program.initialize(args);

			mainLoop:
			while (true) {
				try {
					program.execute();
					sleep(1000);
				}
				catch (CommandLineException e) {
					switch (e.getType()) {
						case CONTINUE:
							sleep(1000);
							continue mainLoop;
						case BREAK:
							break mainLoop;
						case ERROR:
							throw e.getCause();
					}
				}
			}
		}
		catch (Throwable e) {
			if (e instanceof RuntimeException) {
				throw ((RuntimeException) e);
			}

			e.printStackTrace();
			System.exit(1);
		}
		finally {
			program.finish();
		}
	}

	/**
	 * Sleeps given milli seconds and catches all InterruptedExceptions.
	 *
	 * @param	millis		Time to wait in milli seconds.
	 */
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) { }
	}
}
