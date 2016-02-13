using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.VisitorRepository
{
    public interface IAndroidTokenRepository
    {
        void CreateNewToken();
        bool CheckToken(Guid tokenGuid);
    }
}
