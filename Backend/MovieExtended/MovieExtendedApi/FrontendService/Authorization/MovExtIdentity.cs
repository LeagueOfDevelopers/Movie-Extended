using System;
using System.Collections.Generic;
using System.Security.Principal;
using System.Web;

namespace FrontendService.Authorization
{
    public class MovExtIdentity: IIdentity
    {
        public MovExtIdentity(int userId, bool isAuthenticated)
        {
            UserId = userId;
            IsAuthenticated = isAuthenticated;
        }

        public int UserId { get; }

        public static MovExtIdentity EmptyIdentity => new MovExtIdentity(0, false);

        public string Name => UserId.ToString();

        public string AuthenticationType => "Token";

        public bool IsAuthenticated { get; }
    }
}