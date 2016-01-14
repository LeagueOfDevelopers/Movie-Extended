using System.Web.Http;
using SimpleInjector;
using SimpleInjector.Integration.WebApi;

namespace FrontendService
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            var container = new Container();
            GlobalConfiguration.Configure(WebApiConfig.Register);
            container.RegisterWebApiControllers(GlobalConfiguration.Configuration);
            container.Verify();
            GlobalConfiguration.Configuration.DependencyResolver =
        new SimpleInjectorWebApiDependencyResolver(container);
            GlobalConfiguration.Configure(WebApiConfig.Register);

        }
    }
}
