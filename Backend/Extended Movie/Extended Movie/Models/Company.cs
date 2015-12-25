using System;

namespace Extended_Movie.Models
{
    public class Company
    {
        public Company(Guid? id , string name , Uri websiteUri)
        {
            Id = id;
            Name = name;
            WebSite = websiteUri;
        }


        public virtual Guid? Id { get; protected set; } 
        public virtual string Name { get; protected set; }
        public virtual Uri WebSite { get; protected set; }

    }
}