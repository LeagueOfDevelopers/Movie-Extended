using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models.Entities
{
    public class QrCodeFingerprint
    {
        public virtual string Value { get; protected set; }
        public virtual string Id { get; protected set; }

        protected QrCodeFingerprint()
        {
        }

        public QrCodeFingerprint(string value)
        {
            Value = value;
        }
    }
}
