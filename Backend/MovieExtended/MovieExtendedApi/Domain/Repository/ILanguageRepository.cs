using System.Collections.Generic;
using Domain.Models.Entities;
namespace Domain.Repository
{
    public interface ILanguageRepository
    {
        void SaveLanguage(Language language);
        ISet<Language> GetLanguagesByMovieId(int movieId);
        void DeleteLanguageByLanguageId(int languageID);
        void DeleteLanguageByMovieId(int movie);
        IEnumerable<Language> GetLanguageByName(string languageName);
        void UpdateLanguage(string jsonForUpdate);
    }
}