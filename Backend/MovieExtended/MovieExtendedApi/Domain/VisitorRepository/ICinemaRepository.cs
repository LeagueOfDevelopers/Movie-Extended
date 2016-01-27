using System;
using System.Collections.Generic;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
   public interface ICinemaRepository
    {
        IEnumerable<Cinema> GetAllCinemas();
        void SaveCinemaData(Cinema cinema);
        IEnumerable<Cinema> GetCinemaByCompanyId(Guid companyId);
        void DeleteCinemaByCinemaId(Guid cinemaId);
        void DeleteCinemaByCompanyId(Guid companyId);
        Cinema GetCinemaByCinemaId(Guid cinemaId);

    }
}
