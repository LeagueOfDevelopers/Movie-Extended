using System.Web.Http;
using Domain.Authorization_authentification;
using FrontendService.Authorization;

namespace FrontendService
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            // Конфигурация и службы веб-API
           // config.SuppressHostPrincipal();
            // Маршруты веб-API
            config.MapHttpAttributeRoutes();
            config.Routes.MapHttpRoute("DefaultApi", "api/{controller}/{id}", new {id = RouteParameter.Optional}
                );
            var authorizer = config.DependencyResolver.GetService(typeof(IAuthorizer)) as IAuthorizer;
            config.Filters.Add(new AuthenticateAttribute(authorizer));
        }
    }
}