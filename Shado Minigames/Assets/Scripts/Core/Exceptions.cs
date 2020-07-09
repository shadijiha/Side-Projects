/**
 *
 */

using System;

namespace Core
{
    public class IllegalFileNameException : Exception
    {
        private string msg;

        public IllegalFileNameException(string msg)
            : base(msg) {
            this.msg = msg;
        }

        public IllegalFileNameException()
            : this("") { }

        public string getMessage() {
            return msg;
        }
    }

    public class LanguagePackSyntaxError : Exception
    {
        private string msg;

        public LanguagePackSyntaxError(string msg)
            : base(msg) {
            this.msg = msg;
        }

        public LanguagePackSyntaxError()
            : this("") { }

        public string getMessage() {
            return msg;
        }
    }

    public class InvalidAttributeException : Exception
    {
        private string msg;

        public InvalidAttributeException(string msg)
            : base(msg) {
            this.msg = msg;
        }

        public InvalidAttributeException()
            : this("Desired attribute was not found") { }

        public string getMessage() {
            return msg;
        }
    }
}