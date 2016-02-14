using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Models.Entities;

namespace Domain.Models
{
     public interface ISessionKeeper
    {
        Guid CreateSession(Session session);
        bool CheckIfSessionExists(Guid sessionId);
        SessionState GetSessionState(Guid sessionId);
    }
}
