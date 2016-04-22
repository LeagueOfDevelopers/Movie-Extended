using Domain.Models.Entities;
using Domain.Models.FrontendEntities;

namespace Domain.mapper
{
    public class CompanyMapper
    {
        public FrontendCompany ToFrontendCompany(Company company)
        {
            var frontendCompany = new FrontendCompany(company.Id,company.Name,company.Website);
            foreach (var cinema in company.Cinema)
            {
                var frontendCinema = new FrontendCinema(cinema.Id,cinema.Name,cinema.Address);
                frontendCompany.Cinema.Add(frontendCinema);
            }
            return frontendCompany;
        }
    }
}
