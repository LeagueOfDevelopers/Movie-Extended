using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Web;
using System.Web.Http;
using System.Web.Http.Controllers;

namespace FrontendService.Authorization
{
    public class AuthorizationAttribute : AuthorizeAttribute
    {
        private readonly AccountRole _accountRole;

        public AuthorizationAttribute(AccountRole accountRole)
        {
            _accountRole = accountRole;
        }

        protected override bool IsAuthorized(HttpActionContext actionContext)
        {
            return Thread.CurrentPrincipal.IsInRole(_accountRole);
        }
    }
}