﻿using System;
using System.Collections.Generic;

namespace Domain.Models.Entities
{
    public class Company
    {
        public Company(int id, string name, Uri website , ISet<Cinema> cinema)
        {
            Id = id;
            Name = name;
            Website = website;
            Cinema = cinema;
        }

        public Company(string name, Uri website, ISet<Cinema> cinema )
        {
            Name = name;
            Website = website;
            Cinema = cinema;
        }

        protected Company()
        {
        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual Uri Website { get; protected set; }

        public  virtual ISet<Cinema> Cinema { get; set; }

        public virtual int IdAdmin { get; set; }
    }
}