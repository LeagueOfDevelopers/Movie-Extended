using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    interface ITokenGenerator
    {
        Token Generate(Movie movie, Cinema cinema);
    }
}
