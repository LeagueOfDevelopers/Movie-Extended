using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Extended_Movie.Visitor_Repository;
using SimpleInjector;

namespace Extended_Movie.App_Start
{
    public class Bootstraper : System.Web.HttpApplication 
    {
        protected void Application_Start(object sender, EventArgs e)
        {
            var container = new Container();
            container.Register<ILanguageRepository,LanguageRepository>(Lifestyle.Singleton);
            container.Register<IMovieRepository,MovieRepository>(Lifestyle.Singleton);
            container.Register<IFileRepository,FileRepository>(Lifestyle.Singleton);
            container.Register<ICinemaRepository,CinemaRepository>(Lifestyle.Singleton);
            container.Register<ICompanyRepository,CompanyRepository>(Lifestyle.Singleton);
            container.Verify();
        }
    }
}