using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.VisitorRepository
{
    public interface IQrCodeRepository
    {
        void SaveQrCodeFingeprint(string qrCodeFingerprint);
    }
}
