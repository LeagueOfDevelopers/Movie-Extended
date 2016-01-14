using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    public class Cinema
    {
        public Cinema(Guid? id, string name, string address, Guid companyId)
        {
            Id = id;
            Name = name;
            Address = address;
            CompanyId = companyId;
        }

        protected Cinema()
        {

        }

        public virtual Guid? Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual string Address { get; protected set; }

        public virtual Guid CompanyId { get; set; }
    }
}
