using System.Diagnostics;
using System.Web.Http;
using SimpleInjector;
using SimpleInjector.Integration.WebApi;
using Extended_Movie.Visitor_Repository;

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
                typeof(SessionProvider)) as SessionProvider;
            sessionProvider.OpenSession();
        }

        protected void Application_EndRequest()
        {
            Debug.WriteLine("End request");
            var sessionProvider = GlobalConfiguration.Configuration.DependencyResolver.GetService(
                typeof(SessionProvider)) as SessionProvider;
            sessionProvider.CloseSession();
        }
    }
}
