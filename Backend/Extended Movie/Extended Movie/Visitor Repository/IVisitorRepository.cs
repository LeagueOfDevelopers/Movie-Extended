using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Extended_Movie.Models;

namespace Extended_Movie.Visitor_Repository
{
    interface IVisitorRepository
    {
        void SaveFileData(File file);
        void SaveMovieData(Movie movie);
        void SaveCinemaData(Cinema cinema);
        void SaveLanguageData(Language language);
        
    }
}
