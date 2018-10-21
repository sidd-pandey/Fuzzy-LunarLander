package lunarlander.game;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import javax.swing.JLabel;

public class LogHandler extends Handler {

	JLabel label;
	JLabel lrLabel, udLabel;
	
	public LogHandler(JLabel logLabel, JLabel lrJLabel, JLabel udJLabel) {
		setFormatter(new SimpleFormatter());
		this.label = logLabel;
		this.lrLabel = lrJLabel;
		this.udLabel = udJLabel;
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
		
			}else if (record.getSourceClassName().equals("com.fuzzylite.Engine")) {
			
				String msg = getFormatter().format(record);
				if (msg.contains("xOutputMove: Aggregated")) {
					String move = msg.split("xOutputMove: Aggregated")[1];
					move = move.contains("[none(") ? move.substring(move.indexOf('(')+1, move.indexOf(')')) : "None";
					lrLabel.setText(move);
				}else if (msg.contains("yOutputMove: Aggregated")) {
					String move = msg.split("yOutputMove: Aggregated")[1];
					move = move.contains("[none(") ? move.substring(move.indexOf('(')+1, move.indexOf(')')) : "None";
					udLabel.setText(move);
				}
				
			}
		}
	}

}
