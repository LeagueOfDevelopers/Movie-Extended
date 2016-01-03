using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Extended_Movie.Controllers.AndroidClient
{
    interface ISessionController
    {
         Guid Login(string qr);
        string GetMovieStartTime(Guid sessionId);
        
    }
}
