package lunarlander.game;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import javax.swing.JLabel;

public class LogHandler extends Handler {

	JLabel label;
	
	public LogHandler(JLabel logLabel) {
		setFormatter(new SimpleFormatter());
		this.label = logLabel;
	}
	
	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(LogRecord record) {
		synchronized (label) {
			if (record.getSourceClassName().equals("com.fuzzylite.rule.Rule")) {
				label.setText(getFormatter().format(record).split("trigger :")[1]);
			}
		}
	}

}
