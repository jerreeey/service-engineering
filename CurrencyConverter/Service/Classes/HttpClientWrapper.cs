using System.Net.Http;
using System.Threading.Tasks;

namespace CurrencyConverterWebServiceTests
{
    public class HttpClientWrapper : IHttpClient
    {
        private readonly HttpClient _httpClient;

        public HttpClientWrapper()
        {
            _httpClient = new HttpClient();
        }

        public HttpResponseMessage Get(string requestUri)
        {
            return _httpClient.GetAsync(requestUri).Result;
        }
    }
}
