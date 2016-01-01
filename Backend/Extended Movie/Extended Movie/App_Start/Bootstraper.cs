using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Extended_Movie.Visitor_Repository;
using SimpleInjector;
using SimpleInjector.Integration.Web.Mvc;

namespace Extended_Movie.App_Start
{
    public class Bootstraper : System.Web.HttpApplication 
    {
        protected void Application_Start(object sender, EventArgs e)
        {
            var container = new Container();
            container.Register<ILanguageRepository,LanguageRepository>(Lifestyle.Singleton);
            ILanguageRepository lanLanguageRepository = container.GetInstance<ILanguageRepository>();
            container.Register<IMovieRepository,MovieRepository>(Lifestyle.Singleton);
            IMovieRepository movieRepository = container.GetInstance<IMovieRepository>();
            container.Register<IFileRepository,FileRepository>(Lifestyle.Singleton);
            IFileRepository fileRepository = container.GetInstance<IFileRepository>();
            container.Register<ICinemaRepository,CinemaRepository>(Lifestyle.Singleton);
            ICinemaRepository cinemaRepository = container.GetInstance<ICinemaRepository>();
            container.Register<ICompanyRepository,CompanyRepository>(Lifestyle.Singleton);
            ICompanyRepository companyRepository = container.GetInstance<ICompanyRepository>();
            container.Verify();
            DependencyResolver.SetResolver(new SimpleInjectorDependencyResolver(container));
        }
    }
}