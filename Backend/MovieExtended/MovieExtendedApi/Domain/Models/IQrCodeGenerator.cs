using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    public interface IQrCodeGenerator
    {
        string Generate(int movieId, int companyId);
    }
}
