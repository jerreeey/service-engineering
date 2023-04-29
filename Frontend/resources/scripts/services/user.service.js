import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'https://app-carrental-230415231716.azurewebsites.net/users';
const API_URL2 = 'https://app-carrental-230415231716.azurewebsites.net/authentications';

class UserService {
  login(user) {
    return axios
      .post(API_URL2 + '/sessions', {
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
    return axios.post(API_URL2, {
      email: user.email,
      password: user.password
    });
  }

  delete(user) {
    return axios.delete(API_URL + '/' + user.id, {
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

  changePassword(user, newPassword) {
    return axios.put(API_URL + '/' + user.id, {
      password: newPassword
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

  changeEmail(user, newEmail) {
    return axios.put(API_URL + '/' + user.id, {
      email: newEmail
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