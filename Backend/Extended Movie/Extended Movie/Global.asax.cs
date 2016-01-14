using System.Web.Http;
using System.Web.Routing;
using Extended_Movie.Controllers.AndroidClient;
using SimpleInjector;
using SimpleInjector.Integration.WebApi;
using Extended_Movie.App_Start;

namespace Extended_Movie
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            var container = new Container();
            container.Options.DefaultScopedLifestyle = new WebApiRequestLifestyle();

            //container.Register<>();
            container.RegisterWebApiControllers(GlobalConfiguration.Configuration);
            container.Verify();
            GlobalConfiguration.Configuration.DependencyResolver =
        new SimpleInjectorWebApiDependencyResolver(container);
            GlobalConfiguration.Configure(WebApiConfig.Register);
        }
    }
}
