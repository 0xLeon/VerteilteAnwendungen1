package com.leon.hfu.util.commandLine;

/**
 * Created by Stefan on 22.03.2015.
 */
public final class CommandLineExecutor {
	/**
	 *
	 * @param	program
	 * @param	args
	 */
	public static final void run(CommandLineProgram program, String[] args) {
		try {
			program.initialize(args);

			mainLoop:
			while (true) {
				try {
					program.execute();
				}
				catch (CommandLineException e) {
					switch (e.getType()) {
						case CONTINUE:
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
}
