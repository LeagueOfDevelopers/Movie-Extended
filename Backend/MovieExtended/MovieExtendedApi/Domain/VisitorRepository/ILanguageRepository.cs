using System.Collections.Generic;
using Domain.Models;

namespace Domain.VisitorRepository
{
    public interface ILanguageRepository
    {
        IEnumerable<Language> GetAllLanguages();
        void SaveLanguage(Language language);
        IEnumerable<Language> GetLanguagesByMovieId(int movieId);
        void DeleteLanguageByLanguageId(int languageID);
        void DeleteLanguageByMovieId(int movieId);
        IEnumerable<Language> GetLanguageByName(string languageName);
        void UpdateLanguage(string jsonForUpdate);
    }
}
