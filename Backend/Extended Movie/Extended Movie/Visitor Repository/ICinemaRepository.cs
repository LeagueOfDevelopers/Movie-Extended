using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Extended_Movie.Models;

namespace Extended_Movie.Visitor_Repository
{
    interface ICinemaRepository
    {
        IEnumerable<Cinema> GetAllCinemas();
        void SaveCinemaData(Cinema cinema);
        Cinema GetCinemaByCompanyId(Guid companyId);
        void DeleteCinemaByCinemaId(Guid? cinemaId);
        void DeleteCinemaByCompanyId(Guid companyId);
        Cinema GetCinemaByCinemaId(Guid? cinemaId);

    }
}
