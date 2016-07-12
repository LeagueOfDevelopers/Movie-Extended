﻿using System;
using System.Collections.Generic;

namespace Domain.Models.Entities
{
    public class Movie
    {
        public Movie(int id, string name , ISet<Language> language)
        {
            Id = id;
            Name = name;
            Language = language;

        }

        public Movie(string name , ISet<Language> language,File poster)
        {
            Name = name;
            Language= language;
            Poster = poster;

        }

        

        protected Movie()
        {
        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public  virtual ISet<Language> Language { get; set; }

        public virtual Guid AndroidToken { get; set; }

        public virtual File  Poster { get; set; }

        public virtual File RussianTrack { get; set; }

        public virtual int IdAdmin { get; set; }
    }
}