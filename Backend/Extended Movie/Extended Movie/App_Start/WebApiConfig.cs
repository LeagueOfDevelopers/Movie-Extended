using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;

namespace Extended_Movie
{
    public static class WebApiConfig
    {
        // qw  qw  q
        public static void Register(HttpConfiguration config)
        {
            // Конфигурация и службы веб-API
            var q = "test";
            // Маршруты веб-API
            config.MapHttpAttributeRoutes();

            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );
        }
    }
}
