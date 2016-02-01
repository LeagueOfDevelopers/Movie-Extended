using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models.Entities
{
    public class QrCodeFingerprint
    {
        public string Value { get; protected set; }
        public string Id { get; protected set; }

        public QrCodeFingerprint(string value)
        {
            Value = value;
        }
    }
}
