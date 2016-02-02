using System.Diagnostics;
using System.Web.Http;
using SimpleInjector;
using SimpleInjector.Integration.WebApi;
using Infrastructure.VisitorRepository;

namespace FrontendService
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            var container = new Bootstraper().Configure();
            GlobalConfiguration.Configuration.DependencyResolver = new SimpleInjectorWebApiDependencyResolver(container);

            GlobalConfiguration.Configure(WebApiConfig.Register);
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
