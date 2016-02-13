using System;
using System.Linq;
using Domain.Models.Entities;
using Journalist;
using NHibernate.Linq;
using NHibernate.Util;

namespace Infrastructure.VisitorRepository
{
   public class AndroidTokenRepository
   {
       private readonly SessionProvider _provider;

       public AndroidTokenRepository(SessionProvider provider)
       {
           Require.NotNull(provider, nameof(SessionProvider) );
           _provider = provider;
       }
        public bool CheckToken(Guid tokenGuid)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<AndroidToken>().Any(token => token.qrCodeToken ==tokenGuid );

        }

   }
}
