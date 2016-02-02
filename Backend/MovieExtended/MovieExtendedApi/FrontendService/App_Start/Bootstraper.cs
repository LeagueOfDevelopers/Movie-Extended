using System.CodeDom.Compiler;
using System.Web.Http;
using Domain.VisitorRepository;
using Infrastructure.VisitorRepository;
using SimpleInjector;
using SimpleInjector.Integration.WebApi;
using Domain.Models;
namespace FrontendService
{
    public class Bootstraper
    {
        public Container Configure()
        {
            var container = new Container();
            container.Options.DefaultScopedLifestyle = new WebApiRequestLifestyle();
            container.Register<SessionProvider>(Lifestyle.Singleton);
            container.Register<IMovieRepository>(()=>container.GetInstance<MovieRepository>(),Lifestyle.Singleton);
            container.Register<ILanguageRepository>(() => container.GetInstance<LanguageRepository>(), Lifestyle.Singleton);
            container.Register<ICompanyRepository>(() => container.GetInstance<CompanyRepository>(), Lifestyle.Singleton);
            container.Register<IFileRepository>(() => container.GetInstance<FileRepository>(), Lifestyle.Singleton);
            container.Register<ICinemaRepository>(() => container.GetInstance<CinemaRepository>(), Lifestyle.Singleton);
            container.Register<IQrCodeRepository>(() => container.GetInstance<QrCodeRepository>(), Lifestyle.Singleton);
            container.Register<IQrCodeGenerator>(()=>container.GetInstance<QrCodeGenerator>(),Lifestyle.Singleton);
            
            container.RegisterWebApiControllers(GlobalConfiguration.Configuration);
            container.Verify();
            return container;
        }
    }
}