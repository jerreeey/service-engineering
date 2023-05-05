using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services.Protocols;

namespace Service
{
    public class UserCredentials : SoapHeader
    {
        public string username;
        public string password;
    }
}