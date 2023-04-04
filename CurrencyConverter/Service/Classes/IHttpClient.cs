using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace CurrencyConverterWebServiceTests
{
    public interface IHttpClient
    {
        HttpResponseMessage Get(string requestUrl);
    }
}
