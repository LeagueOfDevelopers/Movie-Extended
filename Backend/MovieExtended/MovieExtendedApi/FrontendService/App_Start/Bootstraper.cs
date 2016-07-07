using System;
using System.Configuration;
using System.Web.Http;
using Domain.Authorization;
using Domain.Authorization_authentification;
using Domain.FingerPrinting;
using Domain.Models;
using Domain.Repository;
using Infrastructure;
using Infrastructure.Repository;
using SimpleInjector;
using SimpleInjector.Integration.WebApi;

namespace FrontendService
{
    public class Bootstraper
    {
        public Container Configure()
        {
            var container = new Container();
            container.Options.DefaultScopedLifestyle = new WebApiRequestLifestyle();
            container.Register<SessionProvider>(Lifestyle.Singleton);
            container.Register<IMovieRepository>(() => container.GetInstance<MovieRepository>(), Lifestyle.Singleton);
            container.Register<ILanguageRepository>(() => container.GetInstance<LanguageRepository>(),
                Lifestyle.Singleton);
            container.Register<ICompanyRepository>(() => container.GetInstance<CompanyRepository>(), Lifestyle.Singleton);
            container.Register<IFileRepository>(() => container.GetInstance<FileRepository>(), Lifestyle.Singleton);
            container.Register<ICinemaRepository>(() => container.GetInstance<CinemaRepository>(), Lifestyle.Singleton);
            container.Register<IQrCodeRepository>(() => container.GetInstance<QrCodeRepository>(), Lifestyle.Singleton);
            container.Register<IQrCodeGenerator>(() => container.GetInstance<QrCodeGenerator>(), Lifestyle.Singleton);
            container.Register<ICompanyUserRepository>(() => container.GetInstance<CompanyUserRepository>() , Lifestyle.Singleton);
            container.Register<ISessionKeeper>(() => container.GetInstance<SessionKeeper>(), Lifestyle.Singleton);
            
            container.Register<IFileManager>(()=>container.GetInstance<FileManager>(),Lifestyle.Singleton);
            container.Register<IFingerPrintKeeper>(()=> container.GetInstance<FingerPrintKeeper>(),Lifestyle.Singleton);
            container.Register<IAuthorizer>(() => new Authorizer(
                TimeSpan.FromSeconds(int.Parse(ConfigurationManager.AppSettings["Authorizer.TokenLifeTimeInSeconds"])),
                container.GetInstance<ICompanyUserRepository>()),
                Lifestyle.Singleton);
            container.RegisterWebApiControllers(GlobalConfiguration.Configuration);
            container.Verify();
            return container;
        }
    }
}