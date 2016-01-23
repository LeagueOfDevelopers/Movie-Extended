using System.Diagnostics;
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
            

        }
        protected void Application_BeginRequest()
        {
            Debug.WriteLine("Begin request");
            var sessionProvider = GlobalConfiguration.Configuration.DependencyResolver.GetService(
                typeof(DatabaseSessionProvider)) as DatabaseSessionProvider;
            sessionProvider.OpenSession();
        }

        protected void Application_EndRequest()
        {
            Debug.WriteLine("End request");
            var sessionProvider = GlobalConfiguration.Configuration.DependencyResolver.GetService(
                typeof(DatabaseSessionProvider)) as DatabaseSessionProvider;
            sessionProvider.CloseSession();
        }
    }
}
