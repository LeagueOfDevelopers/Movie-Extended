using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Routing;
using Domain;

using SimpleInjector;

namespace FrontendService
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            var container = new Container();
            GlobalConfiguration.Configure(WebApiConfig.Register);
        }
    }
}
