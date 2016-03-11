using System.Collections.Generic;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;

namespace Domain.Repository
{
    public interface ILanguageRepository
    {
        IEnumerable<FrontendLanguage> GetAllLanguages();
        void SaveLanguage(Language language);
        ISet<Language> GetLanguagesByMovieId(int movieId);
        void DeleteLanguageByLanguageId(int languageID);
        void DeleteLanguageByMovieId(int movie);
        IEnumerable<Language> GetLanguageByName(string languageName);
        void UpdateLanguage(string jsonForUpdate);
    }
}