package rhinoit.js;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.apache.commons.lang3.SystemUtils;

public class RhinoLogFormatter extends Formatter {

	public String format(LogRecord record) {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat dateformatter = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss.SSS Z");
		sb.append(dateformatter.format(new Date()));
		sb.append(" [").append(Thread.currentThread().getName()).append("]");
		sb.append(" [").append(record.getLevel()).append("] ");
		sb.append(formatMessage(record));
		sb.append(SystemUtils.LINE_SEPARATOR);
		if (record.getThrown() != null) {
			sb.append(record.getThrown());
		}
		return sb.toString();
	}

	public static StringWriter stack(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		pw.close();
		return sw;
	}
}
