using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models.Entities
{
     public class CompanyUser
    {
         public CompanyUser(int id , string firstname,string lastname , Company company)
         {
             Id = id;
             FirstName = firstname;
             LastName = lastname;
             Company = company;
         }

         protected CompanyUser()
         {
             
         }
        public virtual int Id { get;  set; }
        public virtual string FirstName { get; set; }
        public virtual  string LastName { get; set; }
        public virtual Company Company { get; set; }
    }
}
