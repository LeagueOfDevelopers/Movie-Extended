using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using Domain.Models.Entities;

using Domain.Repository;
using Journalist;
using NHibernate.Linq;

namespace Infrastructure.Repository
{
    public class LanguageRepository : ILanguageRepository
    {
        private readonly SessionProvider _provider;

        public LanguageRepository(SessionProvider provider)
        {
            Require.NotNull(provider, nameof(SessionProvider));
            _provider = provider;
        }

        

        public void SaveLanguage(Language language)
        {
            var session = _provider.GetCurrentSession();
            session.BeginTransaction();
            session.Save(language);
            session.Transaction.Commit();
        }

        public ISet<Language> GetLanguagesByMovieId(int movieId)
        {
            var session = _provider.GetCurrentSession();
            var movie = session.Query<Movie>().SingleOrDefault(model => model.Id == movieId);
            return movie.Language;
        }


        public void DeleteLanguageByLanguageId(int languageID)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Language>().SingleOrDefault(language => language.Id == languageID);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }

        public void DeleteLanguageByMovieId(int movie)
        {
            throw new NotImplementedException();
        }


        public IEnumerable<Language> GetLanguageByName(string languageName)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Language>().Where(language => language.Name == languageName);
        }

        public void UpdateLanguage(Language language)
        {
            var session = _provider.GetCurrentSession();
            session.Update(language);
        }


        public Language GetLanguageById(int Id)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Language>().SingleOrDefault(language => language.Id == Id);
        }

        public IEnumerable<Language> GetAllLanguages()
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Language>();
        } 
    }
}