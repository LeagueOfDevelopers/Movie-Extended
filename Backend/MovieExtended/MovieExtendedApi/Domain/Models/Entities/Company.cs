using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    public class Company
    {
        public Company(int id, string name, Uri website)
        {
            Id = id;
            Name = name;
            Website = website;
        }

        public Company(string name, Uri website)
        {
            Name = name;
            Website = website;
        }

        protected Company()
        {

        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual Uri Website { get; protected set; }
    }
}
