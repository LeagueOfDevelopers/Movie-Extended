using System;
using System.Collections.Generic;
using System.Threading;
using System.Web;
using System.Web.Http;
using System.Web.Http.Controllers;

namespace FrontendService.Authorization
{
    public class AuthorizationAttribute : AuthorizeAttribute
    {
        protected override bool IsAuthorized(HttpActionContext actionContext)
        {
            throw  new NotImplementedException();
        }
    }
}