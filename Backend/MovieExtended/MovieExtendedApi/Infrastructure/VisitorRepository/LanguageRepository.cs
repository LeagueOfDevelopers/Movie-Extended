using System.Collections;
using System.Collections.Generic;
using System.Linq;
using Domain.Models;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;
using Domain.VisitorRepository;
using Journalist;
using NHibernate.Linq;

namespace Infrastructure.VisitorRepository
{
    public class LanguageRepository:ILanguageRepository
    {
        private readonly SessionProvider _provider;
        
        public LanguageRepository(SessionProvider provider)
        {
            Require.NotNull(provider, nameof(SessionProvider));
            _provider = provider;
        }
        public IEnumerable<FrontendLanguage> GetAllLanguages()
        {
            var session = _provider.GetCurrentSession();
            var allLanguages = session.Query<Language>().AsEnumerable();
            var convertedLanguages = new List<FrontendLanguage>();

            foreach (var dblanguage in allLanguages)
            {
                var convertedLanguage = new FrontendLanguage(dblanguage);
                convertedLanguages.Add(convertedLanguage);
            }
            return convertedLanguages;
        }

        public void SaveLanguage(Language language)
        {
            var session = _provider.GetCurrentSession();
            session.BeginTransaction();
            session.Save(language);
            session.Transaction.Commit();
        }

        public IEnumerable<AndroidLanguage> GetLanguagesByMovieId(int movieId)
        {
            var session = _provider.GetCurrentSession();
            var languagesByMovieId = session.Query<Language>().Where(language => language.Movie.Id == movieId).AsEnumerable();
            var convertedLanguages = new List<AndroidLanguage>();

            foreach (var dblanguage in languagesByMovieId)
            {
                var convertedLanguage= new AndroidLanguage();
                convertedLanguage.Id = dblanguage.Id;
                convertedLanguage.Name = dblanguage.Name;
                convertedLanguage.TrackFileId = dblanguage.TrackFile.Id;
                convertedLanguages.Add(convertedLanguage);
            }
            return convertedLanguages;
        }

        public void DeleteLanguageByLanguageId(int languageID)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Language>().SingleOrDefault(language => language.Id == languageID);
            if (checkIfExists != null) session.Delete((checkIfExists));
        }

        public void DeleteLanguageByMovieId(int movieId)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Language>().Where(language => language.Movie.Id == movieId);
            if (checkIfExists != null) session.Delete(checkIfExists);   
        }

        public IEnumerable<Language> GetLanguageByName(string languageName)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Language>().Where(language => language.Name == languageName);
        }

        public void UpdateLanguage(string jsonForUpdate)
        {
            throw new System.NotImplementedException();
        }
    }
}