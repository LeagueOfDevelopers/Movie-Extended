using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using Extended_Movie.Visitor_Repository;
using SimpleInjector;
using SimpleInjector.Integration.WebApi;

namespace FrontendService.App_Start
{
    public class Bootstraper
    {
        public Container Configure()
        {
            var container = new Container();
            container.Options.DefaultScopedLifestyle = new WebApiRequestLifestyle();
            container.Register<IMovieRepository>(()=>container.GetInstance<MovieRepository>(),Lifestyle.Singleton);
            container.Register<ILanguageRepository>(() => container.GetInstance<LanguageRepository>(), Lifestyle.Singleton);
            container.Register<ICompanyRepository>(() => container.GetInstance<CompanyRepository>(), Lifestyle.Singleton);
            container.Register<IFileRepository>(() => container.GetInstance<FileRepository>(), Lifestyle.Singleton);
            container.Register<ICinemaRepository>(() => container.GetInstance<CinemaRepository>(), Lifestyle.Singleton);
            container.RegisterWebApiControllers(GlobalConfiguration.Configuration);
            container.Verify();
            return container;
        }
    }
}