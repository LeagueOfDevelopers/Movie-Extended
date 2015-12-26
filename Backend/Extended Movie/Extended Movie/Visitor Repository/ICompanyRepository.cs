using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Extended_Movie.Models;

namespace Extended_Movie.Visitor_Repository
{
    interface ICompanyRepository
    {
        IEnumerable<Company> GetAllCompanies();
        Company GetCompanyByCompanyId(Guid? companyId);
        void deleteCompanyById(Guid? companyId);


    }
}
