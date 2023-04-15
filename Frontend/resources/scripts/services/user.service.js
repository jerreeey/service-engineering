import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'https://6422e31877e7062b3e255693.mockapi.io/api/v1/users';
const API_URL2 = 'https://6422e31877e7062b3e255693.mockapi.io/api/v1/sessions';

class UserService {
  login(user) {
    return axios
      .post(API_URL + '/', {
        email: user.email,
        password: user.password
      })
      .then(response => {
        if (response.data.token) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
  }

  register(user) {
    return axios.post(API_URL, {
      email: user.email,
      password: user.password
    });
  }

  delete(user) {
    return axios.delete(API_URL + '/' + user.id, {
      id: user.id
    },
    {
      headers: authHeader()
    })
    .then(response => {
      if (response.code  == 200) {
        localStorage.removeItem('user');
      }

      return response.data;
    });
  }

  changePassword(user) {
    return axios.put(API_URL + '/' + user.id, {
      password: user.password
    },
    {
      headers: authHeader()
    })
    .then(response => {
      if (response.data.password  != user.password) {
        localStorage.setItem('user', JSON.stringify(response.data));
      }

      return response.data;
    });
  }

  changeEmail(user) {
    return axios.put(API_URL + '/' + user.id, {
      email: user.email
    },
    {
      headers: authHeader()
    })
    .then(response => {
      if (response.data.email  != user.email) {
        localStorage.setItem('user', JSON.stringify(response.data));
      }
      
      return response.data;
    });
  }
}

export default new UserService();