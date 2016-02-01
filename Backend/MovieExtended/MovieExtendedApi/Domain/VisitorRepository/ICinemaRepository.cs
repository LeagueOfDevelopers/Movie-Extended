using System.Collections.Generic;
using Domain.Models;
using Domain.Models.Entities;

namespace Domain.VisitorRepository
{
   public interface ICinemaRepository
    {
        IEnumerable<Cinema> GetAllCinemas();
        void SaveCinemaData(Cinema cinema);
        IEnumerable<Cinema> GetCinemaByCompanyId(int companyId);
        void DeleteCinemaByCinemaId(int cinemaId);
        void DeleteCinemaByCompanyId(int companyId);
        Cinema GetCinemaByCinemaId(int cinemaId);
       void UpdateCinema(string jsonForUpdate);
    }
}
