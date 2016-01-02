using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Routing;
using Extended_Movie.Controllers.AndroidClient;
using Extended_Movie.Models;
using SimpleInjector;
using SimpleInjector.Integration.WebApi;

namespace Extended_Movie
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            GlobalConfiguration.Configure(WebApiConfig.Register);
            var container = new Container();
            container.Options.DefaultScopedLifestyle = new WebApiRequestLifestyle();
            
            //container.Register<>();
            container.RegisterWebApiControllers(GlobalConfiguration.Configuration);
            GlobalConfiguration.Configuration.DependencyResolver =
        new SimpleInjectorWebApiDependencyResolver(container);
            container.Register<ISessionController,SessionController>(Lifestyle.Scoped);
            container.Verify();
            var _sessionController = (ISessionController)container.GetInstance<ISessionController>();


        }
    }
}
