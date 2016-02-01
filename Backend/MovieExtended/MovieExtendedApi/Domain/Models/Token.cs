using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    public class Token
    {
        public Token(string value)
        {
            Value = value;
        }

        public string Value { get; private set; }
    }
}
