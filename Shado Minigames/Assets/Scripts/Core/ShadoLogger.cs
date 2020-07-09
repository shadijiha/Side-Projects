/**
 *
 */

using System;
using System.Collections.Generic;
using System.IO;

namespace Core
{
    public class ShadoLogger
    {
        private LogLevel logLevel;
        private TextWriter stream;

        private IList<LogMessage> errors;
        private IList<LogMessage> warnings;
        private IList<LogMessage> infos;

        public ShadoLogger(LogLevel logLevel) {
            this.logLevel = logLevel;
            this.stream = Console.Out;
            this.errors = new List<LogMessage>();
            this.warnings = new List<LogMessage>();
            this.infos = new List<LogMessage>();
        }

        public void redirectStream(TextWriter new_stream) {
            stream = new_stream;
        }

        public void error(string format, params object[] args) {
            string msg = String.Format(format, args);

            LogMessage m = new LogMessage(msg, DateTime.Now, LogLevel.ERROR, null);

            errors.Add(m);
            printMessage(m);
        }

        public void error(Exception e)	{

            LogMessage m = new LogMessage(e.Message,
                DateTime.Now,
                LogLevel.ERROR,
                e);

            errors.Add(m);
            printMessage(m);
        }

        public void warn(string format, params object[] args)	{

            string msg = String.Format(format, args);

            LogMessage m = new LogMessage(msg, DateTime.Now, LogLevel.WARNING, null);

            warnings.Add(m);
            printMessage(m);
        }

        public void warn(Exception e)	{

            LogMessage m = new LogMessage(e.Message,
                DateTime.Now,
                LogLevel.WARNING,
                e);

            warnings.Add(m);
            printMessage(m);
        }

        public void log(string format, params object[] args)	{

            string msg = String.Format(format, args);

            LogMessage m = new LogMessage(msg, DateTime.Now, LogLevel.INFO, null);

            infos.Add(m);
            printMessage(m);
        }

        public void log(Exception e)	{

            LogMessage m = new LogMessage(e.Message,
                DateTime.Now,
                LogLevel.INFO,
                e);

            infos.Add(m);
            printMessage(m);
        }

        public IList<LogMessage> allErrors()	{
            return new List<LogMessage>(errors);
        }

        public IList<LogMessage> allWarnings()	{
            return new List<LogMessage>(warnings);
        }

        public IList<LogMessage> allInfos()	{
            return new List<LogMessage>(infos);
        }

        private void printMessage(LogMessage m)	{

            String o = String.Format("%s \t[%s] \t%s.", m.time.ToString(), m.type.ToString("G"), m.message);

            if (m.cause != null)
                o += " Caused by: " + m.cause.ToString();

            stream.WriteLine(o);
        }

        public class LogMessage
        {
            public string message { get; }
            public DateTime time { get; }
            public LogLevel type { get; }
            public Exception cause { get; }

            public LogMessage(string message, DateTime time, LogLevel type, Exception cause) {
                this.message = message;
                this.time = time;
                this.type = type;
                this.cause = cause;
            }
        }

        public enum LogLevel
        {
          ERROR, WARNING, INFO, ALL
        }
    }
}