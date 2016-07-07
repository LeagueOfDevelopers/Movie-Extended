using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Net.Mail;
using System.Timers;
using Domain.Authorization;
using Domain.FingerPrinting;
using Domain.Models;
using Domain.Models.Entities;
using Infrastructure;
using Infrastructure.Repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using File = Domain.Models.Entities.File;

namespace UnitTestProject2
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void save()
        {
            var provider = new SessionProvider();
            var movieRepo = new MovieRepository(provider);
            //var session = provider.GetCurrentSession();
            //session.Transaction.Begin();
            //session.Save(company);
            //session.Transaction.Commit();
        }

        [TestMethod]
        public void testCompanyRepository()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            var langRepo = new LanguageRepository(provider);
            var lang = langRepo.GetLanguageById(2);
            lang.TrackFile.FilePath = "2idTrack";
            lang.Subtitles.FilePath = "2idSubtitle";
            
            //lang.Subtitles = new File("~/Subtitles/4.srt",FileType.Subtitles);
            langRepo.UpdateLanguage(lang);

        }

        [TestMethod]
        public void getMovie()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            var movieRepo = new MovieRepository(provider);
            var film = movieRepo.CheckAndroidToken(new Guid("00000000-0000-0000-0000-000000000000"));
            var choto_tam = movieRepo.GetMovieByMovieId(1);
            choto_tam.RussianTrack=new File(@"C:\Users\дшшр\Downloads\Summer Of Haze - Философия Урбанистического Безвременья (Многоточие Drag'n'trap Refix).mp3", FileType.Track);
            movieRepo.SaveMovie(
                choto_tam);
        }

        [TestMethod]
        public void PasswordTests()
        {
            var pass = new Password("slipknot");
            var md5Hash = pass.Value;
            Debug.WriteLine(md5Hash);
            var fejf = Path.GetRandomFileName();
            var defde = Path.GetExtension(fejf);
        }

        [TestMethod]
        public void TestCreating()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            var movie = new MovieRepository(provider);
            var language = new Language("test", new File("edfed",FileType.Track),new File("wdwd",FileType.Subtitles));
            var di = movie.CreateLanguage(language,2);
            Assert.IsNotNull(di); 
        }

        [TestMethod]
        public void FingerPrintTests()
        {
            IFingerPrintKeeper fingerprinter = new FingerPrintKeeper();
            fingerprinter.CreateHashesAndGetMovieDurationTime(@"C:\Users\дшшр\Downloads\Dr_WS - Wichhouse.mp3", new Movie(6,"dwedwed",new HashSet<Language>()));
            var queryTime = fingerprinter.QueryWithTimeInformation(@"C:\Users\дшшр\Downloads\Telegram Desktop\20160517_145213.mp3", 6);
            
            Assert.IsNotNull(queryTime);
        }

        [TestMethod]
        public void SaveAdminToDb()//Not test
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            var compusrepo = new CompanyUserRepository(provider);
            Account acc = new Account("admin" , "admin ", new MailAddress("example@mail.ru"), new Password("slipknot4997"));
            compusrepo.CreateUser(acc);
        }

        
    }
}
            


