using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    public class Company
    {
        public Company(Guid? id, string name, Uri website, Uri photoUri)
        {
            Id = id;
            Name = name;
            Website = website;
        }

        protected Company()
        {

        }

        public virtual Guid? Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual Uri Website { get; protected set; }
    }
}
