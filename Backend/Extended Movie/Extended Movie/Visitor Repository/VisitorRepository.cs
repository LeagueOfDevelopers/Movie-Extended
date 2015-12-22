using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Extended_Movie.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class VisitorRepository : IVisitorRepository
    {
        private readonly ISession _session;
        
        public void SaveCinemaData(Cinema cinema)
        {
            _session.BeginTransaction();
            _session.SaveOrUpdate(cinema);
            _session.Transaction.Commit();
        }

        public void SaveFileData(File file)
        {
            _session.BeginTransaction();
            _session.SaveOrUpdate(file);
            _session.Transaction.Commit();
        }

        public void SaveLanguageData(Language language)
        {
            _session.BeginTransaction();
            _session.SaveOrUpdate(language);
            _session.Transaction.Commit();
        }

        public void SaveMovieData(Movie movie)
        {
            _session.BeginTransaction();
            _session.SaveOrUpdate(movie);
            _session.Transaction.Commit();
        }
    }
}